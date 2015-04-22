/**
 * 
 */
package core.events;

import java.util.Random;

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
	private double[][] tick;
	private Coordinate[][] soundCoordinates;
	private double lambda = 6;

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
		vision.setBaseCoords(agent.getCoordinates().clone());
		//TODO calculate what the agent can see and make a ?list? of it
		agent.giveEvent(vision);
	}
	
	// Split the map into sub section.
	// Has to be initialized when map gets created
	public void initializeSoundOnMap(){
		
		int sectionSize = 25;
		int height = map.getMapHeight();
		int width = map.getMapWidth();
		
		int nHeight = height / sectionSize;
		int nWidth = width / sectionSize;
		
		int extraHeight = height % sectionSize;
		int extraWidth = width % sectionSize;
		
		int nSections = nWidth*nHeight;
		
		if(extraHeight != 0){
			nSections += nHeight;
		}
		
		if(extraWidth != 0){
			nSections += nWidth;
		}
		
		if(extraWidth != 0 && extraHeight != 0){
			nSections ++;
		}
		
		//tick[currentTick][nextRandomSoundEvent]
		tick = new double[nSections][2];
		// The first entry is the bottomLeft corner the Second is the top right
		// of each sub section.
		soundCoordinates = new Coordinate[nSections][2];
		int k = 0;
		for (int i=0; i<nWidth ; i++){
			for (int j=0; j<nHeight ; j++){
				tick[k][0] = 0;
				tick[k][1] = generateNextSound();
				soundCoordinates[k][0] = new Coordinate(i*25,j*25,0);
				soundCoordinates[k][1] = new Coordinate((i+1)*25,(j+1)*25,0);
				k++;
			}
		}
		
		if(extraHeight != 0){
			for (int i=0; i<nWidth ; i++){
				tick[k][0] = 0;
				tick[k][1] = generateNextSound();
				soundCoordinates[k][0] = new Coordinate(i*25,(nHeight-1)*25,0);
				soundCoordinates[k][1] = new Coordinate((i+1)*25,nHeight*extraHeight,0);
				k++;
			}				
		}
		
		if(extraWidth != 0){
			for (int j=0; j<nHeight ; j++){
				tick[k][0] = 0;
				tick[k][1] = generateNextSound();
				soundCoordinates[k][0] = new Coordinate((nWidth-1)*25,j*25,0);
				soundCoordinates[k][1] = new Coordinate(nWidth*extraWidth,(j+1)*25,0);
				k++;
			}				
		}
		
		if(extraWidth != 0 && extraHeight != 0){
			tick[k][0] = 0;
			tick[k][1] = generateNextSound();
			soundCoordinates[k][0] = new Coordinate((nWidth-1)*25,(nHeight-1)*25,0);
			soundCoordinates[k][1] = new Coordinate(nWidth*extraWidth,nHeight*extraHeight,0);
		}
		
	}
	
	//Generate when next event happens (in secs)
	private double generateNextSound(){
		Random rand = new Random();
		return -Math.log(1.0 - rand.nextDouble()) / lambda;
	}
	
	// Generates the random sound for each soundCoordinate for each tick
	public void randomSoundOnMap(double plusTick){
		
		int nSections = tick.length;
		
		// generate a random sound for each map sub section.
		for(int i = 0; i<nSections ; i++){
			generateRandomSound(i, plusTick, soundCoordinates[i][0], soundCoordinates[i][1]);
		}
	}
	
	//for one 25m2 area 
	private void generateRandomSound(int nrOfArea, double plusTick, Coordinate bottomLeft, Coordinate topRight){ 
		
		double distanceSound = 5;
		
		tick[nrOfArea][0] = tick[nrOfArea][0] + plusTick;
		if(tick[nrOfArea][0] >= tick[nrOfArea][1]){ //Coordinate.distenceBetweenCoordinates()
			for (Agent tempAgent: spriteManager.getAgentList()) {
				Coordinate soundCoo = new Coordinate((int)Math.random()*(bottomLeft.x-topRight.x), (int)Math.random()*(bottomLeft.y-topRight.y),0);
				if(Coordinate.distenceBetweenCoordinates(soundCoo,tempAgent.getCoordinates()) <= distanceSound){ 
					//calculate the angle under which the agent hears the sound and add the uncertainty to it
					double dx = soundCoo.x - tempAgent.getCoordinates().x;
					double dy = soundCoo.y - tempAgent.getCoordinates().y;
					//Math.pow(StandardDeviation,2)
					double varianceRad = Math.toRadians(Math.pow(10, 2));
					double direction = (Math.atan2(dy, dx) + (Math.random()*(varianceRad*2) - varianceRad)) % 2 * Math.PI;
					if (direction < 0) direction += 2*Math.PI;
					
					Sound sound = new Sound(tempAgent.getTimeKey(), direction, tempAgent.getCoordinates().clone());
					tempAgent.giveEvent(sound);
					
					tick[nrOfArea][0] = 0;
					tick[nrOfArea][1] = generateNextSound();
				}
			}
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
