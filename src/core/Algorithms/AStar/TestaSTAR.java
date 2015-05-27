/**
 * 
 */
package core.Algorithms.AStar;

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
		ArrayList<Node> neightbors = getNeightbors(best);
		
		boolean found = false;
		for (Node neightbor: neightbors){
		//calculate the values of the neighbors
		
			for (Node tempOpenNode: openNodes){
				if (tempOpenNode.x == neightbor.x && tempOpenNode.y == neightbor.y){
					updateNode(tempOpenNode,neightbor);
					found = true;
					break;
				}
			}
			if (!found){
				for (Node tempClosedNode: closedNodes){
					if (tempClosedNode.x == neightbor.x && tempClosedNode.y == neightbor.y){
						updateNode(tempClosedNode,neightbor);
						found = true;
						break;
					}
				}
			}
			if (!found){
				openNodes.add(neightbor);
			}
		}
		
	}
	
	
	private void updateNode(Node orginal, Node newVals){
		//TODO implement this
	}
	private ArrayList<Node> getNeightbors(Node node){
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
		public Node parent;
		
		public int x = 0;
		public int y = 0;
	}
}
