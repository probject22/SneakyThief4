package core.events;

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
		this.baseCoords = coords;
	}
	/**
	 * @return the state
	 */
	public GridState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(GridState state) {
		this.state = state;
	}
	/**
	 * @return the sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}
	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Coordinate getField() {
		return field;
	}

	public void setField(Coordinate field) {
		this.field = field;
	}

	private Coordinate baseCoords = null;
	private Coordinate field = null;
	private GridState state = null;
	private Sprite sprite = null;
	
}
