package core.sprite;
import dataContainer.Coordinates;
import dataContainer.Twist;
/**
 * All movable objects are sprites
 * 
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Sprite {
	public Sprite(Coordinates coords){
		this.coords = coords;
	}
	
	public Twist GetMove(){
		return null;
	}
	
	public Coordinates getCoordinates(){
		return coords == null? null: coords.clone();
	}
	
	public void setCoordinates(Coordinates coords){
		this.coords = coords;
	}
	
	private Coordinates coords;
	
}
