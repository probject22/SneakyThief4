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
import core.events.Event;
import core.events.Sound;
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
	/**
	 * @param coords
	 */
	public Guard(Coordinate coords) {
		super(coords);
		stico = new Stico(this);
	}
	
	protected void processSound(Event sound){ 
		processSound((Sound)sound);
	}
	protected void processSound(Sound sound){ 
		soundsDirection.add(sound.getDirection());
	}
	
	public Action getAction(){
		
		if(catchMode){
			
			if (lastSeen.getSpriteInVisionMap().containsValue(lastSeenThief)){
				return BlockingES(lastSeenThiefDirection);
			}
			if (soundsDirection.isEmpty()){
				catchMode = false;
			}
			else{
				System.out.println("catchingHear");
				double closestThief = soundsDirection.get(0);
				double closestThiefDirection = Math.abs(getCoordinates().getAngle(lastSeenThiefDirection)- soundsDirection.remove(0));
				
				for (double direction : soundsDirection){
					if (Math.abs(this.getCoordinates().clone().getAngle(lastSeenThiefDirection)-direction) < closestThiefDirection){
						closestThiefDirection = Math.abs(this.getCoordinates().getAngle(lastSeenThiefDirection)-direction);
						closestThief = direction;
					}
				}
				lastSeenThiefDirection = new Coordinate((int)(Math.sin(closestThief)*2)+getCoordinates().x,(int)(Math.cos(closestThief)*2)+getCoordinates().y,0);
				soundsDirection.clear();
				
				return BlockingES(lastSeenThiefDirection);
				}	
		}
		//if everything is normal basic exploration is done
				Action action = basicExploration(); 
	
		// Check if there is a intruder in View !!
		//If yes Use BES to get Action	
		soundsDirection.clear();	
		for (Sprite s : lastSeen.getSpriteInVisionMap().values()){
			if (s instanceof Thief){
				catchMode = true;
				lastSeenThiefDirection = s.getCoordinates().clone();
				//System.out.println(lastSeenThiefDirection);
				lastSeenThief = (Thief) s;
				return BlockingES(lastSeenThiefDirection);
			}
		}
		//if problems: go to stico
		if (action == null || action.getActionElements() == null || action.getActionElements().isEmpty())
			action = stico.getMoveAction(lastSeen);
		return action;
	}
	
	/**
	 * Returns an Blocking Action 
	 * according to the Blocking Coordinate
	 * found using BES
	 * @author Sina (21/05/2015)
	 */
	protected Action BlockingES(Coordinate intruder){
		Collection<Coordinate> otherAgents = lastSeen.getSpriteInVisionMap().keySet();
		otherAgents.remove(lastSeenThiefDirection);
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
	
	public boolean hasCought(){
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
}
