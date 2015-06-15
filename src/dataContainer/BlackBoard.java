/**
 * 
 */
package dataContainer;

import java.util.ArrayList;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class BlackBoard {
	ArrayList<Coordinate> guardCoords;
	Coordinate thiefCoord;
	/**
	 * 
	 */
	public BlackBoard() {
		guardCoords = new ArrayList<Coordinate>();
		thiefCoord = null;
	}
	public void addGuard(Coordinate coord){
		if(!guardCoords.contains(coord)){
			guardCoords.add(coord);		
		}
	}
	public void updateThief(Coordinate coord){
		thiefCoord = coord.clone();
	}
	public Coordinate getThiefCoord(){
		return thiefCoord.clone();
	}
	
	public ArrayList<Coordinate> getGuardList(){
		return guardCoords;
	}

}
