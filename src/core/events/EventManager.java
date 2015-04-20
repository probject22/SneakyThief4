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
import dataContainer.Coordinate;

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
	private double[] tick;
	private Coordinate[][] soundCoordinates;

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
			generateVisionEvent(agent, timeStamp);
		}
		
	}
	
	private void generateVisionEvent(Agent agent, double timeStamp){
		Vision vision = new Vision(timeStamp);
		//TODO calculate what the agent can see and make a ?list? of it
		agent.giveEvent(vision);
	}
	
	// Split the map into sub section.
	private void initializeSoundOnMap(){
		
		int sectionSize = 25;
		int height = map.getMapHeight();
		int width = map.getMapWidth();
		
		int nHeight = height / sectionSize;
		int nWidth = width / sectionSize;
		
		int extraHeight = height % sectionSize;
		int extraWidth = width % sectionSize;
		
		int nSections = nWidth*nHeight;
		
		//if(extraHeight != 0){
		//	nSections += nHeight;
		//}
		//
		//if(extraWidth != 0){
		//	nSections += nWidth;
		//}
		
		tick = new double[nSections];
		// The first entry is the bottomLeft corner the Second is the top right
		// of each sub section.
		soundCoordinates = new Coordinate[nSections][2];
		int k = 0;
		for (int i=0; i<nWidth ; i++){
			for (int j=0; j<nHeight ; j++){
				tick[k] = 0;
				soundCoordinates[k][0] = new Coordinate(i*25,j*25,0);
				soundCoordinates[k][1] = new Coordinate((i+1)*25,(j+1)*25,0);
				k++;
			}
		}
		
		
		
	}
	
	// Generates the random sound for each soundCoordinate for each tick
	public void generateRandomSound(){
		
		int nSections = tick.length;
		
		// generate a random sound for each map sub section.
		for(int i = 0; i<nSections ; i++){
		//
		//Sharon will finish this part
		//
		//
		}
	}
	
	
	private void generateMoveSoundEvent(Agent agent, double timeStamp, double speed){
		//TODO check if the standardDeviation is applied correctly
		/* in degree */
		double standardDeviation = 10;
		double variance = Math.pow(standardDeviation, 2);
		
		/*variance in rad */
		double varianceRad = Math.toRadians(variance);
		
		double distence = 0;
		if (speed >0 && speed < 0.5)
			distence = 1;
		else if (speed < 1)
			distence = 3;
		else if (speed < 2)
			distence = 5;
		else
			distence = 10;
		
		
		for (Agent tempAgent: spriteManager.getAgentList())
			if (distenceBetweenAgents(agent, tempAgent) <= distence){
				/* calculate the angle under witch the agent hears the sound and add the variance to it */
				double dx = agent.getCoordinates().x - tempAgent.getCoordinates().x;
				double dy = agent.getCoordinates().y - tempAgent.getCoordinates().y;
				double direction = (Math.atan2(dy, dx) + (Math.random()*(varianceRad*2) - varianceRad)) % 2 * Math.PI;
				if (direction < 0) direction += 2*Math.PI;
				
				Sound sound = new Sound(timeStamp, direction, tempAgent.getCoordinates().clone());
				tempAgent.giveEvent(sound);
			}
	}
	
	private double distenceBetweenAgents(Agent agent1, Agent agent2){
		return Coordinate.distenceBetweenCoordinates(agent1.getCoordinates(), agent2.getCoordinates());
	}
}
