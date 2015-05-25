/**
 *
 */
package core.sprite;

import core.Algorithms.AStar.MapAStar;
import core.BeliefMap;
import core.Algorithms.PathFinder;
import core.DebugConstants;
import core.Map;
import core.actions.Action;
import core.actions.Move;
import core.actions.Turn;
import core.events.Event;
import core.events.Vision;
import dataContainer.Coordinate;
import gui.BeliefMapGui;

import java.util.ArrayList;

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

	public static final double MAX_SPEED = 1.4;
	public static final double MAX_ANG_VEL = 1;
	protected Map beliefMap;
	protected Vision lastSeen;
	
	// Set the Target Coordinate
	protected Coordinate target = new Coordinate(18,18,0);
	
	
	private PathFinder<Coordinate> pathFinder;
	
	
	private ArrayList<Event> events = new ArrayList<Event>();
	
	private boolean debug = DebugConstants.agentDebug;
	private BeliefMapGui beliefMapGUi;


	public void setBeliefMap(Map map){
		this.beliefMap =  map;
		beliefMapGUi.updateGui();
		beliefMapGUi = new  BeliefMapGui((Map)beliefMap, "test");
	}

    public Agent(Coordinate coords) {
        super(coords);
        this.beliefMap =  new BeliefMap();
        beliefMapGUi = new  BeliefMapGui((Map)beliefMap, "test");
	    // Create an A* pathfinder
	    pathFinder = new MapAStar(beliefMap);
	    
	    
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
	}

	private void processVision(Event vision){
		processVision((Vision) vision);
	}
	private void processVision(Vision vision){
		if (debug) System.out.println("A vision event occured");
		if (beliefMap != null && beliefMap instanceof BeliefMap){
			((BeliefMap)beliefMap).processVision(vision);
			beliefMapGUi.updateGui();
		}
		else
			System.err.println("The beliefmap is null or its not an instance of the beliefmap");
		lastSeen = vision;
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
	}
	
    /***********************************************************************************\
    *	All functions with create basic actions											*
    \***********************************************************************************/
	protected Action aStar(Coordinate goal){
		Action action = new Action();
		Coordinate next = pathFinder.getShortestPath(getCoordinates(), goal);
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
	
	/* the vision variable */
	private double minVisionRange = 0;
	private double maxVisionRange = 10;
	private double visionAngle = 45;
	private double structureVisionRange = 10;
	private double towerVisionRange = 15;


	
	private double timeKey;

	public void setPathFinder (PathFinder<Coordinate> pathFinder) {
		this.pathFinder = pathFinder;
	}
}
