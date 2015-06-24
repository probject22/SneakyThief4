/**
 *
 */
package core.sprite;

import gui.BeliefMapGui;

import java.util.ArrayList;

import core.BeliefMap;
import core.DebugConstants;
import core.Map;
import core.Algorithms.Exploration;
import core.Algorithms.PathFinder;
import core.Algorithms.AStar.AStar;
import core.Algorithms.BasicExploration.BasicRandomExploration;
import core.Algorithms.LRTAStar.LRTAStar;
import core.Algorithms.RTAStar.MapRTAStar;
import core.Algorithms.RTAStar.RealTimeAStar;
import core.Algorithms.RTAStar2.MapAdapter;
import core.actions.Action;
import core.actions.Move;
import core.actions.Turn;
import core.events.Event;
import core.events.Sound;
import core.events.Vision;
import dataContainer.BlackBoard;
import dataContainer.Coordinate;

/**
 *
 * The integral intelligent class.
 *
 * The agent will have various systems:
 *
 * A belief that represents all that the agents knows and thinks about the world around him.
 *
 * A Plan that consists of a sequence of actions that an agent wants to perform.
 *
 * An interpreter that takes on events from the world around the agent and incorporates them
 * into the belief.
 *
 * A duration which denotes how much duration the agent has in total spent making actions.
 *
 *
 * @author ing. R.J.H.M. Stevens
 */
public class Agent extends Sprite {

	protected BlackBoard blackboard;
	public static final double MAX_SPEED = 1.4;
	public static final double MAX_ANG_VEL = 1;
	protected Map beliefMap;
	protected Vision lastSeen;
	
	private Exploration exploration;
	
	// Set the Target Coordinate
	protected Coordinate target = new Coordinate(15,10	,0);
	
	
	private PathFinder<Coordinate> pathFinder;
	private PathFinder<Coordinate> realTimePathFinder;
	
	protected ArrayList<Event> events = new ArrayList<Event>();
	
	private boolean debug = DebugConstants.agentDebug;
	private BeliefMapGui beliefMapGUi;


	public void setBeliefMap(Map map){
		this.beliefMap =  map;
		//beliefMapGUi.close();
		//beliefMapGUi = new  BeliefMapGui((Map)beliefMap, "test");
		//beliefMapGUi.updateGui();
		pathFinder = new AStar(beliefMap);
		realTimePathFinder = new LRTAStar(beliefMap);
		exploration.setBeliefMap(map);
	}

    public Agent(Coordinate coords) {
        super(coords);
        this.beliefMap =  new BeliefMap();
        //beliefMapGUi = new  BeliefMapGui((Map)beliefMap, "test");
	    // Create an A* pathfinder
	    pathFinder = new AStar(beliefMap);
	    realTimePathFinder = new LRTAStar(beliefMap);
	    exploration = new BasicRandomExploration(this,beliefMap);
	    
	    
    }

    /***********************************************************************************\
    *	All function that have something to do with the events that get returned 		*
    *	e.g. Vision, sound																*
    \***********************************************************************************/
	/**
	 * Method that processes events to update the belief state of the agent. The simulator will
	 * hand events to the agents using this method.
	 * @param event The event that the agent should know about.
	 */
	public void giveEvent(Event event){
		events.add(event);
		if (event instanceof Vision)
			processVision(event);
		if (event instanceof Sound && this instanceof Thief)
			((Thief)this).processSound(event);
		if (event instanceof Sound && this instanceof Guard)
			((Guard)this).processSound(event);
	}

	private void processVision(Event vision){
		processVision((Vision) vision);
	}
	private void processVision(Vision vision){
		if (debug) System.out.println("A vision event occured");
		if (beliefMap != null && beliefMap instanceof BeliefMap){
			((BeliefMap)beliefMap).processVision(vision);
		//	beliefMapGUi.updateGui();
		}
		lastSeen = vision;
		
		if (blackboard != null){
			Coordinate thiefCoord = blackboard.getThiefCoord();
			for (Coordinate c : vision.getStateInVisionMap().keySet()){
				if (thiefCoord != null && thiefCoord.x == c.x && thiefCoord.y == c.y)
					blackboard.updateThief(null);
			}

			for (Sprite s : lastSeen.getSpriteInVisionMap().values()){
				if (s instanceof Thief){
					blackboard.updateThief(s.getCoordinates().clone());
				}	
			}
		}
	}
	
