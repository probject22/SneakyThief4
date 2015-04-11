package core.sprite;

import core.actions.Move;
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

	private Coordinate coords;

	/**
	 * Moves a sprite
	 * @param move
	 */
	public void move(Move move) {
		//TODO: implement move
	}
}
