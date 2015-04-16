/**
 *
 */
package core.sprite;

import core.actions.*;
import core.events.Event;
import dataContainer.Coordinate;
import dataContainer.MoveDirection;

/**
 *
 * The integral intelligent class.
 *
 * The agent will have various systems:
 *
 * A belief that represents all that the agents knows and thinks about the world around him.
 *
 * A Plan that consists of a sequence of actions that an agent wants to perform.
 *
 * An interpreter that takes on events from the world around the agent and incorporates them
 * into the belief.
 *
 * A duration which denotes how much duration the agent has in total spent making actions.
 *
 *
 * @author ing. R.J.H.M. Stevens
 */
public class Agent extends Sprite {

    public Agent(Coordinate coords) {
        super(coords);
    }

	/**
	 * Method that processes events to update the belief state of the agent. The simulator will
	 * hand events to the agents using this method.
	 * @param event
	 */
	public void giveEvent(Event event){

	}

	/**
	 * Get the action that the agent currently wants to perform. This will consist of only a single
	 * square with a certain speed, and possibly a turn.
	 * @return
	 */
	public Action getAction(){
		Action action = new Action();
		//Default action
		action.addActionElement(new Move(1.0));
		action.addActionElement(new Turn(Math.toRadians(45.0), 1));
		action.addActionElement(new Wait(Math.random()));
		return action;
	}
	
	public void addTimeToKey(double time){
		timeKey += time;
		if (timeKey < 0){
			System.err.println("The value of the timekey was to big and became negative");
			System.exit(-1);
		}
	}
	public double getTimeKey(){
		return timeKey;
	}
	
	public double getMinVisionRange(){
		return this.minVisionRange;
	}
	
	public double getMaxVisionRange(){
		return this.maxVisionRange;
	}
	
	public double getVisionAngleRad(){
		return Math.toRadians(this.visionAngle);
	}
	
	/* the vision variable */
	private double minVisionRange = 0;
	private double maxVisionRange = 10;
	private double visionAngle = 45;
	
	private double timeKey;
}
