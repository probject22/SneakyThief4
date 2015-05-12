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
	private boolean debug = DebugConstants.stacoDebug;
	private Coordinate baseCoords;
	/**
	 * 
	 */
	public Staco(Agent agent) {
		baseCoords = agent.getCoordinates();
	}
	
	public Action getMoveAction(Vision vision){
		HashMap<Coordinate, Sprite> sprites = vision.getSpriteInVisionMap();
		if (sprites.size() != 0)
			//TODO recalculate baseCoords
			baseCoords = baseCoords;
		Action action = new Action();
		action.addActionElement(new Turn(Math.toRadians(5), Agent.MAX_ANG_VEL));
		action.addActionElement(new Move(Agent.MAX_SPEED));
		
		return action;
	}

}
