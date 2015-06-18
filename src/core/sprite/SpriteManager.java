/**
 * 
 */
package core.sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


/**
 * The SpriteManager knows about the Map of the world, and about all sprites living
 * on the map. It asks the Agents for their actions, and performs them if possible.
 *
 * This class is a singleton, meaning that there can be only one SpriteManager.
 *
 * @author ing. R.J.H.M. Stevens
 *
 */
public class SpriteManager {

	private static SpriteManager spriteManager;
	/**
	 * private constructor for singleton design pattern.
	 */
	 private SpriteManager() {
		agents = new PriorityQueue<>(new AgentComparator());
	 }


	public static SpriteManager instance(){
		if(spriteManager == null)
			spriteManager = new SpriteManager();

		return spriteManager;
	}
	public static SpriteManager newInstance(){
		spriteManager = new SpriteManager();

		return spriteManager;
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
	
	public ArrayList<Thief> getThieves(){
		ArrayList<Thief> allThieves = new ArrayList<Thief>();
		while(!agents.isEmpty()){
			Agent agent = (Agent) agents.remove();
			if(agent instanceof Thief){
				allThieves.add((Thief) agent);
			}
		}
		return allThieves;
	}
	public ArrayList<Guard> getGuards(){
		ArrayList<Guard> allGuards = new ArrayList<Guard>();
		while(!agents.isEmpty()){
			Agent agent = (Agent) agents.remove();
			if(agent instanceof Guard){
				allGuards.add((Guard) agent);
			}
		}
		return allGuards;
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


}
