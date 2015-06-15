/**
 * 
 */
package core.Algorithms.RTAStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import core.Map;
import core.Algorithms.PathFinder;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;
/**
 * @author Stan Kerstjens
 *
 */
public class RealTimeAStar implements PathFinder<Coordinate> {
	
	// The map that AStar works on. This map should be the beliefmap of the agent.
	Map map;
	
	/**
	 * 
	 */
	public RealTimeAStar(Map map) {
		this.map = map;
	}
	
	// The nodes that the RTA* works on. These nodes are generated as the algorithm progresses, and the algorithms
	// checks whether the node already exists based on the coordinates of the node.
	HashMap<Coordinate,Node> nodes = new HashMap<Coordinate,Node>();
	
	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		
		// if the goal is not reachable, return failure. There is no path.
		if(!map.isMoveable(to)) return null;
		
		// define the starting node (the current location of the agent)
		Node start = new Node();
		start.c = from.clone();
		
		// the starting node has no parent.
		start.parent = null;
		
		// The expected cost does not have a current cost component, so it is just the heuristic value
		// The heuristic is the direct distance from the start to the goal. This heuristic never over-
		// estimates the true cost, as required
		start.f = start.c.distance(to);
		
		// initialize the iterator node with the start node
		Node n = start;
		
		// (optional) define a maximum number of iterations. This is usefull for very long paths. This will
		// give a preliminary result.
		//int MAX_ITERATION = 1000000;
		//int i = 0;
		
		// Loop until the goal is found
		while(!n.c.equals(to)){
			
			// get the neighbouring nodes of the current node
			List<Node> neighbours = neighbours(n);
			
			
			//if there are no possible moves return null
			if(neighbours.isEmpty())
				return null;
			
			// For every neighbouring node, if the node does not yet have a f-value, initiate the value
			// to the direct distance to the goal, plus the cost from moving from the current node to the
			// neigbour.
			for(Node no : neighbours)
				if(no.f == 0) 
					no.f = no.c.distance(to)+1;
			
			
			// sort the list of neigbours based on their f-value
			Collections.sort(neighbours);
			
			// 
			n.f = neighbours.get(1).f + 1;
			
			//move
			Node p = n;
				
			n = neighbours.get(0);
			System.out.println("p.f " + p.f + " " + p.c);
			System.out.println("n.f " + n.f + " " + neighbours.get(1).c);
			n.parent = p;
			//if(i++ > MAX_ITERATION) break;
		}
		
		if(n.parent != null)
			while (n.parent.parent != null)
				n = n.parent;
		
		return n.c;
	}
	
	public List<Node> neighbours(Node node) {
		ArrayList<Node> options = new ArrayList<Node>();
		ArrayList<Node> out = new ArrayList<Node>();
		
		ArrayList<Coordinate> co = new ArrayList<>();
		
		co.add(new Coordinate(node.c.x-1,node.c.y-1,0));
		co.add(new Coordinate(node.c.x-1,node.c.y,0));
		co.add(new Coordinate(node.c.x-1,node.c.y+1,0));
		co.add(new Coordinate(node.c.x,node.c.y-1,0));
		co.add(new Coordinate(node.c.x,node.c.y+1,0));
		co.add(new Coordinate(node.c.x+1,node.c.y,0));
		co.add(new Coordinate(node.c.x+1,node.c.y-1,0));
		co.add(new Coordinate(node.c.x+1,node.c.y+1,0));
		
		
		for(Coordinate c : co){
			boolean found = false;
			for(Coordinate tempCoord :nodes.keySet()){
				if (tempCoord.x == c.x && tempCoord.y == c.y){
				options.add(nodes.get(tempCoord));
				System.out.println("node exist");
				found = true;
				}
			}
			if(!found){
				Node a = new Node(node, c.x,c.y);
				options.add(a);
				System.out.println("not exist");
				nodes.put(c, a);
			}
			
		}
		
		GridState[][] grid = map.getCopyOfMap();
		
		for (Node tempNode : options){
			if (tempNode.c.x < grid.length && tempNode.c.x >= 0 && tempNode.c.y < grid[0].length && tempNode.c.y >= 0){
				if (grid[tempNode.c.x][tempNode.c.y].moveable()){
					SpriteManager manager = SpriteManager.instance();
					List<Agent> agents = manager.getAgentList();
					for(Agent agent: agents){
						Node checkedAgent = new Node(null, agent.getCoordinates().x, agent.getCoordinates().y);
					if(checkedAgent.c.x != tempNode.c.x||checkedAgent.c.y!=tempNode.c.y)
					out.add(tempNode);	
					}
					if (agents.size() == 0)
						out.add(tempNode);	
					
				}
			}
		}
	
		return out;
	}
	
	private class Node implements Comparable<Node>{
		Coordinate c;
		Node parent;
		double f = 0;
		
		public Node(){}
		public Node(Node p,int x, int y){
			this.parent = p;
			this.c = new Coordinate(x,y,0);
		}
		@Override
		public int compareTo(Node o) {
			if(f <  o.f) return -1;
			if(f >  o.f) return 1;
			else return 0;
		}
		
	}
}
