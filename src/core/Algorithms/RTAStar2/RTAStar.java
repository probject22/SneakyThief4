package core.Algorithms.RTAStar2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dataContainer.Coordinate;

public abstract class RTAStar {
	
	
	public List<Node> getShortestPath(Node from, Node to){
		
		// if there are no neighbours, there is no path
		if ( from.neighbours.isEmpty() )
			return null;
		
		// assume there exists a path.
		
		// calculate the heuristic for the starting node
		from.f = h(from, to);
		
		// tracking node
		Node n = from;
		
		List<Node> visited = new ArrayList<Node>();
		
		// loop until the goal is found
		while ( ! n.equals(to) ){
			
			// go through all the neighbours
			for( Entry<Node, Double> neighbour : n.neighbours.entrySet() )
				
				// for every neighbour that has not been visited yet
				if( ! visited.contains(neighbour) )
					
					// the f-value of each neighbour is the heuristic function plus the cost of moving there from the current node
					neighbour.getKey().f = h(neighbour.getKey(), to) + neighbour.getValue();

			
			double minF = Double.MAX_VALUE;
			double secondMinF = Double.MAX_VALUE;
			Node minNeighbour = null;
			
			// find the best and second best nodes and their f values
			for ( Node neighbour : n.neighbours.keySet() ){
				
				// check if the f value is smaller than the mininimum F
				if( neighbour.f < minF ){
					//update
					minF = neighbour.f;
					minNeighbour = neighbour;
				}
				
				// check if the f value is larger than the minimum, but lower than the second
				// else because this is mutually exclusive with the previous if statement
				else if (neighbour.f >= minF && neighbour.f < secondMinF)
					// update
					secondMinF = neighbour.f;
			}
			
			// add the current node to the visited list
			visited.add(n);
			
			// set the parent of the next neighbour to the current node
			minNeighbour.parent = n;
			
			// move to the best neighbour
			n = minNeighbour;
			
			// set the f-value of the parent to the second best plus the cost of moving to that node
			n.parent.f = secondMinF + n.neighbours.get(n.parent);
			
		}
		// loop is exited as soon as the goal node is reached
		
		// reconstruct the path
		return reconstructPath(n);
	}
	
	abstract double h(Node from, Node to);
	
	public List<Node> reconstructPath(Node solution){
		
		// start with empty list
		ArrayList<Node> path = new ArrayList<>();
		
		// start tracking at the end node
		Node n = solution;
		
		// while there still is a parent
		while ( n != null ){
			
			// add the current node to the list
			path.add(n);
			
			// move to the parent
			n = n.parent;
		}
		
		return path;
		
	}
	
	protected class Node {
		// neighbours of the node with the respective moving cost
		Map<Node, Double> neighbours;
		
		// the parent of the current node, used for path reconstruction
		Node parent;
		
		// expected cost of moving through this node
		double f;
		
		// element
		Coordinate c;
		
		public boolean equals(Object o){
			if ( !(o instanceof Node)) return false;
			return ((Node) o).c.equals(c);
		}
	}
	
}
