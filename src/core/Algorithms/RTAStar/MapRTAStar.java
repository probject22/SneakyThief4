package core.Algorithms.RTAStar;

import core.Algorithms.PathFinder;
import core.Algorithms.RTAStar.RTAStar;
import core.Algorithms.RTAStar.RTAStar.Node;
import core.Map;
import dataContainer.Coordinate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;


/**
 * Created by Sina copied from Stan on 15/05/15.
 */
public class MapRTAStar extends RTAStar<Coordinate, Coordinate> implements PathFinder<Coordinate> {

	public double getCost(Node<Coordinate> from, Node<Coordinate> to) {
        int counter = 0;
        int x = min(from.element.x, to.element.x);
        int y = min(from.element.y, to.element.y);

        int toX = max(from.element.x, to.element.x);
        int toY = max(from.element.y, to.element.y);

        while(x < toX && y < toY){
            counter++;
            x++;
            y++;
        }

        while(x < toX){
            counter++;
            x++;
        }

        while(y < toY){
            counter++;
            y++;
        }

        return counter;
    }

    @Override
    /**
     * Returns the direct distance between two nodes (coordinates on the map)
     */
    public double getHeuristic(Node<Coordinate> from, Node<Coordinate> to) {
        return from.element.distance(to.element);
    }

    private Map map; //map of the world

    /**
     * Constructor
     * @param map
     */
    public MapRTAStar(Map map){
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

        // all neighbouring coordinates
        List<Coordinate> neighbourCoordinates = neighbourCoordinates(node.element);

        //loop through neighbouring coordinates
        for (Coordinate coordinate : neighbourCoordinates)
            // if an agent can move there
            if(map.isMoveable(coordinate))
                //create a new node
                neighbours.add(createNode(coordinate, node));
        

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

	@Override
	public int getPathLengt() {
		// TODO Auto-generated method stub
		return 1;
	}
}
