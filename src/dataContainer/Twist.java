/**
 * 
 */
package dataContainer;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Twist {
	public Twist(){
		forwardSpeed = 0;
		angle = 0;
	}
	public Twist(double forwardSpeed, double angle){
		this.forwardSpeed = forwardSpeed;
		this.angle = angle;
	}
	public double forwardSpeed;
	public double angle;
}
