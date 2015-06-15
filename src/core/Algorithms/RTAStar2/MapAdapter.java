package core.Algorithms.RTAStar2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.Map;
import core.Algorithms.RTAStar2.RTAStar.Node;
import dataContainer.Coordinate;
import dataContainer.GridState;

public class MapAdapter {

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
				if (grid[neighbour.x][neighbour.y].moveable()) {
					Node n;
					if (!closed.contains(neighbour)) {
						n = pathfinder.new Node();
						n.c = neighbour;
						madeNodes.put(neighbour, n);
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
		
		return start;

	}

}
