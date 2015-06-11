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
	Map map;
	
	/**
	 * 
	 */
	public RealTimeAStar(Map map) {
		this.map = map;
	}
	
	HashMap<Coordinate,Node> nodes = new HashMap<Coordinate,Node>();
	
	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		
		if(!map.isMoveable(to)) return null;
		
		Node start = new Node();
		start.c = from.clone();
		start.parent = null;
		start.f = start.c.distance(to);
		
		Node n = start;
		//int MAX_ITERATION = 1000000;
		//int i = 0;
		while(!n.c.equals(to)){
			
			List<Node> neighbours = neighbours(n);
			
			
			//if there are no possible moves return null
			if(neighbours.isEmpty())
				return null;
			
			//if there is only one possible move, make that move
			if(neighbours.size() == 1)
				return neighbours.get(0).c;
			
			// don't visit a node twice
			for(Node no : neighbours){
				if(no.f == 0)
					no.f = no.c.distance(to)+1;
			}
			
			// select the second best neighbour
			Collections.sort(neighbours);
			
			
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
