/**
 *
 */
package core.sprite;

import core.actions.*;
import dataContainer.Coordinate;

/**
 * @author ing. R.J.H.M. Stevens
 */
public class Agent extends Sprite {

    public Agent(Coordinate coords) {
        super(coords);
    }

	public Action getActions(){
		Action action = new Action();
		action.addAction(new Turn(Math.toRadians(45.0)));
		action.addAction(new Move(1.0));
		return action;
	}
	
}
