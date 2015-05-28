/**
 * 
 */
package dataContainer;

import static java.lang.Math.*;

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
	
	static public double distanceBetweenCoordinates(Coordinate c1, Coordinate c2){
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
	public Coordinate addPolar(double angle, double r){
		return new Coordinate((int)Math.round(x + r*cos(angle)),(int)Math.round( y + r*sin(angle)),this.angle);
	}

	public Coordinate left(){
		return new Coordinate(x-1,y,angle);
	}

	public Coordinate leftTop(){
		return new Coordinate(x-1,y+1,angle);
	}
	public Coordinate rightTop(){
		return new Coordinate(x+1,y+1,angle);
	}
	public Coordinate leftBottom(){
		return new Coordinate(x-1,y-1,angle);
	}
	public Coordinate rightBottom(){
		return new Coordinate(x+1,y-1,angle);
	}

	public Coordinate right(){
		return new Coordinate(x+1,y,angle);
	}

	public Coordinate top(){
		return new Coordinate(x,y+1,angle);
	}

	public Coordinate bottom(){
		return new Coordinate(x,y-1,angle);
	}

	public boolean onLine(Coordinate from, Coordinate to){

		double slope,intercept;
		double x1 = from.x,x2 = to.x,y1 = from.y ,y2 = to.y;

		double px = x,py = y;
		double left,top,right,bottom;
		double dx, dy;

		dx = x2 - x1;
		dy = y2 - y1;

		slope = dy / dx;

		intercept = y1 - slope * x1;

		if(x1 < x2) {
			left = x1; right = x2;
		} else {
			left = x2; right = x1;
		}
		if(y1 < y2) {
			top = y1; bottom = y2;
		} else {
			top = y1; bottom = y2;
		}

		if ( ((int)slope * px + intercept) + 1 >= py && ((int)slope * px + intercept) <= py ){
			if (px >= left && px <= right && py >= top && py <= bottom) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean equals(Object o){
		if(!(o instanceof Coordinate)) return false;
		Coordinate coordinate = (Coordinate) o;
		return this.x == coordinate.x && this.y == coordinate.y;
	}

	public boolean isNeighbour(Coordinate coordinate) {
		return 	left().equals(coordinate) ||
				right().equals(coordinate) ||
				top().equals(coordinate) ||
				bottom().equals(coordinate) ||
				leftTop().equals(coordinate) ||
				rightTop().equals(coordinate) ||
				leftBottom().equals(coordinate) ||
				rightBottom().equals(coordinate);
	}
}
