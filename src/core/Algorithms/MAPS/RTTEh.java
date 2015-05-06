package core.Algorithms.MAPS;

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
public class RTTEh {
    Map map;
    Coordinate current;

    public MoveDirection getMoveDirection(Coordinate target){
        List<MoveDirection> open = new ArrayList<>();

        //mark all the moving directions as open
        Collections.addAll(open, MoveDirection.values());

        double[] directions = new double[0];
        java.util.Map<MoveDirection, Double> proposalDirections = new HashMap<>();
        for (double direction : directions) {
            Obstacle obstacle = castRay(direction);
            if (obstacle == null)
                continue;
            List<MoveDirection> closedDirections = detectClosedDirections(obstacle);

            addDirection(proposalDirections, obstacle, direction, current, target);
        }

        return mergeResults(proposalDirections);


    }

    private MoveDirection mergeResults(java.util.Map<MoveDirection,Double> proposalDirections) {
        return null;
    }

    private void addDirection(java.util.Map<MoveDirection, Double> proposals, Obstacle obstacle, double rayDirection, Coordinate agent, Coordinate target) {
        obstacle.setProposal(agent, target, rayDirection);
        double direction = obstacle.getDirection();
        proposals.put(MoveDirection.angleToMoveDirection(direction), obstacle.getEstimatedDistance());

    }

    private List<MoveDirection> detectClosedDirections(Obstacle obstacle) {
        return null;
    }


    private Obstacle castRay(double direction) {
        return null;
    }
}
