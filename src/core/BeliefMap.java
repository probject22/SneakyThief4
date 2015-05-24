/**
 * 
 */
package core;

import java.util.HashMap;
import java.util.Set;

import core.DebugConstants;
import core.Map;
import core.events.Vision;
import dataContainer.Coordinate;
import dataContainer.GridState;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class BeliefMap extends Map {
	private boolean debug = DebugConstants.beliefMapDebug;
	
	public BeliefMap(){
		Map map = Map.mapClass;
		this.map = new GridState[map.getMapWidth()][map.getMapHeight()];
		for (int i = 0; i<this.map.length; i++){
			for (int j = 0; j<this.map[0].length; j++){
				this.map[i][j] = GridState.unknown;
			}
		}
	}
	
	public void processVision(Vision vision){
		HashMap<Coordinate, GridState> stateList = vision.getStateInVisionMap();
		for (int i = 0; i < stateList.size(); i++){
			Set<Coordinate> coordList = stateList.keySet();
			for (Coordinate coord: coordList){
				this.map[coord.x][coord.y] = stateList.get(coord);
				if (debug) System.out.println("Gridstate " + stateList.get(coord) + " " + coord);
			}
		}
	}
	
}
