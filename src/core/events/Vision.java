package core.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
	
	public void deleteSprite(Coordinate coords){
		spriteInVisionMap.remove(coords.clone());
	}
	
	public void addGrid(Coordinate coords, GridState state){
		stateInVisionMap.put(coords.clone(),state);
	}
	public void deleteGrid(ArrayList<Coordinate> remove) {
		while (remove.size() != 0){
			deleteGrid(remove.remove(0));
		}
		
	}
	public void deleteGrid(Coordinate coords){
		Set<Coordinate> coordinates = stateInVisionMap.keySet();
		for (int i = 0; i < coordinates.size(); i++ ){
			Coordinate coord = (Coordinate) coordinates.toArray()[i];
			if (coords.equals(coord)){
				stateInVisionMap.remove(coord);
			}
		}
		//System.out.print(stateInVisionMap.remove(coords.clone()));
	}

	private Coordinate baseCoords = null;
	HashMap <Coordinate, GridState> stateInVisionMap = new HashMap<Coordinate, GridState>();
	HashMap <Coordinate, Sprite> spriteInVisionMap = new HashMap<Coordinate, Sprite>();
	/**
	 * @return the stateInVisionMap
	 */
	public HashMap<Coordinate, GridState> getStateInVisionMap() {
		return stateInVisionMap;
	}

	/**
	 * @return the spriteInVisionMap
	 */
	public HashMap<Coordinate, Sprite> getSpriteInVisionMap() {
		return spriteInVisionMap;
	}


	
}
