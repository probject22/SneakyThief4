package core.actions;

import core.Map;
import dataContainer.MoveDirection;

/**
 *
 * A move class which is the unit of movement. Every move will move an agent only one
 * square in any of the eight possible directions, with a certain speed. The duration the
 * move will take depends on the amount of meters per square.
 *
 * Created by Stan on 08/04/15.
 */
public class Move implements ActionElement {
	public Move (double speed){
		this.speed = speed;
	}
	public double speed;

	@Override
	public double duration() {
		return speed * Map.meters_per_unit;
	}

}
