package core.Algorithms.MAPS;


import dataContainer.Coordinate;

import static java.lang.Math.*;

/**
 * Created by Stan on 26/04/15.
 */
public class Obstacle {

    // The point where the raycast hit the obstacle
    Vertex hitPoint;

    //TODO implement all these calculations (see paper MAPS)

    private boolean behindOfLeft;
    private boolean behindOfRight;

    private double innerLeftmostDirection;
    private double innerRightmostDirection;
    private double outerLeftmostDirection;
    private double outerRightmostDirection;

    private double distanceAgentOuterLeftmostPoint;
    private double distanceAgentLeftAlternativePoint;

    private double dLeft;
    private double dRight;
    private double dLeftAlter;
    private double dRightAlter;
    private double dLeftInner;
    private double dRightInner;

    private double targetDirection;

    private boolean innerLeftZeroAngleBlocking;
    private boolean innerRightZeroAngleBlocking;
    private boolean outerLeftZeroAngleBlocking;
    private boolean outerRightZeroAngleBlocking;

    private boolean insideOfLeft;
    private boolean insideOfRight;
    private boolean insideOfInnerLeft;
    private boolean insideOfInnerRight;
    private double epsilon = 0.001;

    private Coordinate outerLeftmostPoint;


    public void calculateFeatures(Coordinate agent, Coordinate target, double rayDirection){


        //TODO: Feature calculation (See maps paper)

        // left tour
        Vertex vertex = hitPoint;
        double cumulativeLeftAngle = agent.getAngle(vertex.coordinate());

        outerLeftmostDirection = max(rayDirection, cumulativeLeftAngle);

        boolean innerLeftMostFound = false;
        while ( vertex != hitPoint){
            vertex = hitPoint.left;
            double angleIncrement = vertex.right.coordinate().getAngle(vertex.coordinate());
            cumulativeLeftAngle += angleIncrement;

            // calculate the outer leftmost angle
            if( cumulativeLeftAngle > outerLeftmostDirection ){
                outerLeftmostDirection = cumulativeLeftAngle;
                outerLeftmostPoint = vertex.coordinate();
            }


            // calculate the inner leftmost angle
            if ( !innerLeftMostFound && angleIncrement < 0) {
                innerLeftmostDirection = cumulativeLeftAngle - angleIncrement;
                innerLeftMostFound = true;
            }
        }

        if(!innerLeftMostFound)
            innerLeftmostDirection = epsilon;


    }
    double estimatedDistance = Double.MAX_VALUE;
    double direction = 0;
    boolean targetBlocked = false;

    public void setProposal(Coordinate agent, Coordinate target, double rayDirection){

        calculateFeatures(agent, target, rayDirection);
        targetBlocked = false;
        estimatedDistance = Double.MAX_VALUE;
        direction = 0;

        if((behindOfLeft && !insideOfRight) || (behindOfRight && !insideOfLeft)){
            //Case 1:
            if(outerLeftmostDirection + outerRightmostDirection >= 2* PI ){
                //Case1.1:
                if(distanceAgentOuterLeftmostPoint < distanceAgentLeftAlternativePoint){
                    //Case 1.1.1:
                    estimatedDistance = min(dLeft, dRightAlter);
                    direction = outerLeftmostDirection;
                } else {
                    // Case 1.1.2:
                    estimatedDistance = min(dLeftAlter, dRight);
                }
            } else {
                // Case 1.2:
                if( dLeft < dRight ){
                    // Case 1.2.1:
                    estimatedDistance = dLeft;
                    direction = outerLeftmostDirection;
                } else{
                    // Case 1.2.2:
                    estimatedDistance = dRight;
                    direction = outerRightmostDirection;
                }
            }
            targetBlocked = true;
        } else if ( behindOfLeft ) {
            // Case 2:
            if( targetDirection != 0 && outerRightZeroAngleBlocking){
                // Case 2.1
                estimatedDistance = dLeft;
                direction = outerLeftmostDirection;
            } else {
                // Case 2.2
                estimatedDistance = dRightInner;
                direction = innerRightmostDirection;
            }
            targetBlocked = true;
        } else if ( behindOfRight ) {
            // Case 3:
            if( targetDirection != 0 && outerLeftZeroAngleBlocking ) {
                //Case 3.1:
                estimatedDistance = dRight;
                direction = outerRightmostDirection;
            } else {
                // Case 3.2:
                estimatedDistance = dLeftInner;
                direction = innerLeftmostDirection;
            }
            targetBlocked = true;
        } else {
            // Case 4:
            if (insideOfLeft && !insideOfRight
                    && innerLeftZeroAngleBlocking && !insideOfInnerLeft) {
                // Case 4.1:
                estimatedDistance = dLeftInner;
                direction = innerLeftmostDirection;
                targetBlocked = true;
            } else if( insideOfRight && !insideOfLeft && innerRightZeroAngleBlocking && !insideOfInnerRight){
                // Case 4.2:
                estimatedDistance = dRightInner;
                direction = innerRightmostDirection;
                targetBlocked = true;
            }
        }
    }

    public double getDirection() {
        return direction;
    }

    public double getEstimatedDistance() {
        return estimatedDistance;
    }


    /**
     * Inner class used to describe the border
     */
    private class Vertex{
        int x, y;
        Vertex left, right;

        Coordinate coordinate() {
            return new Coordinate(x,y,0);
        }
    }
}
