package core.Algorithms.BES;

import dataContainer.Coordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static java.lang.Math.PI;

/**
 * Created by Stan on 29/04/15.
 */
public class BES {
    public Coordinate getBlockingLocation(
            Coordinate agent,
            Collection<Coordinate> otherAgents,
            Coordinate intruder ){

        // If this agent is closest to the intruder, the agent should go towards the intruder
        // this also catches the case where there is only one agent.
        if(isClosestToIntruder(agent,otherAgents,intruder))
            return intruder;

        Collection<Double> escapeDirections = getEscapeDirections(agent, otherAgents, intruder);

        Map<Coordinate, Double> directionsMap = getDirectionsMapping(agent, otherAgents, intruder, escapeDirections);

        double escapeDirection = directionsMap.get(agent);

        Coordinate blockingLocation = getBlockingLocation(agent, intruder, escapeDirection);

        return blockingLocation;
    }

    private Map<Coordinate, Double> getDirectionsMapping(Coordinate agent, Collection<Coordinate> otherAgents, Coordinate intruder, Collection<Double> escapeDirections) {
        // TODO: implement
        return null;
    }

    private Coordinate getBlockingLocation(Coordinate agent, Coordinate intruder, double escapeDirection) {
        return null;
    }

    private Collection<Double> getEscapeDirections(Coordinate agent, Collection<Coordinate> otherAgents, Coordinate intruder) {
        int n = otherAgents.size() + 1;

        Collection<Double> directions = new ArrayList<>();

        double direction = intruder.getAngle(agent);
        for(int i = 0; i < n; i++){
            directions.add(direction);
            direction += 2* PI / n;
        }
        return directions;
    }

    /**
     * Checks whether the agent is the closest to the intruder.
     * @param agent
     * @param otherAgents
     * @param intruder
     * @return
     */
    private boolean isClosestToIntruder(Coordinate agent, Collection<Coordinate> otherAgents, Coordinate intruder){
        for (Coordinate otherAgent : otherAgents) {
            if(otherAgent.distance(intruder) > agent.distance(intruder))
                return false;
        }
        return true;
    }
}
