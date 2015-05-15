/**
 * 
 */
package core.Algorithms.Coverage.Staco;

import java.util.HashMap;

import core.DebugConstants;
import core.actions.Action;
import core.actions.Move;
import core.actions.Turn;
import core.events.Vision;
import core.sprite.Agent;
import core.sprite.Sprite;
import dataContainer.Coordinate;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Staco {
	private float distenceBetweenSprites = 10;
	private float minDistencBetweenSprites = 7;
	private boolean debug = DebugConstants.stacoDebug;
	private boolean dir = true;
	private Agent agent;
	/**
	 * 
	 */
	public Staco(Agent agent) {
		this.agent = agent;
	}
	
	public Action getMoveAction(Vision vision){
		HashMap<Coordinate, Sprite> sprites = vision.getSpriteInVisionMap();
		if (sprites.size() != 0)
			for(Coordinate coord: sprites.keySet()){
				distenceBetweenSprites = (float) Coordinate.distanceBetweenCoordinates(coord, agent.getCoordinates());
				if(debug) System.err.println("The distence between the agents is " +distenceBetweenSprites);
			}
		Action action = new Action();
		if (distenceBetweenSprites < minDistencBetweenSprites){
			dir = !dir;
			if (debug) System.err.println("The agent with coords " + agent.getCoordinates() + " changed direction");
			distenceBetweenSprites = minDistencBetweenSprites+1;
		}
		if (dir){
			action.addActionElement(new Turn(Math.toRadians(22.5), Agent.MAX_ANG_VEL));
			action.addActionElement(new Move(Agent.MAX_SPEED));
		}
		else{
			action.addActionElement(new Turn(Math.toRadians(-22.5), Agent.MAX_ANG_VEL));
			action.addActionElement(new Move(Agent.MAX_SPEED));
		}
		
		return action;
	}

}
