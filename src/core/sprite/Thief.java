package core.sprite;

import java.util.ArrayList;
import java.util.List;

import core.actions.Action;
import dataContainer.Coordinate;

/**
 * Created by Stan on 08/04/15.
 */
public class Thief extends Agent {

	/**
	 * @param coords
	 */
	public Thief(Coordinate coords) {
		super(coords);
		// TODO Auto-generated constructor stub
	}
	
	public Action getAction(){
		
		Action action = aStar(target);
		
		// Check if there is a Agent in View (This should be done using belief map instead)!!
		//If yes use reverse RTA* to escape.
		List<Coordinate> agentsInView = new ArrayList<Coordinate>(lastSeen.getSpriteInVisionMap().keySet());
		for (Sprite s : lastSeen.getSpriteInVisionMap().values())
			if (s instanceof Guard)
				action = reverseAStar(agentsInView);
		return action;
	}
	
	public boolean canSprint(){
		//if within 10 ticks of previous sprint false
		//else return true
		return false;
	}
	public boolean shouldSprint(){
		//if a guard is near by, return true
		//else return false
		return false;
	}
}
