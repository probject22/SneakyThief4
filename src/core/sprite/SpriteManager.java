/**
 * 
 */
package core.sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import core.Map;
import core.actions.Action;
import core.actions.ActionElement;
import core.actions.Move;
import core.actions.Turn;
import core.events.EventManager;
import core.events.EventType;
import dataContainer.Coordinate;
import dataContainer.MoveDirections;


/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class SpriteManager {

	/**
	 * @param map the worldmap
	 * 
	 */
	public SpriteManager(Map map) {
		this.map = map;
		agents = new ArrayList<Agent>();
	}

	/**
	 * add an new agent to the list
	 * @param agent an agent
	 */
	public void addAgent(Agent agent){
		if (agent == null){
			System.err.println("The agent can't be null");
			return;
		}
		agents.add(agent);
	}
	
	/**
	 * 
	 * @return the agent who spend the less time moving
	 */
	public Agent getFirstAgent(){
		if (agents == null || agents.isEmpty())
			return null;
		Comparator<Object> comparator = new AgentComparator();
		Collections.sort(agents, comparator);
		return agents.get(0);
	}
	
	/**
	 * @return a list of all the agents
	 */
	public List<Agent> getAgentList(){
		return agents;
	}
	
	/**
	 * Get the action list of the agent who spend the least time and execute the list
	 */
	public void firstAgentAction(){
		Agent agent = getFirstAgent();
		Action actions = agent.getActions();
		double timeSpend = 0;
		
		for (ActionElement action: actions.getActions()){
			
			/* this handles the turn action */
			if ( action instanceof Turn){
				agent.getCoordinates().addToAngle(((Turn) action).getAngleRad());
				eventManager.triggerEvent(EventType.Turn, agent);
				
				//TODO figure out howmutch time this move cost you
				timeSpend += 1.0;
				break;
			}
			
			/* this handles the move action (THIS NEEDS TO BE INPLEMENTED) */
			if ( action instanceof Move){
				Coordinate coords = agent.getCoordinates();
				Coordinate newCoords = coords.clone();
				for (MoveDirections dir: MoveDirections.values()){
					if (coords.angle >= dir.getStartAngleRad() && coords.angle < dir.getEndAngleRad()){
						newCoords.x += dir.getDx();
						newCoords.y += dir.getDy();
						if(isMovePossible(agent, newCoords)){
							agent.setCoordinates(newCoords);
							//TODO figure out howmutch time this move cost you
							timeSpend += 1.0;
							eventManager.triggerEvent(EventType.Move, agent);
						}
						break; //break for moveDir..
					}
				}
				break;  //break if action
			}
				
		}
		
		/* update the time witch was spend to execute the actions */
		agent.addTimeToKey(timeSpend);
	}
	
	/**
	 * this function checks if a move is possible
	 * this check should be done both on the map and against the list of agents
	 * @param agent	The agent who want to do the move
	 * @param newCoords the new coordinates
	 * @return true if the move is possible else false
	 */
	private boolean isMovePossible(Agent agent, Coordinate newCoords) {
		if (map.getCopyOfMap()[newCoords.x][newCoords.y].moveable()){
			for (Agent tempAgent: agents){
				Coordinate coords = tempAgent.getCoordinates();
				if (!(coords.x == newCoords.x && coords.y == newCoords.y))
					return true;
			}
		}
		return false;
	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
		
	}
	private List<Agent> agents;
	private Map map;
	private EventManager eventManager;


}
