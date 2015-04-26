package core.Algorithms.AStar.adapter;

import core.Algorithms.AStar.Node;
import core.Algorithms.AStar.ResultInterpreter;
import dataContainer.Coordinate;
import dataContainer.MoveDirection;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class that converts the output node from the astar algorithm to a list of move directions. Maybe this class should
 * be adapted to return moves instead of directions.
 *
 * Created by Stan on 26/04/15.
 */
public class MapResultInterpreter implements ResultInterpreter<MoveDirection, Coordinate> {
    @Override
    public List<MoveDirection> getResult(Node<Coordinate> node) {
        List<MoveDirection> moves = new ArrayList<>();

        while(node != null){
            moves.add(getMoveDirection(node.parent, node));
            node = node.parent;
        }

        return moves;
    }

    private MoveDirection getMoveDirection(Node<Coordinate> from, Node<Coordinate> to){
        if(from.element.x == to.element.x-1 && from.element.y-1 == to.element.y)
            return MoveDirection.SW;
        if(from.element.x == to.element.x && from.element.y-1 == to.element.y)
            return MoveDirection.S;
        if(from.element.x == to.element.x+1 && from.element.y-1 == to.element.y)
            return MoveDirection.SE;
        if(from.element.x == to.element.x-1 && from.element.y == to.element.y)
            return MoveDirection.W;
        if(from.element.x == to.element.x+1 && from.element.y == to.element.y)
            return MoveDirection.E;
        if(from.element.x == to.element.x-1 && from.element.y+1 == to.element.y)
            return MoveDirection.NW;
        if(from.element.x == to.element.x && from.element.y+1 == to.element.y)
            return MoveDirection.N;
        if(from.element.x == to.element.x+1 && from.element.y+1 == to.element.y)
            return MoveDirection.NE;
        return null;
    }
}
