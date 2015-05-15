package core.Algorithms.BES;

import dataContainer.Coordinate;

import java.util.*;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

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

        // Get the set of escape directions
        Collection<Double> escapeDirections = getEscapeDirections(agent, otherAgents, intruder);

        List<Coordinate> agents = new ArrayList<>();
        agents.add(agent);
        agents.addAll(otherAgents);

        // Determine the optimal matting of agents to directions
        Map<Coordinate, Double> directionsMap = getDirectionsMapping(agents, intruder, escapeDirections);

        // Select the escape direction assigned to the current agent
        double escapeDirection = directionsMap.get(agent);

        // Determine the blocking location
        Coordinate blockingLocation = getBlockingLocation(agent, intruder, escapeDirection);

        return blockingLocation;
    }

    private Map<Coordinate, Double> getDirectionsMapping(Collection<Coordinate> allAgents, Coordinate intruder, Collection<Double> escapeDirections) {
        Map<Coordinate,Double> directionsMapping = new HashMap<>();

        Map<Coordinate, Double> references = new HashMap<>();

        //calculate reference points
        for (Double escapeDirection : escapeDirections) {
            Coordinate c = new Coordinate(
                    intruder.x + (cos(escapeDirection) > 0 ? 1 : -1),
                    intruder.y + (sin(escapeDirection) > 0 ? 1 : -1),0);
            references.put(c, escapeDirection);
        }

        Collection<Coordinate> agents = new ArrayList<>();
        agents.addAll(allAgents);

        for (Map.Entry<Coordinate, Double> entry : references.entrySet()) {
            Coordinate min = new Coordinate(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);

            //Find agent closest to the reference point
            for (Coordinate agent : agents) {
                if (entry.getKey().distance(agent) < entry.getKey().distance(min))
                    min = agent;
            }

            agents.remove(min);
            directionsMapping.put(min, entry.getValue());

        }


        return directionsMapping;
    }

    private Coordinate getBlockingLocation(Coordinate agent, Coordinate intruder, double escapeDirection) {


        return intruder.addPolar(escapeDirection,5);

        // Enhanced method
        /*
        double e = 0.5;
        double ep = 0.05;
        double alpha = Math.min(Math.abs(escapeDirection - intruder.getAngle(agent)),Math.abs(intruder.getAngle(agent)- escapeDirection) );

        if ( e < alpha && alpha < 180 - e){
        //TODO: implement dependence on speed of agents
            if (sin(alpha) * (1+ep) <= 1){
                double theta = Math.asin((sin(alpha)*(1+ep));
                if(theta < 180 - alpha - e){
                    //TODO: continue here
                    double pdir = // predator direction see fig. 13
                    Coordinate bl = // intersection pdir and escapeDirection
                    return bl;
                }
            }
        } else {
            return intruder;
        }

        return null;
        */
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
