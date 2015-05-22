/**
 * 
 */
package core.sprite;

import core.actions.Action;
import dataContainer.Coordinate;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Guard extends Agent {

	/**
	 * @param coords
	 */
	public Guard(Coordinate coords) {
		super(coords);
	}
	
	public Action getAction(){
		Action action = staco.getMoveAction(lastSeen);
		
		// Check if there is a intruder in View (This should be done using belief map instead)!!
		//If yes Use BES to get Action
		for (Sprite s : lastSeen.getSpriteInVisionMap().values())
			if (s instanceof Thief)
				action = BlockingES(s.getCoordinates());
		
		return action;
	}

}
