/**
 * 
 */
package dataContainer;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * The coordinates are stored in this container
 * @author ing. Robert Stevens
 * @version 1.0
 * @begin 1-4-2015
 *
 */
public class Coordinate {
	public int x;
	public int y;
	public double angle;
	
	public Coordinate(){}
	public Coordinate(int x, int y, double angle){
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	public void addToAngle(double angle){
		this.angle += angle;
		while (this.angle < 0)
			this.angle += Math.PI*2;
		this.angle %= Math.PI*2;
	}
	
	public Coordinate clone(){
		return new Coordinate(x, y, angle);
	}
	
	static public double distanceBetweenCoordinates (Coordinate c1, Coordinate c2){
		double dx = c1.x - c2.x;
		double dy = c1.y - c2.y;
		
		return sqrt(dx * dx + dy * dy);
	}

	public double distance(Coordinate element) {
		return sqrt(pow(x-element.x, 2) + pow( y - element.y, 2));
	}

	public double getAngle(Coordinate two){
		return Math.atan2((two.y - this.y),(two.x - this.x));
	}
	public String toString(){
		return "X = " +this.x +", y = "+this.y+", angle = "+this.angle+" rad/"+Math.toDegrees(angle)+" deg.";
	}

	public boolean equals(Object o){
		if(!(o instanceof Coordinate)) return false;
		Coordinate coordinate = (Coordinate) o;
		return this.x == coordinate.x && this.y == coordinate.y;
}

}
