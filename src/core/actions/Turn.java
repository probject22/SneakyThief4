package core.actions;

/**
 *
 * A Turn is an actionelement that defines the turn of an agent around his central
 * axis. This turn has a velocity (which is always positive) and an angle (which can
 * be positive or negative depending on the direction.
 *
 * Created by Stan on 08/04/15.
 */
public class Turn implements ActionElement {

	public Turn(double from, double to, double angularVelocity){
		this(
				    to - from,
				    angularVelocity);
	}

	public Turn(double radians, double angularVelocity){
		if (radians < 0) radians += (2*Math.PI);
		rad = radians % (2*Math.PI);
		this.angularVelocity = angularVelocity;
	}

	/**
	 * Returns the angle of the move in radians.
	 * @return the angle of the move in radians.
	 */
	public double getAngle(){
		return rad;
	}
	private double angularVelocity;
	private double rad;


	@Override
	public double duration() {
		if (rad < Math.PI)
			return Math.abs(rad / angularVelocity);
		return Math.abs((rad-Math.PI*2) / angularVelocity);
	}
}
