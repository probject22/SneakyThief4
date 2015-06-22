package core.Algorithms.RTAStar2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import core.Map;
import core.Algorithms.PathFinder;
import core.Algorithms.AStar.AStar;
import core.Algorithms.RTAStar2.RTAStar.Node;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;

public class MapAdapter implements PathFinder<Coordinate> {
	List<Coordinate> outPutList;
	public MapAdapter(Map map){ this.map = map; }
	
	public List<Coordinate> getPath(Map map, Coordinate from, Coordinate to) {
		RTAStar pathfinder = new RTAStar();
		
		Node start = toGraph(map, from, pathfinder);
		Node end = pathfinder.new Node();
		end.c = to;
		
		List<Node> path = pathfinder.getShortestPath(start, end);
		
		List<Coordinate> result = new ArrayList<>();
		
		for(Node n : path)
			result.add(n.c);
		return result;
	
	}

	public Node toGraph(Map map, Coordinate from, RTAStar pathfinder) {
		GridState[][] grid = map.getMap();
		List<Node> openNodes = new ArrayList<>();
		List<Node> closedNodes = new ArrayList<>();
		
		Node start = pathfinder.new Node();
		start.c = from;
		openNodes.add(start);
		
		while (openNodes.size() != 0){
			Node best =  openNodes.remove(0);
			openNodes.remove(best);
			closedNodes.add(best);
			
			ArrayList<Node> neighbors = getNeighbors(best, pathfinder);
			
			
			for (Node neighbor: neighbors){
			//calculate the values of the neighbors
				boolean found = false;
				neighbor.parent = null;
				//TODO set cost etc
				
				for (Node tempOpenNode: openNodes){
					if (tempOpenNode.c.x == neighbor.c.x && tempOpenNode.c.y == neighbor.c.y){
						best.neighbours.put(tempOpenNode, grid[neighbor.c.x][neighbor.c.y].getCost());
						found = true;
						break;
					}
				}
				if (!found){
					for (Node tempClosedNode: closedNodes){
						if (tempClosedNode.c.x == neighbor.c.x && tempClosedNode.c.y == neighbor.c.y){
							best.neighbours.put(tempClosedNode, grid[neighbor.c.x][neighbor.c.y].getCost());
							found = true;
							break;
						}
					}
				}
				if (!found){
					openNodes.add(neighbor);
					best.neighbours.put(neighbor, grid[neighbor.c.x][neighbor.c.y].getCost());
				}
			}
		}
	
		return start;
		/*
		GridState[][] grid = map.getMap();

		List<Node> open = new ArrayList<>();
		List<Coordinate> closed = new ArrayList<>();

		HashMap<Coordinate, Node> madeNodes = new HashMap<>();

		Node start = pathfinder.new Node();
		start.c = from;

		madeNodes.put(from, start);
		
		open.add(start);

		while (!open.isEmpty()) {
			
			Node current = open.remove(0);

			for (Coordinate neighbour : current.c.neighbourCoordinates()) {
				if (0 <= neighbour.x && 0 <= neighbour.y && grid.length > neighbour.x && grid[0].length > neighbour.y )
				if (grid[neighbour.x][neighbour.y].moveable()) {
					Node n;
					boolean found = false;
					for(Coordinate check : madeNodes.keySet()){
						if (check.x == neighbour.x  && check.y == neighbour.y){
							found = true;
							break;
						}
					}
					if (!found) {
						n = pathfinder.new Node();
						n.c = neighbour;
						madeNodes.put(neighbour, n);
						open.add(n);
					} else {
						n = madeNodes.get(neighbour);
					}
					current.neighbours.put(n,
							grid[neighbour.x][neighbour.y].getCost());
					n.neighbours.put(current,
							grid[current.c.x][current.c.y].getCost());
				}
			}
			closed.add(current.c);
		}
		System.err.println("something is not wrong with finishing the graph");
		return start;
	*/
	}
	
	protected ArrayList<Node> getNeighbors(Node node, RTAStar pathfinder){
		ArrayList<Node> options = new ArrayList<Node>();
		ArrayList<Node> out = new ArrayList<Node>();
		options.add(pathfinder.new Node(node.c.x-1, node.c.y-1));
		options.add(pathfinder.new Node(node.c.x  , node.c.y-1));
		options.add(pathfinder.new Node(node.c.x+1, node.c.y-1));
		options.add(pathfinder.new Node(node.c.x-1, node.c.y));
		options.add(pathfinder.new Node(node.c.x+1, node.c.y));
		options.add(pathfinder.new Node(node.c.x-1, node.c.y+1));
		options.add(pathfinder.new Node(node.c.x  , node.c.y+1));
		options.add(pathfinder.new Node(node.c.x+1, node.c.y+1));
		
		GridState[][] grid = map.getCopyOfMap();
		
		for (Node tempNode: options){
			if (tempNode.c.x < grid.length && tempNode.c.x >= 0 && tempNode.c.y < grid[0].length && tempNode.c.y >= 0){
				if (grid[tempNode.c.x][tempNode.c.y].moveable()){
					SpriteManager manager = SpriteManager.instance();
					List<Agent> agents = manager.getAgentList();
					boolean found = false;
					for(Agent agent: agents){
						Node checkedAgent = pathfinder.new Node(agent.getCoordinates().x, agent.getCoordinates().y);
						if(checkedAgent.c.x == tempNode.c.x && checkedAgent.c.y == tempNode.c.y)
							found = true;
					}
					if (!found || agents.size() == 0)
						out.add(tempNode);
					
				}
			}
		}
	
		return out;
	}
	
	Map map;

	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		GridState[][] grid = map.getCopyOfMap();
		//if (outPutList == null || outPutList.size() < 3 || !grid[outPutList.get(outPutList.size()-2).x][outPutList.get(outPutList.size()-2).y].moveable())
		outPutList = getPath(map, from, to);
		if (outPutList == null)
			return null;
		Coordinate out;
		if (outPutList.size() > 2){
			
			out =   outPutList.remove(outPutList.size()-2);
		}
		else
		out =  outPutList.remove(0);
		return out;
	}

	@Override
	public int getPathLengt() {
		// TODO Auto-generated method stub
		return 1;
	}

}
