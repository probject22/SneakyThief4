package core.Algorithms.MAPS;

import core.Algorithms.PathFinder;
import core.Map;
import dataContainer.Coordinate;
import dataContainer.MoveDirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * RTTEh algorithm
 *
 * Created by Stan on 26/04/15.
 */
public abstract class RTTEh {
    Map map;

    public Coordinate getMoveDirection(Coordinate current, Coordinate target){
        List<Coordinate> open = new ArrayList<>();

        //mark all the neighbour cells as open
        open.addAll(generateNeighbours(current));

        List<Double> proposals = new ArrayList<>();

        //generate directions
        double[] directions = getDirections(open.size());
        java.util.Map<Coordinate, Double> utilities = new HashMap<>();
        for (double direction : directions) {

            //Cast a ray in every direction
            //Get the obstacle it runs into
            Obstacle obstacle = castRay(direction);

            //If the ray does not hit an obstacle continue
            if (obstacle == null)
                continue;

            //Calculate the features of the obstacle
            obstacle.calculateFeatures(current,target,direction);

            //Check whether the obstacle blocks a direction
            //remove from open directions
            open.remove(detectClosedDirections(obstacle));

            proposals.add(obstacle.getDirection());
        }

        return mergeResults(proposals);


    }

    private Coordinate mergeResults(List<Double> proposals) {
        return null;
    }

    private double[] getDirections(int amountOfDirections) {
        double d = 2*Math.PI / amountOfDirections;
        double[] directions = new double[amountOfDirections];
        for (int i = 0; i < amountOfDirections; i++) {
            directions[i] = (.5 + i) * d % (Math.PI * 2);
        }
        return directions;
    }

    private void addDirection(java.util.Map<MoveDirection, Double> proposals,
                              Obstacle obstacle,
                              double rayDirection,
                              Coordinate agent,
                              Coordinate target) {
        obstacle.setProposal(agent, target, rayDirection);
        double direction = obstacle.getDirection();
        proposals.put(MoveDirection.angleToMoveDirection(direction), obstacle.getEstimatedDistance());

    }

    private List<Coordinate> detectClosedDirections(Obstacle obstacle) {
        return null;
    }

    protected abstract List<Coordinate> generateNeighbours(Coordinate c);

    private Obstacle castRay(double direction) {
        return null;
    }

}
