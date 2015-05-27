package core.sprite;

import java.util.ArrayList;
import java.util.List;

import core.Algorithms.ThiefPath;
import core.Algorithms.reverseRTAStar.MapReverseRTAStar;
import core.actions.Action;
import core.actions.Move;
import core.actions.Turn;
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
		//if a guard is near by, return true
		//else return false
		return false;
	}
	
	public boolean atGoal(){
		/*Coordinate currentCoord = this.getCoordinates();
		Coordinate adjacentCoord = currentCoord.leftTop();
		GridState adjacentState = adjacentCoord.getGridState();
		(if grd.Target){return true;}
		Coordinate adjacentCoord = currentCoord.top();
		GridState adjacentState = adjacentCoord.getGridState();
		(if grd.Target){return true;}
		Coordinate adjacentCoord = currentCoord.rightTop();
		GridState adjacentState = adjacentCoord.getGridState();
		(if grd.Target){return true;}
		Coordinate adjacentCoord = currentCoord.left();
		GridState adjacentState = adjacentCoord.getGridState();
		(if grd.Target){return true;}
		Coordinate adjacentCoord = currentCoord.right();
		GridState adjacentState = adjacentCoord.getGridState();
		(if grd.Target){return true;}
		Coordinate adjacentCoord = currentCoord.leftBottom();
		GridState adjacentState = adjacentCoord.getGridState();
		(if grd.Target){return true;}
		Coordinate adjacentCoord = currentCoord.bottom();
		GridState adjacentState = adjacentCoord.getGridState();
		(if grd.Target){return true;}
		Coordinate adjacentCoord = currentCoord.rightBottom();
		GridState adjacentState = adjacentCoord.getGridState();
		(if grd.Target){return true;}*/
		
		
		
		return false;
	}
	
	public boolean isCaught(){
		for (Sprite s : lastSeen.getSpriteInVisionMap().values())
			if (s instanceof Thief)
				return true;
		
		return false;				
		
	}
}
