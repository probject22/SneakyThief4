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
public class Coordinate {
	public double x;
	public double y;
	public double angle;
	
	public Coordinate(){}
	public Coordinate(double x, double y, double angle){
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	
	public Coordinate clone(){
		return new Coordinate(x, y, angle);
	}
}
