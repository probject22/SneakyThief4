package core.Algorithms;

import dataContainer.Coordinate;

/**
 *
 * Implementation of the HeuristicFunction interface for use with AStar for planning on a map.
 *
 *
 * Created by Stan on 26/04/15.
 */
public class MapHeuristicFunction implements HeuristicFunction<Coordinate>{
    @Override
    /**
     * Returns the direct distance between two nodes (coordinates on the map)
     */
    public double getHeuristic(Node<Coordinate> from, Node<Coordinate> to) {
        return from.element.distance(to.element);
    }
}
