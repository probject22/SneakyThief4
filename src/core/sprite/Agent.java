/**
 *
 */
package core.sprite;

import core.Algorithms.AStar.MapAStar;
import core.Algorithms.BES.BES;
import core.Algorithms.BeliefMap.BeliefMap;
import core.Algorithms.PathFinder;
import core.Algorithms.ThiefPath;
import core.DebugConstants;
import core.Map;
import core.Simulator;
import core.actions.Action;
import core.actions.Move;
import core.actions.Turn;
import core.events.Event;
import core.events.Vision;
import dataContainer.Coordinate;
import gui.BeliefMapGui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import core.Algorithms.Coverage.Stico.*;
import core.Algorithms.reverseRTAStar.MapReverseRTAStar;
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

	public static final double MAX_SPEED = 2.4;
	public static final double MAX_ANG_VEL = 1;
	private BeliefMap beliefMap;
	protected Vision lastSeen;
	// Set the Target Coordinate
	protected Coordinate target = new Coordinate(18,18,0);
	
	protected Stico staco;
	private PathFinder<Coordinate> pathFinder;
	private ThiefPath<Coordinate> thiefPath;
	
	private ArrayList<Event> events = new ArrayList<Event>();
	
	private boolean debug = DebugConstants.agentDebug;
	private BeliefMapGui beliefMapGUi;


	public void setBeliefMap(Map map){
		this.beliefMap =  (BeliefMap) map;
	}

    public Agent(Coordinate coords) {
        super(coords);
        this.beliefMap =  new BeliefMap();
        beliefMapGUi = new  BeliefMapGui((Map)beliefMap, "test");
	    // Create an A* pathfinder
	    pathFinder = new MapAStar(beliefMap);
	    // Create an Escape map for Thief
	    thiefPath = new MapReverseRTAStar(beliefMap);
	    staco = new Stico(this);
    }

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
		if (beliefMap != null){
			beliefMap.processVision(vision);
			beliefMapGUi.updateGui();
		}
		else
			System.err.println("The beliefmap is null");
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
		Action action = staco.getMoveAction(lastSeen);
		
		// Check if there is a intruder in View (This should be done using belief map instead)!!
		//If yes Use BES to get Action
		//for (Sprite s : lastSeen.getSpriteInVisionMap().values())
		//	if (s instanceof Thief)
		//		action = BlockingES(s.getCoordinates());
		
		return action;
	}
	
	protected Action aStar(Coordinate goal){
		Action action = new Action();
		Coordinate next = pathFinder.getShortestPath(getCoordinates(), goal);
		double angle = getCoordinates().angle;
		double goalAngle = getCoordinates().getAngle(next);
		action.addActionElement(new Turn(angle, goalAngle, Agent.MAX_SPEED));
		action.addActionElement(new Move(Agent.MAX_SPEED));
		
		return action;
	}
	
	protected Action reverseAStar(List<Coordinate> followers){
		Action action = new Action();
		Coordinate next = thiefPath.getBestEscape(getCoordinates(),followers, target);
		double angle = getCoordinates().angle;
		double goalAngle = getCoordinates().getAngle(next);
		action.addActionElement(new Turn(angle, goalAngle, Agent.MAX_SPEED));
		action.addActionElement(new Move(Agent.MAX_SPEED));
		
		return action;
	}
	
	/**
	 * Returns an Blocking Action 
	 * according to the Blocking Coordinate
	 * found using BES
	 * @author Sina (21/05/2015)
	 */
	protected Action BlockingES(Coordinate intruder){
		Collection<Coordinate> otherAgents = lastSeen.getSpriteInVisionMap().keySet();
		Coordinate next = BES.getBlockingLocation(this.getCoordinates(),otherAgents,intruder);
		
		//Use A* or RTA* to get to the Blocking Coordinate.
		return aStar(next);
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

	private void setPathFinder (PathFinder<Coordinate> pathFinder) {
		this.pathFinder = pathFinder;
	}
}
