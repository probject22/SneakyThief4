package core.Algorithms;

import core.Map;

import dataContainer.Coordinate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stan on 26/04/15.
 */
public class MapNeighbourGenerator implements NeighbourGenerator<Coordinate> {


    private final Map map;

    public MapNeighbourGenerator(Map map){
        this.map = map;
    }

    @Override
    public List<Node> getNeighbours(Node<Coordinate> node) {
        List<Node> neighbours = new ArrayList<>();

        List<Coordinate> neighbourCoordinates = neighbourCoordinates(node.element);
        for (Coordinate coordinate : neighbourCoordinates) {
            if(map.isMoveable(coordinate))
                neighbours.add(createNode(coordinate, node));
        }

        return neighbours;
    }

    private Node<Coordinate> createNode(Coordinate coordinate, Node<Coordinate> parent){
        Node<Coordinate> node = new Node();
        node.element = coordinate;
        node.parent = parent;
        return node;
    }

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
