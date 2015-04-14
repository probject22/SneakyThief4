package core.events;

import dataContainer.Coordinate;


/**
 * This class implement the sound event message
 * Created by Stan on 08/04/15.
 */
public class Sound extends Event {

	/**
	 * @param timeStamp
	 */
	public Sound(double timeStamp, double direction, Coordinate baseCoords) {
		super(timeStamp);
		this.direction = direction;
		this.baseCoords = baseCoords;
	}
	public double getDirection(){
		return this.direction;
	}
	
	public Coordinate getBaseCoordinate(){
		return this.baseCoords;
	}
	private double direction;
	private Coordinate baseCoords;
}