	public Vision getLastSeen(){
		return lastSeen;
	}

	/**
	 * Get the action that the agent currently wants to perform. This will consist of only a single
	 * square with a certain speed, and possibly a turn.
	 * @return The action that the agents want to perform next
	 * 
	 * 
	 */
	public Action getAction(){
		return null;
		//return null;
	}
	
    /***********************************************************************************\
    *	All functions with create basic actions											*
    \***********************************************************************************/
	protected Action aStar(Coordinate goal){
		Action action = new Action();
		Coordinate next = pathFinder.getShortestPath(getCoordinates(), goal);
		if (next == null)
			return null;
		double angle = getCoordinates().angle;
		double goalAngle = getCoordinates().getAngle(next);
		action.addActionElement(new Turn(angle, goalAngle, Agent.MAX_SPEED));
		action.addActionElement(new Move(Agent.MAX_SPEED));
		
		return action;
	}
	
	
	protected Action rTAStar(Coordinate goal){
		Action action = new Action();
		Coordinate next = realTimePathFinder.getShortestPath(getCoordinates(), goal);
		if (next == null)
			return null;
		double angle = getCoordinates().angle;
		double goalAngle = getCoordinates().getAngle(next);
		action.addActionElement(new Turn(angle, goalAngle, Agent.MAX_SPEED));
		action.addActionElement(new Move(Agent.MAX_SPEED));
		
		return action;
	}
	
	protected Action basicExploration(){
		Action action = new Action();
		Coordinate goal = exploration.getGoal();
		if (goal == null)
			return null;
		Coordinate next = pathFinder.getShortestPath(getCoordinates(), goal);
		if (next == null)
			return null;
		double angle = getCoordinates().angle;
		double goalAngle = getCoordinates().getAngle(next);
		action.addActionElement(new Turn(angle, goalAngle, Agent.MAX_SPEED));
		action.addActionElement(new Move(Agent.MAX_SPEED));
		
		return action;
	}



	/**
	 * increments the time of the agent.
	 * @param time the time to be added to the current.
	 */
	public void addTimeToKey(double time){
		timeKey += time;
		if (timeKey < 0){
			System.err.println("The value of the timekey was to big and became negative");
			System.exit(-1);
		}
	}

	/**
	 * Returns the current timeKey of the agent.
	 * @return current timeKey of the agent
	 */
	public double getTimeKey(){
		return timeKey;
	}
	
	public double getMinVisionRange(){
		return this.minVisionRange;
	}
	
	public double getMaxVisionRange(){
		return this.maxVisionRange;
	}
	
	public double getVisionAngleRad(){
		return Math.toRadians(this.visionAngle);
	}
	public double getStructureVisionRange() {
		return structureVisionRange;
	}

	public double getTowerVisionRange() {
		return towerVisionRange;
	}
	
	/**
	 * @return the currentminVisionRange
	 */
	public double getCurrentMinVisionRange() {
		return currentminVisionRange;
	}

	/**
	 * @return the currentMaXVisionRange
	 */
	public double getCurrentMaxVisionRange() {
		return currentMaXVisionRange;
	}
	
	/* the vision variable */
	protected double minVisionRange = 0;
	protected double maxVisionRange = 10;
	protected double visionAngle = 45;
	protected double structureVisionRange = 10;
	protected double towerVisionRange = 15;

	protected double currentminVisionRange = minVisionRange;
	protected double currentMaXVisionRange = maxVisionRange;
	
	private double timeKey;

	public void setPathFinder (PathFinder<Coordinate> pathFinder) {
		this.pathFinder = pathFinder;
	}
	
	public Map getBeliefMap(){
		return beliefMap;
	}
}
