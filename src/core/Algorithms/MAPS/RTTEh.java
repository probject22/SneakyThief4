package core.Algorithms.MAPS;

import core.Map;
import dataContainer.Coordinate;
import dataContainer.MoveDirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Stan on 26/04/15.
 */
public class RTTEh {
    Map map;
    Coordinate current;

    public MoveDirection getMoveDirection(){
        List<MoveDirection> open = new ArrayList<>();

        //mark all the moving directions as open
        Collections.addAll(open, MoveDirection.values());

        double[] directions = new double[0];
        List<MoveDirection> proposalDirections = new ArrayList<>();
        for (double direction : directions) {
            Obstacle obstacle = castRay(direction);
            if (obstacle == null)
                continue;
            Border border = extractBorder(obstacle);
            List<MoveDirection> closedDirections = detectClosedDirections(border);

            proposalDirections.add(determineDirection(border));
        }

        return mergeResults(proposalDirections);


    }

    private MoveDirection mergeResults(List<MoveDirection> proposalDirections) {
        return null;
    }

    private MoveDirection determineDirection(Border border) {
        return null;
    }

    private List<MoveDirection> detectClosedDirections(Border border) {
        return null;
    }

    private Border extractBorder(Obstacle obstacle) {
        return null;
    }

    private Obstacle castRay(double direction) {
        return null;
    }
}
