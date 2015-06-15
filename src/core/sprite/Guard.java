/**
 * 
 */
package core.sprite;

import java.util.ArrayList;
import java.util.Collection;

import core.Algorithms.BES.BES;
import core.Algorithms.Coverage.Stico.Stico;
import core.actions.Action;
import core.actions.Move;
import core.actions.Turn;
import core.actions.Wait;
import core.events.Event;
import core.events.Sound;
import core.events.Vision;
import dataContainer.BlackBoard;
import dataContainer.Coordinate;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Guard extends Agent {
	
	
	protected Stico stico;
	protected boolean catchMode;
	protected Coordinate lastSeenThiefDirection;
	protected Thief lastSeenThief;
	protected ArrayList<Double> soundsDirection = new ArrayList<Double>();
	
	protected GuardStates currentState = GuardStates.EXPLORATION;
	/**
	 * @param coords
	 */
	public Guard(Coordinate coords) {
		super(coords);
		stico = new Stico(this);
		
		minVisionRange = 0;
		maxVisionRange = 10;
		visionAngle = 45;
		structureVisionRange = 10;
		towerVisionRange = 15;
	}
	public void setBlackBoard(BlackBoard board){
		this.blackboard = board;
		blackboard.addGuard(this.getCoordinates());
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
			if(toCatchState()){
				currentState = GuardStates.CATCH_MODE;
			}
			else if(!toCoverageState()){
				out = explorationState();
				if (out != null)
					return out;
				currentState = GuardStates.COVARAGE;
			}
			else{
				currentState = GuardStates.COVARAGE;
			}
			///////////////////////////////////////////
		case COVARAGE:
			if(toCatchState()){
				currentState = GuardStates.CATCH_MODE;
			}
			else{
				out = coverageState();
				if (out != null)
					return out;
			}
		case CATCH_MODE:
			out = catchState();
			if (out != null)
				return out;
			if (toExplorationState()){
				currentState = GuardStates.EXPLORATION;
			}
			else{
				currentState = GuardStates.COVARAGE;
			}
		}
		
		soundsDirection.clear();	
		//To avoid null errors
		if (out == null){
			out = new Action();
			out.addActionElement(new Wait(0.01));
		}
		return out;

	}
	
	protected boolean toExplorationState(){
		Action action = basicExploration(); 
		if (action != null)
			return false;
		return true;
	}
	protected Action explorationState(){
		return basicExploration(); 
	}
	
	protected boolean toCoverageState(){
		return !toExplorationState();
	}
	protected Action coverageState(){
		return stico.getMoveAction(lastSeen);
	}
	
	protected boolean toCatchState(){
		for (Sprite s : lastSeen.getSpriteInVisionMap().values()){
			if (s instanceof Thief){
				lastSeenThief = (Thief) s;
				lastSeenThiefDirection = s.getCoordinates().clone();
				return true;
			}
		}
		return false;
	}
	
	protected Action catchState(){
		//if the thief is still in view cone follow him.
		if (lastSeen.getSpriteInVisionMap().containsValue(lastSeenThief)){
			lastSeenThiefDirection = lastSeenThief.getCoordinates().clone();
			return BlockingES(lastSeenThiefDirection);	
		}
		//if hear no sound gets out of catch mode
		if (soundsDirection.isEmpty()){
			return null;
		}
		//else try to find where the sound comes from
			
		double closestThief = soundsDirection.remove(0);
		double closestThiefDirection = Math.abs(getCoordinates().getAngle(lastSeenThiefDirection) - closestThief);
		
		for (double direction : soundsDirection){
			if (Math.abs(getCoordinates().clone().getAngle(lastSeenThiefDirection) - direction) < closestThiefDirection){
				closestThiefDirection = Math.abs(this.getCoordinates().getAngle(lastSeenThiefDirection)-direction);
				closestThief = direction;
			}
		}
		
		lastSeenThiefDirection = new Coordinate((int)(Math.sin(closestThief)*2)+getCoordinates().x,(int)(Math.cos(closestThief)*2)+getCoordinates().y,0);
		
		
		return BlockingES(lastSeenThiefDirection);	
	}
	
	/**
	 * Returns an Blocking Action 
	 * according to the Blocking Coordinate
	 * found using BES
	 * @author Sina (21/05/2015)
	 */
	protected Action BlockingES(Coordinate intruder){
		Collection<Coordinate> otherAgents = lastSeen.getSpriteInVisionMap().keySet();
		otherAgents.remove(lastSeenThief.getCoordinates().clone());
		Coordinate next = BES.getBlockingLocation(getCoordinates().clone(),otherAgents,intruder);
		//Use A* or RTA* to get to the Blocking Coordinate.
		return rTAStar(next);
	}
	public void enterTower(){
		this.currentMaXVisionRange = this.towerMaxVisionRange;
		this.currentminVisionRange = this.towerMinVisionRange;
		inTower = true;
	}
	
	public void leaveTower(){
		this.currentMaXVisionRange = this.towerMaxVisionRange;
		this.currentminVisionRange = this.towerMinVisionRange;
		inTower = false;
	}
	
	protected double towerMinVisionRange = 2;
	protected double towerMaxVisionRange = 15;
	
	protected boolean inTower = false;
	
	public boolean isInTower(){
		return inTower;
	}
	
	public boolean hasCaught(){
		if (lastSeen == null ||lastSeen.getSpriteInVisionMap() == null || lastSeen.getSpriteInVisionMap().size() ==0)
			return false;
		for (Sprite s : lastSeen.getSpriteInVisionMap().values()){
			if (s instanceof Thief){
					if(s.getCoordinates().isNeighbour(this.getCoordinates())){
						System.out.println("We WON");
						return true;
					}
			}
	}
		return false;
	}
	
	protected enum GuardStates{
		COVARAGE,
		EXPLORATION,
		CATCH_MODE
	}
}
