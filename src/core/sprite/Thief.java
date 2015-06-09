package core.sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.Algorithms.ThiefPath;
import core.Algorithms.reverseRTAStar.MapReverseRTAStar;
import core.actions.Action;
import core.actions.Move;
import core.actions.Turn;
import core.events.Vision;
import dataContainer.Coordinate;
import dataContainer.GridState;

/**
 * Created by Stan on 08/04/15.
 */
public class Thief extends Agent {

	private ThiefPath<Coordinate> thiefPath;
	
	/**
	 * @param coords
	 */
	public Thief(Coordinate coords) {
		super(coords);
		// Create an Escape map for Thief
	    thiefPath = new MapReverseRTAStar(beliefMap);
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
	
	protected Action reverseAStar(List<Coordinate> followers){
		Action action = new Action();
		Coordinate next = thiefPath.getBestEscape(getCoordinates(),followers, target);
		double angle = getCoordinates().angle;
		double goalAngle = getCoordinates().getAngle(next);
		action.addActionElement(new Turn(angle, goalAngle, Agent.MAX_SPEED));
		action.addActionElement(new Move(Agent.MAX_SPEED));
		
		return action;
	}
	
	public boolean canSprint(){
		//if within 10 ticks of previous sprint false
		//else return true
		return false;
	}
	public boolean shouldSprint(){
		//if a guard is near by and thief is in the opposite direction, return true
		//else return false
		return false;
	}
	
	public boolean atGoal(){
		Coordinate currentCoord = this.getCoordinates();
		GridState[][] thisMap = beliefMap.getCopyOfMap();
		if(thisMap[currentCoord.x][currentCoord.y]== GridState.Target)
			{return true;}
		return false;
	}
	
	public boolean isCaught(){
		SpriteManager spriteManager = SpriteManager.instance();
		ArrayList<Guard> guardList = spriteManager.getGuards();
		Coordinate myCoords = this.getCoordinates();
		for(int i = 0; i<guardList.size();i++){
			Agent thisAgent = guardList.get(i);
			Coordinate agentCoords = thisAgent.getCoordinates();
			double distance = myCoords.distanceBetweenCoordinates(myCoords, agentCoords);
			if(distance<2&&distance!=0){
				Vision vision = thisAgent.getLastSeen();
				HashMap<Coordinate, Sprite> agentsInVision = vision.getSpriteInVisionMap();
				for(int j = 0;j<agentsInVision.size();j++){
					if(this == agentsInVision.get(j)){
						return true;
					}
					else return false;
				}
				
			}

		}
		return false;
		
	}
}
