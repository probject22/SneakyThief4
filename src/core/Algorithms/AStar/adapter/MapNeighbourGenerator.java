package core.Algorithms.AStar.adapter;

import core.Algorithms.AStar.NeighbourGenerator;
import core.Algorithms.AStar.Node;
import core.Map;

import dataContainer.Coordinate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Implementation of NeighbourGenerator interface. Generates neighbouring nodes based on a map. Nodes that are generates
 * are those coordinates adjacent to the agent.
 *
 * Created by Stan on 26/04/15.
 */
public class MapNeighbourGenerator implements NeighbourGenerator<Coordinate> {


    private final Map map; //map of the world

    /**
     * Constructor
     * @param map
     */
    public MapNeighbourGenerator(Map map){
        this.map = map;
    }

    /**
     * returns the list of neighouring nodes
     * @param node
     * @return
     */
    @Override
    public List<Node> getNeighbours(Node<Coordinate> node) {
        //result list
        List<Node> neighbours = new ArrayList<>();

        // all neighbouring coordinates
        List<Coordinate> neighbourCoordinates = neighbourCoordinates(node.element);

        //loop through neighbouring coordinates
        for (Coordinate coordinate : neighbourCoordinates) {
            // if an agent can move there
            if(map.isMoveable(coordinate))
                //create a new node
                neighbours.add(createNode(coordinate, node));
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
        Node<Coordinate> node = new Node();
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
