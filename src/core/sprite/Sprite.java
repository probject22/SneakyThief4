package core.sprite;

import dataContainer.Coordinate;
/**
 * All movable objects are sprites
 * 
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Sprite {
	public Sprite(Coordinate coords){
		this.coords = coords;
	}
	

	public Coordinate getCoordinates(){
		return coords == null? null: coords.clone();
	}
	
	public void setCoordinates(Coordinate coords){
		this.coords = coords;
	}
	
	private Coordinate coords;
	
}
