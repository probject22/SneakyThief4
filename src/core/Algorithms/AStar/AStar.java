/**
 * 
 */
package core.Algorithms.AStar;

import static java.lang.Math.sqrt;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import core.Map;
import core.Algorithms.PathFinder;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;
import core.DebugConstants;
/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class AStar implements PathFinder<Coordinate> {
	Map map;
	protected boolean debug = DebugConstants.astarDebug;
	protected ArrayList<Node> openNodes = new ArrayList<Node>(); 
	protected ArrayList<Node> closedNodes = new ArrayList<Node>(); 
	/**
	 * @return the closedNodes
	 */
	public ArrayList<Node> getClosedNodes() {
		return closedNodes;
	}
	protected Coordinate goal;
	/**
	 * 
	 */
	public AStar(Map map) {
		this.map = map;
	}
	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		this.goal = to;
		
		if(debug && goal != null)System.err.println("goal "+goal);
		else if (debug)System.err.println("The astar class is in prey mode");
		openNodes = new ArrayList<Node>(); 
		closedNodes = new ArrayList<Node>();
		openNodes.add(new Node(null, from.x, from.y));
		ArrayList<Node> nodeTree = createTree();
		if (nodeTree == null)
			return null;
		
		Node outNode = null;
		if (nodeTree.size() > 2)
			outNode = nodeTree.get(nodeTree.size()-2);
		else 
			outNode = nodeTree.get(0);
		return new Coordinate(outNode.x, outNode.y, 0);
	}
	
	protected ArrayList<Node> createTree(){
		while (openNodes.size() != 0){
			Node best =  Collections.min(openNodes, new NodeComparator());
			openNodes.remove(best);
			closedNodes.add(best);
			if (goal != null)
				if (best.x == goal.x && best.y == goal.y)
					return backtrackPath(best);
			
			ArrayList<Node> neighbors = getNeighbors(best);
			
			
			for (Node neighbor: neighbors){
			//calculate the values of the neighbors
				boolean found = false;
				neighbor.parent = best;
				setDepth(neighbor);
				setCost(neighbor);
				setHeuristic(neighbor);
				setEstemator(neighbor);
				
				for (Node tempOpenNode: openNodes){
					if (tempOpenNode.x == neighbor.x && tempOpenNode.y == neighbor.y){
						updateNode(tempOpenNode,neighbor);
						found = true;
						break;
					}
				}
				if (!found){
					for (Node tempClosedNode: closedNodes){
						if (tempClosedNode.x == neighbor.x && tempClosedNode.y == neighbor.y){
							updateNode(tempClosedNode,neighbor);
							found = true;
							break;
						}
					}
				}
				if (!found){
					openNodes.add(neighbor);
				}
			}
		}
		return null;
		
	}
	
	/**
	 * returns the path back from the endNode to the start node
	 * @param endNode
	 * @return
	 */
	protected ArrayList<Node> backtrackPath(Node endNode) {
		ArrayList<Node> out = new ArrayList<Node>();
		while(endNode != null){
			out.add(endNode);
			endNode = endNode.parent;
		}
		return out;
	}
	/**
	 * Tells you how deep you are inside the tree
	 */
	protected void setDepth(Node node){
		node.depth = node.parent.depth +1;
	}
	
	/**
	 * The cost you have to pay to get to this node
	 * @param node
	 */
	protected void setCost(Node node){
		GridState[][] grid = map.getCopyOfMap();
		double cost = grid[node.x][node.y].getAstarCost();
		node.g = node.parent.g + cost;
	}
	/**
	 * heuristic the expected cost to go from this node to the goal
	 * @param node
	 */
	protected void setHeuristic(Node node){
		if (goal != null){
			double dx = node.x - goal.x;
			double dy = node.y - goal.y;
		
			node.h =  sqrt(dx * dx + dy * dy);
		}
	}
	
	/**
	 * the total estimation of the cost to get from start to goal
	 * @param node
	 */
	protected void setEstemator(Node node){
		node.f = node.g+node.h;
	}
	
	
	protected void updateNode(Node origonal, Node newVals){
		if (origonal.f > newVals.f){
			
			origonal.f = newVals.f;
			origonal.g = newVals.g;
			origonal.h = newVals.h;
			
			origonal.parent = newVals.parent;
			origonal.depth = newVals.depth;
		}
	}
	protected ArrayList<Node> getNeighbors(Node node){
		ArrayList<Node> options = new ArrayList<Node>();
		ArrayList<Node> out = new ArrayList<Node>();
		
		options.add(new Node(node, node.x-1, node.y-1));
		options.add(new Node(node, node.x  , node.y-1));
		options.add(new Node(node, node.x+1, node.y-1));
		options.add(new Node(node, node.x-1, node.y));
		options.add(new Node(node, node.x+1, node.y));
		options.add(new Node(node, node.x-1, node.y+1));
		options.add(new Node(node, node.x  , node.y+1));
		options.add(new Node(node, node.x+1, node.y+1));
		
		GridState[][] grid = map.getCopyOfMap();
		
		for (Node tempNode: options){
			if (tempNode.x < grid.length && tempNode.x >= 0 && tempNode.y < grid[0].length && tempNode.y >= 0){
				if (grid[tempNode.x][tempNode.y].moveable()){
					SpriteManager manager = SpriteManager.instance();
					List<Agent> agents = manager.getAgentList();
					boolean found = false;
					for(Agent agent: agents){
						Node checkedAgent = new Node(null, agent.getCoordinates().x, agent.getCoordinates().y);
						if(checkedAgent.x == tempNode.x && checkedAgent.y == tempNode.y)
							found = true;
					}
					if (!found)
						out.add(tempNode);
					if (agents.size() == 0)
						out.add(tempNode);	
					
				}
			}
		}
	
		return out;
	}
	
	public class Node{
		public Node(Node parent, int x, int y){
			this.parent = parent;
			this.x = x;
			this.y = y;
		}
		public double g = 0;
		public double h = 0;
		public double f = 0;
		
		public int depth = 0;
		public Node parent;
		
		public int x = 0;
		public int y = 0;
	}
    class NodeComparator implements Comparator<Node>{
        @Override
        public int compare(Node n1, Node n2) {
            if (n1.f < n2.f)
                return -1;
            if (n1.f > n2.f)
                return 1;
            return 0;
        }
    }
}
