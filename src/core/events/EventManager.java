/**
 * 
 */
package core.events;

import java.util.ArrayList;
import java.util.Random;

import core.Map;
import core.actions.ActionElement;
import core.actions.Move;
import core.actions.Turn;
import core.actions.Wait;
import core.sprite.Agent;
import core.sprite.Sprite;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;

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
		double timeStamp = agent.getTimeKey();
		if ( actionElement instanceof Turn){
			//triger a vision event
		}

		/* this handles the move actionElement (THIS NEEDS TO BE IMPLEMENTED) */
		else if ( actionElement instanceof Move){
			//TODO dont trigger this event when a guard is entering a tower leaving a tower or when the agent turns for more then 45 degrees (page 4) 
			if(map.getCopyOfMap()[agent.getCoordinates().x][agent.getCoordinates().y] == GridState.Door)
				generateDoorSoundEvent(agent, timeStamp);
			if(map.getCopyOfMap()[agent.getCoordinates().x][agent.getCoordinates().y] == GridState.Window)
				generateWindowSoundEvent(agent, timeStamp);
			
			generateVisionEvent(agent, timeStamp);
			generateMoveSoundEvent(agent, timeStamp, ((Move)actionElement).speed);
		}

		/* handle the wait ActionElement */
		else if ( actionElement instanceof Wait){
			generateVisionEvent(agent, timeStamp);
		}
		
	}
	
	//Checks if a coordinate is clockwise to a vector
	private Boolean isClockWise(double x, double y,double xT,double yT){
		return -x*yT + y*xT > 0;
	}
	
	//Checks if a coordinate is within radius
	private Boolean isInRadius(Coordinate target,Coordinate baseCoord, double r){
		double distence =  Coordinate.distanceBetweenCoordinates(target, baseCoord);
		return distence <= r;
	}
	
	
	private Boolean isInView (Coordinate target,double angle ,double r, Coordinate baseCoord) 
	{ 
		//initAngle is the orientation of the agent
		double initAngle = baseCoord.angle;
		
		//Setting view barriers
		double x1 = r*Math.cos(initAngle-angle/2);
		double y1 = r*Math.sin(initAngle-angle/2);
		
		double x2 = r*Math.cos(initAngle+angle/2);
		double y2 = r*Math.sin(initAngle+angle/2);
		
		double xTarget = target.x - baseCoord.x;
		double yTarget = target.y - baseCoord.y;
		
		
		//Returns true if target is in view range
		return !isClockWise(x1,y1,xTarget,yTarget) &&
				isClockWise(x2,y2,xTarget,yTarget) &&
				isInRadius (target,baseCoord,r);
		
	}
	
	private double getBlockingAngle(Coordinate base,Coordinate block){
		
		if((base.x > block.x && base.y > block.y) || (base.x < block.x && base.y < block.y))
			return Math.abs(base.getAngle(new Coordinate(block.x+1,block.y-1,0)) - 
					base.getAngle(new Coordinate(block.x-1,block.y+1,0)));
		if((base.x < block.x && base.y > block.y) || (base.x > block.x && base.y < block.y))
			return Math.abs(base.getAngle(new Coordinate(block.x+1,block.y+1,0)) - 
					base.getAngle(new Coordinate(block.x-1,block.y-1,0)));
		if(base.x == block.x)
				return Math.abs(base.getAngle(new Coordinate(block.x,block.y+1,0)) - 
								base.getAngle(new Coordinate(block.x,block.y-1,0)));
		else
			return Math.abs(base.getAngle(new Coordinate(block.x+1,block.y,0)) - 
							base.getAngle(new Coordinate(block.x-1,block.y,0)));
		
	}
	
	private void generateVisionEvent(Agent agent, double timeStamp){
		
		Vision vision = new Vision(timeStamp);
		vision.setBaseCoords(agent.getCoordinates().clone());
		double visionAngle = agent.getVisionAngleRad();
		double minVisionRange = agent.getCurrentMinVisionRange();
		double maxVisionRange = agent.getCurrentMaxVisionRange();
		double towerVisionRange = agent.getTowerVisionRange();
		double structureVisionRange = agent.getStructureVisionRange();
		
		
		Coordinate baseCoords = agent.getCoordinates();
		
		
		//Adding the states (towers,walls,trees) in the vision to the Grid hash map
		GridState[][] state = map.getCopyOfMap();
		for (int i=0; i<map.getMapWidth();i++){
			for (int j=0; j<map.getMapHeight();j++){
				
				if (!isInView(new Coordinate(i,j,0),visionAngle,minVisionRange,baseCoords) &&
						isInView(new Coordinate(i,j,0),visionAngle,maxVisionRange,baseCoords))
					vision.addGrid(new Coordinate(i,j,0), state[i][j]);
				else {
					
					// Adds the further centuries to the vision
					if (state[i][j] == GridState.Sentry)
					if (!isInView(new Coordinate(i,j,0),visionAngle,maxVisionRange,baseCoords) &&
						isInView(new Coordinate(i,j,0),visionAngle,towerVisionRange,baseCoords))
					vision.addGrid(new Coordinate(i,j,0), state[i][j]);
					
					// Adds the structures to the vision
					if (state[i][j] == GridState.Door ||
						state[i][j] == GridState.OuterWall ||
						state[i][j] == GridState.Wall ||
						state[i][j] == GridState.Window)
					if (!isInView(new Coordinate(i,j,0),visionAngle,maxVisionRange,baseCoords) &&
							isInView(new Coordinate(i,j,0),visionAngle,structureVisionRange,baseCoords))
						vision.addGrid(new Coordinate(i,j,0), state[i][j]);
				
				}
			}
		}
		
		
		//Deleting the Areas behind the walls
		double blockAngle = 0;
		ArrayList<Coordinate> remove = new ArrayList<Coordinate>();
		for(Coordinate barrier : vision.getStateInVisionMap().keySet())
		{
			if(vision.getStateInVisionMap().get(barrier) == GridState.Wall)
			{
				
				blockAngle = getBlockingAngle(baseCoords,barrier);
				Coordinate newBase = baseCoords.clone();
				newBase.angle = baseCoords.getAngle(barrier);
				for(Coordinate gridCheck : vision.getStateInVisionMap().keySet()){
					if (!isInView(gridCheck,blockAngle,baseCoords.distance(barrier),newBase) &&
							isInView(gridCheck,blockAngle,maxVisionRange,newBase)){
						remove.add(gridCheck);
					}
				}
			}
		}
		vision.deleteGrid(remove);
		
		//Adding the sprite in the vision to the Sprite hash map
				for (Sprite sprite: spriteManager.getAgentList()){
					if (!isInView(sprite.getCoordinates(),visionAngle,minVisionRange,baseCoords) &&
							isInView(sprite.getCoordinates(),visionAngle,maxVisionRange,baseCoords) &&
							vision.getStateInVisionMap().containsKey(sprite.getCoordinates())){
						vision.addSprite(sprite.getCoordinates(), sprite);
					}
				}
		
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
		if(tick[nrOfArea][0] >= tick[nrOfArea][1]){ //Coordinate.distanceBetweenCoordinates()
			for (Agent tempAgent: spriteManager.getAgentList()) {
				Coordinate soundCoo = new Coordinate((int)Math.random()*(bottomLeft.x-topRight.x), (int)Math.random()*(bottomLeft.y-topRight.y),0);
				if(Coordinate.distanceBetweenCoordinates(soundCoo, tempAgent.getCoordinates()) <= distanceSound){
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
	private void generateDoorSoundEvent(Agent agent, double timeStamp){
		generateSoundEvent(agent, timeStamp, 5);
	}
	private void generateWindowSoundEvent(Agent agent, double timeStamp){
		generateSoundEvent(agent, timeStamp, 10);
	}
	private void generateMoveSoundEvent(Agent agent, double timeStamp, double speed){
		//TODO check if the standardDeviation is applied correctly
		
		
		double distance = 0;
		if (speed >0 && speed < 0.5)
			distance = 1;
		else if (speed < 1)
			distance = 3;
		else if (speed < 2)
			distance = 5;
		else
			distance = 10;
		
		generateSoundEvent(agent, timeStamp, distance);
	}
	
	private void generateSoundEvent(Agent agent, double timeStamp, double distance){
		/* in degree */
		double standardDeviation = 10;
		double variance = Math.pow(standardDeviation, 2);
		
		/*variance in rad */
		double varianceRad = Math.toRadians(variance);

		for (Agent tempAgent: spriteManager.getAgentList())
			if (distenceBetweenAgents(agent, tempAgent) <= distance){
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
		return Coordinate.distanceBetweenCoordinates(agent1.getCoordinates(), agent2.getCoordinates());
	}
}
