/**
 * 
 */
package core.Algorithms.RTAStar;

import java.util.ArrayList;
import java.util.Collections;
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
public class RealTimeAStar implements PathFinder<Coordinate> {
	Map map;
	
	/**
	 * 
	 */
	public RealTimeAStar(Map map) {
		this.map = map;
	}
	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		
		if(!map.isMoveable(to)) return null;
		
		Node start = new Node();
		start.c = from.clone();
		start.parent = null;
		start.f = start.c.distance(to);
		
		Node n = start;
		int MAX_ITERATION = 100000;
		int i = 0;
		while(!n.c.equals(to)){
			//System.out.println(n.c);
			List<Node> neighbours = neighbours(n);
			
			// don't visit a node twice
			for(Node no : neighbours){
				if(no.f == 0)
					no.f = no.c.distance(to) + 1;
			}
			Collections.sort(neighbours);
			
			n.f = neighbours.get(1).f;
			Node p = n;
			n = neighbours.get(0);
			n.parent = p;	
			if(i++ > MAX_ITERATION) break;
		}
		
		while (n.parent.parent != null)
			n = n.parent;
		
		return n.c;
	}
	
	public List<Node> neighbours(Node node) {
		ArrayList<Node> options = new ArrayList<Node>();
		ArrayList<Node> out = new ArrayList<Node>();
		
		options.add(new Node(node, node.c.x-1, node.c.y-1));
		options.add(new Node(node, node.c.x  , node.c.y-1));
		options.add(new Node(node, node.c.x+1, node.c.y-1));
		options.add(new Node(node, node.c.x-1, node.c.y));
		options.add(new Node(node, node.c.x+1, node.c.y));
		options.add(new Node(node, node.c.x-1, node.c.y+1));
		options.add(new Node(node, node.c.x  , node.c.y+1));
		options.add(new Node(node, node.c.x+1, node.c.y+1));
		
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