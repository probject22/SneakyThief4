package core.sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.Algorithms.ThiefPath;
import core.Algorithms.reverseRTAStar.MapReverseRTAStar;
import core.actions.Action;
import core.actions.Move;
import core.actions.Turn;
import core.actions.Wait;
import core.events.Event;
import core.events.Sound;
import core.events.Vision;
import dataContainer.Coordinate;
import dataContainer.GridState;

/**
 * Created by Stan on 08/04/15.
 */
public class Thief extends Agent {

	protected ThiefPath<Coordinate> thiefPath;
	protected ArrayList<Double> soundsDirection = new ArrayList<Double>();
	protected boolean panic;
	protected ThiefStates currentState = ThiefStates.EXPLORATION;
	/**
	 * @param coords
	 */
	public Thief(Coordinate coords) {
		super(coords);
		// Create an Escape map for Thief
	    thiefPath = new MapReverseRTAStar(beliefMap);
	}
	
	protected void processSound(Event sound){ 
		processSound((Sound)sound);
	}
	protected void processSound(Sound sound){ 
		soundsDirection.add(sound.getDirection());
	}
	public Action getAction(){
		Action out = null;
		
		switch(currentState){
		case EXPLORATION:
			if (toPanicState()){
				currentState = ThiefStates.PANIC;
			}
			else if (!toTargetState()){
				out = explorationState();
				if (out != null)
					return out;
			}
			currentState = ThiefStates.TARGET;
			/////////////////////////////////////////////////////////////////////////////
		case TARGET:
			if (toPanicState()){
				currentState = ThiefStates.PANIC;
			}
			else{
				out = targetState();
				if (out != null)
					return out;
			}
			////////////////////////////////////////////////////////////////
		case PANIC:
			out = panicState();
			if (out != null)
				return out;
			currentState = ThiefStates.EXPLORATION;
		}
		
		
		//To avoid null errors
		if (out == null){
			out = new Action();
			out.addActionElement(new Wait(0.01));
		}
		return out;

	}
	protected boolean toPanicState(){
		for (Sprite s : lastSeen.getSpriteInVisionMap().values()) {
			if (s instanceof Guard){
				return true;
			}
		}
		return false;
	}
	protected Action panicState(){
		List<Coordinate> followers = new ArrayList<Coordinate>(lastSeen.getSpriteInVisionMap().keySet());
		for (Sprite s : lastSeen.getSpriteInVisionMap().values()) {
			if (s instanceof Guard){
				Action action = reverseAStar(followers);
				if (action != null)
					return action;
			}
		}
		if(soundsDirection.isEmpty())
			return null;
		else{
			followers = new ArrayList<Coordinate>(lastSeen.getSpriteInVisionMap().keySet());
			for(double soundDirection : soundsDirection){
				followers.add(new Coordinate((int)(Math.sin(soundDirection)*2+getCoordinates().x),
										(int)(Math.cos(soundDirection)*2+getCoordinates().y),0));
			}
			Action action = reverseAStar(followers);
			soundsDirection.clear();		
			return action;	
			}
	}
	
	protected boolean toTargetState(){
		if (target != null)
			return true;
		return false;
	}
	
	protected Action targetState(){
		return aStar(target);
	}
	
	protected boolean toExplorationState(){
		if (target != null)
			return false;
		return true;
	}
	protected Action explorationState(){
		target = new Coordinate(20,25,0);
		return null;
	}
	
	protected Action reverseAStar(List<Coordinate> followers){
		Action action = new Action();
		Coordinate next = thiefPath.getBestEscape(getCoordinates().clone(),followers, target);
		double angle = getCoordinates().clone().angle;
		double goalAngle = getCoordinates().clone().getAngle(next);
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
		if (target == null)
			return false;
		if (target.x == getCoordinates().x && target.y == getCoordinates().y)
			return true;
		return false;
	}
	
	protected enum ThiefStates{
		PANIC,
		EXPLORATION,
		TARGET
	}
}
