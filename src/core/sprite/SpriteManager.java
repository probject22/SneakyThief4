/**
 * 
 */
package core.sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import core.Map;
import core.actions.Action;


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
		
		//TODO go trough the list and execute the actions
		
		/* update the time witch was spend to execute the actions */
		agent.addTimeToKey(timeSpend);
	}
	private List<Agent> agents;
	private Map map;

}
