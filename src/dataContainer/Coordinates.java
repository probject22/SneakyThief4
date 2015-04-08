/**
 * 
 */
package dataContainer;

/**
 * @author ing. Robert Stevens
 * @version 1.0
 * @begin 1-4-2015
 *
 */
public class Coordinates {
	public double x;
	public double y;
	public double angle;
	
	public Coordinates(){}
	public Coordinates(double x, double y, double angle){
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	
	public Coordinates clone(){
		return new Coordinates(x, y, angle);
	}
}
