package core.Algorithms;

import java.util.ArrayList;
import java.util.Comparator;

import core.Map;
import core.Algorithms.AStar.AStar.Node;
import dataContainer.Coordinate;

public class ClinchAStar implements PathFinder<Coordinate>{
	Map map;
	
	protected ArrayList<Node> visited = new ArrayList<Node>();
	
	public ClinchAStar(Map map){
		this.map = map;
	}
	
	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		if(!map.isMoveable(to)) return null;
		ArrayList<Node> neighbours = new ArrayList<Node>();
		return null;
	}
	
	public void addVisit(Node n){
		n.visits = n.visits+1;
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
		public int visits = 0;
		
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
        public int visitCompare(Node n1, Node n2){
        	if(n1.visits>n2.visits){return 1;}
        	else if(n1.visits>n2.visits){return 2;}
        	else return 0;
        }
        
    }

}
