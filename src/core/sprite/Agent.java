/**
 * 
 */
package core.sprite;

import dataContainer.*;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Agent extends Sprite {
	
	public Agent(Coordinates coords) {
		super(coords);
	}

	public Twist GetMove(){
		return new Twist(1.5,((1.0/4.0)*Math.PI)*0.1);
	}
	
	public void setLaserScan(LaserScan laserScan){
		
	}
	
	public void setBlackboard(Blackboard blackboard){
		
	}
	
}
