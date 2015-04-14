/**
 * 
 */
package core.events;

import core.Map;
import core.actions.ActionElement;
import core.actions.Move;
import core.actions.Turn;
import core.actions.Wait;
import core.sprite.Agent;
import core.sprite.SpriteManager;

/**
 *
 * The EventManager at every action that the SpriteManager makes, whether a new event should
 * be generated, and if so, it passes this on to the appropriate agent.
 *
 * @author ing. Robert Stevens
 * @begin 9 apr. 2015
 * @version 1.0
 * @changes
 * @todo 
 */
public class EventManager {

	private final SpriteManager spriteManager;
	private Map map;

	public EventManager(Map map){
		this.spriteManager = SpriteManager.instance();
		this.map = map;
	}

	/**
	 * checks if the actionElement performed by the given agent triggers any events. If so the events
	 * are routed to the appropriate agents.
	 * Always trigger events AFTER EXECUTION OF THE ACTIONELEMENT!
	 * @param actionElement
	 * @param agent
	 */
	public void triggerEvent(ActionElement actionElement, Agent agent){
		//TODO Get the timestamps of the actions
		double timeStamp = 1;
		if ( actionElement instanceof Turn){
			//triger a vision event
		}

		/* this handles the move actionElement (THIS NEEDS TO BE IMPLEMENTED) */
		else if ( actionElement instanceof Move){
			//TODO dont trigger this event when a guard is entering a tower leaving a tower or when the agent turns for more then 45 degrees (page 4) 
			generateVisionEvent(agent, timeStamp);
			generateMoveSoundEvent(agent, timeStamp, ((Move)actionElement).speed);
		}

		/* handle the wait ActionElement */
		else if ( actionElement instanceof Wait){
			//trigger a vision event
		}
		
	}
	
	private void generateVisionEvent(Agent agent, double timeStamp){
		Vision vision = new Vision(timeStamp);
		//TODO calculate what the agent can see and make a ?list? of it
		agent.giveEvent(vision);
	}
	
	private void generateMoveSoundEvent(Agent agent, double timeStamp, double speed){
		Sound sound = new Sound(timeStamp);
		double distence = 0;
		if (speed >0 && speed < 0.5)
			distence = 1;
		else if (speed < 1)
			distence = 3;
		else if (speed < 2)
			distence = 5;
		else
			distence = 10;
		
		//TODO put stuff in the sound event
		
		for (Agent tempAgent: spriteManager.getAgentList())
			if (distenceBetweenAgents(agent, tempAgent) <= distence)
				tempAgent.giveEvent(sound);
	}
	
	private double distenceBetweenAgents(Agent agent1, Agent agent2){
		return 0;
	}
}
