/**
 * 
 */
package core.events;

import core.Map;
import core.actions.Action;
import core.sprite.Agent;

import java.util.List;

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

	private Map map;
	private List<Agent> agents;

	public EventManager(List<Agent> agents, Map map){
		this.agents = agents;
		this.map = map;
	}

	/**
	 * checks if the action performed by the given agent triggers any events. If so the events
	 * are routed to the appropriate agents.
	 *
	 * @param action
	 * @param agent
	 */
	public void triggerEvent(Action action, Agent agent){
		//TODO all the event handling

		//Generate events based on action
		//sound
		//vision

		//Give the events to the appropriate agents
	}
}
