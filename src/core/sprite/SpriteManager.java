/**
 * 
 */
package core.sprite;

import core.Map;
import core.actions.Action;
import core.actions.ActionElement;
import core.actions.Move;
import core.actions.Turn;
import core.events.EventManager;
import dataContainer.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


/**
 * The SpriteManager knows about the Map of the world, and about all sprites living
 * on the map. It asks the Agents for their actions, and performs them if possible.
 *
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
		agents = new PriorityQueue<>(new AgentComparator());
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
	 * This method takes the agent from the priority queue! Care should be taken that
	 * the agent is put back into the queue at some point with incremented duration.
	 * @return the agent who spend the least duration moving
	 */
	public Agent getFirstAgent(){
		if (agents == null || agents.isEmpty())
			return null;
		return agents.poll();
	}
	
	/**
	 * @return a list of all the agents
	 */
	public List<Agent> getAgentList(){
		return new ArrayList<>(agents);
	}
	
	/**
	 * Get the action list of the agent who spend the least duration and execute the list
	 */
	public void firstAgentAction(){
		Agent agent = getFirstAgent();
		Action agentAction = agent.getAction();
		double timeSpend = 0;
		
		for (ActionElement action: agentAction.getActionElements()){

			/*
			Find a way of using polymorphism to do this instead of a switch
			 */



			/* this handles the turn action */

			if ( action instanceof Turn){
				agent.getCoordinates().addToAngle(((Turn) action).getAngle());
				//TODO check if turn is valid
			}
			
			/* this handles the move action (THIS NEEDS TO BE IMPLEMENTED) */
			if ( action instanceof Move){
				Move move = (Move) action;
						if(isMovePossible(agent, move)){
							agent.move(move);
						}
			}

			/* handle the wait ActionElement */
				
		}

		eventManager.triggerEvent(agentAction,agent);
		
		/* update the duration witch was spend to execute the agentAction */
		agent.addTimeToKey(agentAction.duration());

		/* put the agent back into the queue with a new duration */
		agents.add(agent);
	}
	
	/**
	 * this function checks if a move is possible
	 * this check should be done both on the map and against the list of agents
	 * @param agent	The agent who want to do the move
	 * @param move the move the agent wants to perform
	 * @return true if the move is possible else false
	 */
	private boolean isMovePossible(Agent agent, Move move) {
		Coordinate coordinate = agent.getCoordinates();
		coordinate.x += move.direction().getDx();
		coordinate.y += move.direction().getDy();
		if (map.getCopyOfMap()[coordinate.x][coordinate.y].moveable()){
			for (Agent tempAgent: agents){
				Coordinate coords = tempAgent.getCoordinates();
				if ((coords.x == coordinate.x || coords.y == coordinate.y))
					return false; // if there is an agent in the way
			}
			return true; // if there is nothing in the way
		}
		return false; // if the map spot is already occupied by an object
	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
		
	}

	/**
	 * A queue with agents. The priority queue ensured that the queue of agents is always sorted
	 * with the smallest key in front. At every iteration we should TAKE AN AGENT FROM THE QUEUE,
	 * perform the operations and then PUT THE AGENT BACK in order to remain the sorted properties.
	 *
	 * Both insertion and retrieval is in done in O(log n) because the priorityqueue is implemented as
	 * a heap. This could be changed to a LinkedList if we want retrieval to be O(1) and insertion to
	 * be O(n).
	 */
	private PriorityQueue<Agent> agents;
	private Map map;
	private EventManager eventManager;


}
