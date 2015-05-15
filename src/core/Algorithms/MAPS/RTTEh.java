package core.Algorithms.MAPS;

import core.Algorithms.PathFinder;
import core.Map;
import dataContainer.Coordinate;
import dataContainer.GridState;
import dataContainer.MoveDirection;

import java.util.*;

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
            List<Obstacle> obstacles = castRay(direction, current);

            //If the ray does not hit an obstacle continue
            if (obstacles.isEmpty())
                continue;

            for (Obstacle obstacle : obstacles) {
                //Calculate the features of the obstacle
                obstacle.calculateFeatures(current, target, direction);

                //Check whether the obstacle blocks a direction
                //remove from open directions
                open.remove(detectClosedDirections(obstacle));

                proposals.add(obstacle.getDirection());
            }
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

    private List<Obstacle> castRay(double direction, Coordinate from) {

        Coordinate to = from.addPolar(
                direction,
                Math.max(map.getMapHeight(),map.getMapHeight())
        );

        java.util.Map<Coordinate, GridState> intersections = map.getIntersectingGridstates(from, to);

        Queue<Coordinate> obstructionCoordinates = new ArrayDeque<>();

        for (java.util.Map.Entry<Coordinate, GridState> entry : intersections.entrySet()) {
            if( !entry.getValue().moveable() )
                obstructionCoordinates.add(entry.getKey());
        }

        Queue<Coordinate> evaluate = new ArrayDeque<>();
        List<Coordinate> history = new ArrayList<>();
        evaluate.add(obstructionCoordinates.poll());
        ArrayList<Obstacle> obstacles = new ArrayList<>();

        while (!evaluate.isEmpty()) {

            Queue<Coordinate> leafs = new ArrayDeque<>();

            boolean grown = true;

            while (grown) {
                Coordinate current = evaluate.poll();
                int count = 0;
                grown = false;
                for (Coordinate obstructionCoordinate : obstructionCoordinates) {
                    if (obstructionCoordinate.isNeighbour(current)) {
                        count++;
                        if (!history.contains(obstructionCoordinate)) {
                            evaluate.add(obstructionCoordinate);
                            grown = true;
                        }
                    }
                }
                if (count < 8) {
                    leafs.add(current);
                }
                history.add(current);
            }

            Obstacle obstacle = new Obstacle();
            while(!leafs.isEmpty())
                obstacle.addCoordinate(leafs.poll());

            //TODO: convert leafs into obstacle
            //TODO: I don't really know how to solve this... I now have a queue of coordinates, I just need to somehow
            //TODO: turn them into an obstacle for RTTEh


            obstacles.add(obstacle);
        }






        return obstacles;
    }

}
