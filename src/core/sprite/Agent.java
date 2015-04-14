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
	 * @param event The event that the agent should know about.
	 */
	public void giveEvent(Event event){

	}

	/**
	 * Get the action that the agent currently wants to perform. This will consist of only a single
	 * square with a certain speed, and possibly a turn.
	 * @return The action that the agents want to perform next
	 */
	public Action getAction(){
		Action action = new Action();
		//Default action
		action.addActionElement(new Turn(Math.toRadians(45.0), 1));
		action.addActionElement(new Move(1.0,MoveDirection.E));
		action.addActionElement(new Wait(Math.random()));
		return action;
	}

	/**
	 * increments the time of the agent.
	 * @param time the time to be added to the current.
	 */
	public void addTimeToKey(double time){
		timeKey += time;
		if (timeKey < 0){
			System.err.println("The value of the timekey was to big and became negative");
			System.exit(-1);
		}
	}

	/**
	 * Returns the current timeKey of the agent.
	 * @return current timeKey of the agent
	 */
	public double getTimeKey(){
		return timeKey;
	}



	private double timeKey;
}
