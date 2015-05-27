/**
 * 
 */
package core.Algorithms.AStar;

import static java.lang.Math.sqrt;

import java.util.ArrayList;

import core.Map;
import core.Algorithms.PathFinder;
import dataContainer.Coordinate;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class TestaSTAR implements PathFinder<Coordinate> {
	Map map;
	private ArrayList<Node> openNodes = new ArrayList<Node>(); 
	private ArrayList<Node> closedNodes = new ArrayList<Node>(); 
	private Coordinate goal;
	/**
	 * 
	 */
	public TestaSTAR(Map map) {
		this.map = map;
	}
	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		this.goal = to;
		openNodes = new ArrayList<Node>(); 
		closedNodes = new ArrayList<Node>();
		openNodes.add(new Node(null, from.x, from.y));
		return null;
	}
	
	private void findPath(){
		Node best = new Node(null, 0,0); //TODO replace this with the node with the lowest f from openNode
		//if the node with the lowest f value is the goal then stop
		//if the openlist.size() == 0 stop
		//remove it from the open list and add it to the closed list
		closedNodes.add(best);
		ArrayList<Node> neighbors = getNeighbors(best);
		
		boolean found = false;
		for (Node neighbor: neighbors){
		//calculate the values of the neighbors
			
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
	
	/**
	 * Tells you how deep you are inside the tree
	 */
	private void setDepth(Node node){
		node.depth = node.parent.depth +1;
	}
	
	/**
	 * The cost you have to pay to get to this node
	 * @param node
	 */
	private void setCost(Node node){
		//TODO calculate the actual cost
		node.g = node.parent.g + 1;
	}
	/**
	 * heuristic the expected cost to go from this node to the goal
	 * @param node
	 */
	private void setHeuristic(Node node){
		double dx = node.x - goal.x;
		double dy = node.y - goal.y;
		
		node.h =  sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * the total estimation of the cost to get from start to goal
	 * @param node
	 */
	private void setEstemator(Node node){
		node.f = node.g+node.h;
	}
	
	
	private void updateNode(Node orginal, Node newVals){
		//TODO implement this
	}
	private ArrayList<Node> getNeighbors(Node node){
		//TODO implement this
		return null;
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
}
