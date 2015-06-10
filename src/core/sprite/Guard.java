/**
 * 
 */
package core.sprite;

import java.util.Collection;

import core.Algorithms.BES.BES;
import core.Algorithms.Coverage.Stico.Stico;
import core.actions.Action;
import dataContainer.Coordinate;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Guard extends Agent {
	
	protected Stico stico;
	/**
	 * @param coords
	 */
	public Guard(Coordinate coords) {
		super(coords);
		stico = new Stico(this);
	}
	
	public Action getAction(){
		//Action action = aStar(new Coordinate(20,20,0));
		Action action = basicExploration(); 
			if (action == null || action.getActionElements() == null || action.getActionElements().isEmpty())
				action = stico.getMoveAction(lastSeen);
		//Action action = aStar(new Coordinate(3,16,0));
		// Check if there is a intruder in View (This should be done using belief map instead)!!
		//If yes Use BES to get Action
		for (Sprite s : lastSeen.getSpriteInVisionMap().values())
			if (s instanceof Thief)
				action = BlockingES(s.getCoordinates());
		//action = aStar(new Coordinate(15,15,0));
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
		return aStar(next);
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
