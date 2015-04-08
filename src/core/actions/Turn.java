package core.actions;

/**
 * Created by Stan on 08/04/15.
 */
public class Turn extends ActionElement {
	public Turn(double radians){
		rad = radians % (2*Math.PI);
	}
	
	public double rad;
}
