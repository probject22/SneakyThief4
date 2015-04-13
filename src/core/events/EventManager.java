/**
 * 
 */
package core.events;

import core.Map;
import core.actions.ActionElement;
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
		//TODO all the event handling

		//Generate events based on actionElement
		//sound
		//vision

		//Give the events to the appropriate agents
	}
}
