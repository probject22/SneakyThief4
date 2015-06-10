/**
 * 
 */
package core.sprite;

import java.util.ArrayList;
import java.util.Collection;

import core.Algorithms.BES.BES;
import core.Algorithms.Coverage.Stico.Stico;
import core.actions.Action;
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
		
		
		
		
		Action action = basicExploration(); 
			if (action == null || action.getActionElements() == null || action.getActionElements().isEmpty())
				action = stico.getMoveAction(lastSeen);
		//Action action = aStar(new Coordinate(3,16,0));
		// Check if there is a intruder in View (This should be done using belief map instead)!!
		//If yes Use BES to get Action
		
			
			
		for (Sprite s : lastSeen.getSpriteInVisionMap().values()){
			if (s instanceof Thief){
				action = BlockingES(s.getCoordinates());
				catchMode = true;
				System.out.println("catching");
				return action;
			}
		}
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
		Coordinate next = BES.getBlockingLocation(this.getCoordinates(),otherAgents,intruder);
		
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
}
