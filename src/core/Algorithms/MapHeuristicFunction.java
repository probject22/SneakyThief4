package core.Algorithms;

import dataContainer.Coordinate;

/**
 * Created by Stan on 26/04/15.
 */
public class MapHeuristicFunction implements HeuristicFunction<Coordinate>{
    @Override
    public double getHeuristic(Node<Coordinate> from, Node<Coordinate> to) {
        return from.element.distance(to.element);
    }
}
