package core.sprite;

import core.actions.Action;
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
		return coords;
	}
	
	public void setCoordinates(Coordinate coords){
		this.coords = coords;
	}
	
	public Action getActions(){
		System.err.println("This function has to be overwritten");
		return null;
	}
	
	private Coordinate coords;
	
}
