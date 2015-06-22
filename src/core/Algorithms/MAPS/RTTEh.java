package core.Algorithms.MAPS;

import core.Algorithms.PathFinder;
import core.Map;
import dataContainer.Coordinate;
import dataContainer.MoveDirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 *
 * RTTEh algorithm
 *
 * Created by Stan on 26/04/15.
 */
public class RTTEh implements PathFinder<Coordinate> {
    Map map;
    
    /**
     * Constructor
     * @param map
     */
    public RTTEh(Map map){
        this.map = map;
    }

    public Coordinate getShortestPath(Coordinate current, Coordinate target){
        List<Coordinate> open = new ArrayList<>();

        //mark all the neighbour cells as open
        open.addAll(generateNeighbours(current));

        
        java.util.Map<Double,Double> proposals = new HashMap<>();

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

            proposals.put(obstacle.getDirection(), obstacle.getEstimatedDistance());

        }
        
        

        return mergeResults(current,target,proposals);
        

    }

    private Coordinate mergeResults(Coordinate current, Coordinate target, java.util.Map<Double,Double> proposals) {
    	
    	double suggestedDirection = (Double) null;
    	
    	List<Coordinate> neighbours = generateNeighbours(current);
        //check if the agent is not fully blocked.
        if(StreamSupport.stream(neighbours.spliterator(), true).allMatch(o -> o == null))
        	return null;
        
        
        
        //Direct angle to target.
        double flyDirection = current.getAngle(target);
        
        //Iterate through proposals find the most direct paths using three scenarios 
        //select the one with the longest distance
        double longestPath = 0;
        //scenario 1 - 90 degrees
        for(Double pDirection : proposals.keySet()){
        	 if (Math.abs(pDirection - flyDirection) <= (Math.PI)/4)
             	if (proposals.get(pDirection) > longestPath) {
             		longestPath = proposals.get(pDirection);
             		suggestedDirection = pDirection;
             	}
        }
        // scenario 2 - 180 degrees
        if (longestPath == 0)
        	for(Double pDirection : proposals.keySet()){
           	 if (Math.abs(pDirection - flyDirection) <= (Math.PI)/2)
                	if (proposals.get(pDirection) > longestPath) {
                		longestPath = proposals.get(pDirection);
                		suggestedDirection = pDirection;
                	}
           }
        // scenario 3 - 360 degrees
        if (longestPath == 0)
        	for(Double pDirection : proposals.keySet()){
                	if (proposals.get(pDirection) > longestPath) {
                		longestPath = proposals.get(pDirection);
                		suggestedDirection = pDirection;
                	}
           }
        
        //scenario 4 no obstacles around
        if (longestPath == 0)
        	return target;
        
        Coordinate result = new Coordinate();
        
        result.x = (int) (longestPath*Math.cos(suggestedDirection));
        result.y = (int) (longestPath*Math.sin(suggestedDirection));
        
        return result;
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

    protected List<Coordinate> generateNeighbours(Coordinate c) {
		return null;
	}

    private Obstacle castRay(double direction) {
        return null;
    }

	@Override
	public int getPathLengt() {
		// TODO Auto-generated method stub
		return 1;
	}

}
