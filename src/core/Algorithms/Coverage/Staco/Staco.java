/**
 * 
 */
package core.Algorithms.Coverage.Staco;

import java.util.HashMap;

import core.DebugConstants;
import core.actions.Action;
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
		return null;
	}

}
