package core.Algorithms;

import dataContainer.Coordinate;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 *
 * Implementation of the CostFunction interface for use with the AStar class.
 *
 * Created by Stan on 26/04/15.
 */
public class MapCostFunction implements CostFunction<Coordinate>{

    /**
     * Counts the amount of tiles between two coordinates on the map (assuming no obstructions).
     * Method will generally be used to evaluate adjacent coordinates.
     * @param from
     * @param to
     * @return
     */
    @Override
    // currently counts moves
    // can be improved to not count moves, but time
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
}
