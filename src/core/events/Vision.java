package core.events;

import java.util.HashMap;

import core.sprite.Sprite;
import dataContainer.Coordinate;
import dataContainer.GridState;


/**
 * This class impelement the vision event message
 * Created by Stan on 08/04/15.
 */
public class Vision extends Event {

	/**
	 * @param timeStamp
	 */
	public Vision(double timeStamp) {
		super(timeStamp);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the coords
	 */
	public Coordinate getBaseCoords() {
		return baseCoords;
	}
	/**
	 * @param coords the coords to set
	 */
	public void setBaseCoords(Coordinate coords) {
		this.baseCoords = coords.clone();
	}
	public void addSprite(Coordinate coords, Sprite sprite){
		spriteInVisionMap.put(coords.clone(), sprite);
	}
	
	public void addGrid(Coordinate coords, GridState state){
		stateInVisionMap.put(coords.clone(),state);
	}

	private Coordinate baseCoords = null;
	HashMap <Coordinate, GridState> stateInVisionMap = new HashMap<Coordinate, GridState>();
	HashMap <Coordinate, Sprite> spriteInVisionMap = new HashMap<Coordinate, Sprite>();
	
}
