/**
 * 
 */
package dataContainer;

import core.sprite.Agent;

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
	
	static public double distenceBetweenCoordinates(Coordinate c1, Coordinate c2){
		double dx = c1.x - c2.x;
		double dy = c1.y - c2.y;
		
		return Math.sqrt(dx * dx + dy * dy);
	}
}
