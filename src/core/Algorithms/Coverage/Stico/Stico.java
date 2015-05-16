/**
 * 
 */
package core.Algorithms.Coverage.Stico;

import java.util.HashMap;

import core.DebugConstants;
import core.actions.Action;
import core.actions.Move;
import core.actions.Turn;
import core.events.Vision;
import core.sprite.Agent;
import core.sprite.Sprite;
import dataContainer.Coordinate;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Stico {
	int steps = 0;
	private float minDistencBetweenSprites = 10;
	private boolean debug = DebugConstants.sticoDebug;
	private boolean dir = true;
	private Agent agent;
	/**
	 * 
	 */
	public Stico(Agent agent) {
		this.agent = agent;
	}
	
	public Action getMoveAction(Vision vision){
		float distenceBetweenSprites = minDistencBetweenSprites+1;
		double angle = 0;
		HashMap<Coordinate, Sprite> sprites = vision.getSpriteInVisionMap();
		if (sprites.size() != 0)
			for(Coordinate coord: sprites.keySet()){
				angle = agent.getCoordinates().getAngle(coord);
				distenceBetweenSprites = (float) Coordinate.distanceBetweenCoordinates(coord, agent.getCoordinates());
			}
		Action action = new Action();
		if (distenceBetweenSprites < minDistencBetweenSprites){
			if(debug) System.err.println("dir " + dir + " angle "+ angle);
			if (dir && angle < Math.PI){
				if(debug) System.err.println("case 1");
				dir = !dir;
				action.addActionElement(new Turn(Math.toRadians(-90), Agent.MAX_ANG_VEL));
				steps = 2;
			}
			else if (!dir && angle < Math.PI){
				if(debug) System.err.println("case 2");
				dir = !dir;
				action.addActionElement(new Turn(Math.toRadians(90), Agent.MAX_ANG_VEL));
				steps =2;
			}
			else if (dir && angle > Math.PI){
				if(debug) System.err.println("case 3");
				System.err.println("case 3 of stico is not implemented yet");
			}
			else if (!dir && angle > Math.PI){
				if(debug) System.err.println("case 4");
				System.err.println("case 4 of stico is not implemented yet");
			}
			
			
			//steps = (int)(minDistencBetweenSprites - distenceBetweenSprites)+1;
		}
		if (steps != 0){
			steps--;
			action.addActionElement(new Move(Agent.MAX_SPEED));
		}
		else if (dir){
			action.addActionElement(new Turn(Math.toRadians(45), Agent.MAX_ANG_VEL));
			action.addActionElement(new Move(Agent.MAX_SPEED));
		}
		else{
			action.addActionElement(new Turn(Math.toRadians(-45), Agent.MAX_ANG_VEL));
			action.addActionElement(new Move(Agent.MAX_SPEED));
		}
		
		return action;
	}

}
