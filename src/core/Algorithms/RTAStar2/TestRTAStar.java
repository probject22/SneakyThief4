/**
 * 
 */
package core.Algorithms.RTAStar2;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import core.Map;
import core.Algorithms.PathFinder;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class TestRTAStar implements PathFinder<Coordinate> {
	private Map map;
	private Coordinate goal;
	protected ArrayList<Node> closedNodes = new ArrayList<Node>(); 
	/**
	 * 
	 */
	public TestRTAStar(Map beliefMap) {
		this.map =beliefMap;
	}

	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		goal = to;
		Node startNode = new Node(null, from.x, from.y);
		setHeuristic(startNode);
		startNode.f = startNode.h;
		closedNodes = new ArrayList<Node>();
		ArrayList<Node> nodeTree = createTree(startNode);
		if (nodeTree == null)
			return null;
		
		Node outNode = null;
		if (nodeTree.size() > 2)
			outNode = nodeTree.get(nodeTree.size()-2);
		else 
			outNode = nodeTree.get(0);
		return new Coordinate(outNode.x, outNode.y, 0);
	}
	
	protected ArrayList<Node> createTree(Node start){
		Node currentNode = start;
		closedNodes.add(currentNode);
		while(true){
			//System.err.println("x " + currentNode.x + " y "+ currentNode.y + " f " + currentNode.f);
			/* if we are at the goal break */
			if (currentNode.x == goal.x && currentNode.y == goal.y){
				break;
			}
			
			/* get a list of all the neighbors and calculate the cost for them */
			ArrayList<Node> tempNeighbors = getNeighbors(currentNode);
			ArrayList<Node> neighbors = new ArrayList<Node>();
			for (Node tempNode : tempNeighbors){
				boolean found = false;
				for (Node closed: closedNodes){
					/* 
					 * check if it was already the best node once before in the past 
					 * if yes the get the old values back
					 */
					if (closed.x == tempNode.x && closed.y == tempNode.y){
						if(currentNode.parent == null || !(closed.x == currentNode.parent.x && closed.y == currentNode.parent.y)){
							closed.parent = currentNode;
							neighbors.add(closed);
						}
						found = true;
					}
				}
				if (!found){
					if(currentNode.parent == null || !(tempNode.x == currentNode.parent.x && tempNode.y == currentNode.parent.y)){
						setCost(tempNode);
						setHeuristic(tempNode);
						setEstemator(tempNode);
						tempNode.parent = currentNode;
						closedNodes.add(tempNode);
						neighbors.add(tempNode);
					}
				}
				
			}
			System.err.println("neighbors " + currentNode.x + " "  + currentNode.y);
			for(Node test: neighbors){
				System.err.println(test.x + " "  + test.y + " " + test.f);
			}
			/* get the best neighbor */
			Node nextNode = Collections.min(neighbors, new NodeComparator());
			neighbors.remove(nextNode);
			
			
			
			/* put the h value of the second best in this one */
			Node secondBest = Collections.min(neighbors, new NodeComparator());
			
			//System.err.println("cur x " + currentNode.x + " cur y " + currentNode.y);
			//System.err.println("Best f " + nextNode.f + " 2f " + secondBest.f + " cf" + currentNode.f);
			//System.err.println("Best x " + nextNode.x + " Best y " + nextNode.y);
			//System.err.println("2Best x " + secondBest.x + " 2Best y " + secondBest.y);
			currentNode.f = secondBest.f;
			
			System.err.println("Best f " + nextNode.f + " 2f " + secondBest.f + " cf" + currentNode.f);
			
			if (currentNode.parent != null && (nextNode.f > currentNode.parent.f + currentNode.parent.g ))
					nextNode = currentNode.parent;
			currentNode = nextNode;
		}
		
		return backtrackPath(currentNode);
	
	}
	protected ArrayList<Node> backtrackPath(Node endNode) {
		ArrayList<Node> out = new ArrayList<Node>();
		while(endNode != null){
			out.add(endNode);
			endNode = endNode.parent;
		}
		return out;
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
					if (!found || agents.size() == 0)
						out.add(tempNode );
				}
			}
		}
	
		return out;
	}
	/**
	 * The cost you have to pay to get to this node
	 * @param node
	 */
	protected void setCost(Node node){
		GridState[][] grid = map.getCopyOfMap();
		double cost = grid[node.x][node.y].getAstarCost();
		node.g = cost;
	}
	/**
	 * heuristic the expected cost to go from this node to the goal
	 * @param node
	 */
	protected void setHeuristic(Node node){
			double dx = node.x - goal.x;
			double dy = node.y - goal.y;
		
			node.h = sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * the total estimation of the cost to get from start to goal
	 * @param node
	 */
	protected void setEstemator(Node node){
		node.f = node.g+node.h;
	}
	public class Node{
		public Node(Node parent, int x, int y){
			this.parent = parent;
			this.x = x;
			this.y = y;
		}
		public Node(Node parent, int x, int y, double h){
			this.parent = parent;
			this.x = x;
			this.y = y;
			this.h = h;
			this.f = h;
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
        //Node best =  Collections.min(openNodes, new NodeComparator());
    }

}
