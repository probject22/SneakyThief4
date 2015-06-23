package core.Algorithms.reverseRTAStar;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

import core.Map;
import core.Algorithms.PathFinder;
import core.Algorithms.ThiefPath;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;

public class MapReverseRTAStar extends ReverseRTAStar<Coordinate, Coordinate> implements ThiefPath<Coordinate>{
	
	public double getCost(Node<Coordinate> from, Node<Coordinate> to) {
        double counter = 0;
        counter = this.map.getCopyOfMap()[to.element.x][to.element.y].getThiefCost();
//        int x = min(from.element.x, to.element.x);
//        int y = min(from.element.y, to.element.y);
//
//        int toX = max(from.element.x, to.element.x);
//        int toY = max(from.element.y, to.element.y);
//
//        while(x < toX && y < toY){
//            counter++;
//            x++;
//            y++;
//        }
//
//        while(x < toX){
//            counter++;
//            x++;
//        }
//
//        while(y < toY){
//            counter++;
//            y++;
//        }

        return counter;
    }

    @Override
    /**
     * Returns the direct distance between two nodes (coordinates on the map)
     */
    public double getHeuristic(Node<Coordinate> from, Node<Coordinate> to) {
        return from.element.distance(to.element);
    }

    private final Map map; //map of the world

    /**
     * Constructor
     * @param map
     */
    public MapReverseRTAStar(Map map){
        this.map = map;
    }

    /**
     * returns the list of neighouring nodes
     * @param node
     * @return
     */
    @Override
    public List<Node<Coordinate>> getNeighbours(Node<Coordinate> node) {
        //result list
        List<Node<Coordinate>> neighbours = new ArrayList<>();
        
    	ArrayList<Coordinate> options = new ArrayList<Coordinate>();
		ArrayList<Coordinate> out = new ArrayList<Coordinate>();
		
		options.add(new Coordinate(node.element.x-1, node.element.x-1,	0));
		options.add(new Coordinate(node.element.x  , node.element.x-1,	0));
		options.add(new Coordinate(node.element.x+1, node.element.x-1,	0));
		options.add(new Coordinate(node.element.x-1, node.element.x,	0));
		options.add(new Coordinate(node.element.x+1, node.element.x,	0));
		options.add(new Coordinate(node.element.x-1, node.element.x+1,	0));
		options.add(new Coordinate(node.element.x  , node.element.x+1,	0));
		options.add(new Coordinate(node.element.x+1, node.element.x+1,	0));
		
		GridState[][] grid = map.getCopyOfMap();
		
		for (Coordinate tempCoord: options){
			if (tempCoord.x < grid.length && tempCoord.x >= 0 && tempCoord.y < grid[0].length && tempCoord.y >= 0){
				if (grid[tempCoord.x][tempCoord.y].moveable()){
					SpriteManager manager = SpriteManager.instance();
					List<Agent> agents = manager.getAgentList();
					for(Agent agent: agents){
						Coordinate agentCoords = agent.getCoordinates();
							if(agentCoords.x != tempCoord.x||agentCoords.y!=tempCoord.y){
								Node<Coordinate> tempNode = new Node<Coordinate>();
								tempNode.element = tempCoord;
								neighbours.add(tempNode);	
							}
					}
					if (agents.size() == 0){
						Node<Coordinate> tempNode = new Node<Coordinate>();
						tempNode.element = tempCoord;
						neighbours.add(tempNode);	
					}
					
				}
			}
		}
	
		return neighbours;
    }

    /**
     * Initializes a node with relevant coordinate and parent.
     * @param coordinate
     * @param parent
     * @return
     */
    private Node<Coordinate> createNode(Coordinate coordinate, Node<Coordinate> parent){
        Node<Coordinate> node = new Node<>();
        node.element = coordinate;
        node.parent = parent;
        return node;
    }

    /**
     * Generates neighbouring coordinates without checking whether the agent could move there.
     * @param coordinate
     * @return
     */
    private List<Coordinate> neighbourCoordinates(Coordinate coordinate){
        List<Coordinate> coordinates = new ArrayList<>();
        int x = coordinate.x;
        int y = coordinate.y;

        coordinates.add(new Coordinate(x-1  ,y-1    ,0));
        coordinates.add(new Coordinate(x    ,y-1    ,0));
        coordinates.add(new Coordinate(x+1  ,y-1    ,0));
        coordinates.add(new Coordinate(x-1  ,y      ,0));
        coordinates.add(new Coordinate(x+1  ,y      ,0));
        coordinates.add(new Coordinate(x-1  ,y+1    ,0));
        coordinates.add(new Coordinate(x    ,y+1    ,0));
        coordinates.add(new Coordinate(x+1  ,y+1    ,0));

        return coordinates;

    }
}
