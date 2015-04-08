package core.actions;

/**
 * Created by Stan on 08/04/15.
 */
public class Turn extends ActionElement {
	public Turn(double degrees){
		rad = Math.toRadians(degrees);
	}
	
	public double rad;
}
