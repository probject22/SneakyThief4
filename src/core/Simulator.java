package core;

import core.actions.*;
import core.events.EventManager;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;
import dataContainer.MoveDirection;
import gui.MainFrame;

/**
 *
 * A controller of the SpriteManager and the Map.
 *
 * Created by Stan on 08/04/15.
 */
public class Simulator {
	private boolean debug = true;
    private static boolean stop = false;
    private static boolean pause = true;
    private double speed = 1;
    
    public static void setStop(boolean newvalue)
    {
    	stop = newvalue;
    }
    public static boolean getStop()
    {
    	return stop;
    }
    public static void setPause(boolean newvalue)
    {
    	pause = newvalue;
    }
    
    public static boolean getPause()
    {
    	return pause;
    }
	public Simulator(){
		if (debug) System.err.println("The simulator has been started");
		
		map = new Map();
		
		spriteManager = SpriteManager.instance();
		spriteManager.addAgent(new Agent(new Coordinate(3,3,Math.toRadians(45))));
		spriteManager.addAgent(new Agent(new Coordinate(20,20,0.0)));
		eventManager = new EventManager(map);
		/*to get the agent list call spriteManager.getAgentList(); */
		
		/* gui stuff */
		mainFrame = new MainFrame();
		mainFrame.setMap(map);
		
		gameLoop();
	}
	
	private void gameLoop(){
		mainFrame.updateGui();
		while (!stop){
			while (pause && !stop){
				sleep(0.1);
			}
			firstAgentAction();
			/* finish the move by updateting the gui */
			mainFrame.updateGui();
			sleep(speed);
		}
		System.out.println("Stop");
		System.exit(0);
	}
	/**
	 * This puts the current thread into sleep
	 * @param time the time that the thread has to sleep in seconds
	 */
	private void sleep(double time){
		time *= 1000;
		try {
			Thread.sleep((long)time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function checks if a move is possible
	 * TODO: check diagonal movement.
	 * this check should be done both on the map and against the list of agents
	 * @param agent	The agent who want to do the move
	 * @param move the move the agent wants to perform
	 * @return true if the move is possible else false
	 */
	private boolean isMovePossible(Agent agent, Move move) {
		
		Coordinate coordinate = agent.getCoordinates().clone();
		MoveDirection dir = MoveDirection.getDirectionFromAngle(coordinate.angle);
		coordinate.x += dir.getDx();
		coordinate.y += dir.getDy();
		GridState[][] tempMap = map.getCopyOfMap();
		if (coordinate.x < 0 && coordinate.y < 0)
			return false;
		if (!(coordinate.x < tempMap.length && coordinate.y < tempMap[0].length))
			return true;
		if (tempMap[coordinate.x][coordinate.y].moveable()){
			for (Agent tempAgent: spriteManager.getAgentList()){
				Coordinate coords = tempAgent.getCoordinates();
				if ((coords.x == coordinate.x && coords.y == coordinate.y))
					return false; // if there is an agent in the way
			}
			return true; // if there is nothing in the way
		}
		return false; // if the map spot is already occupied by an object
	}

	/**
	 * Get the action list of the agent who spend the least duration and execute the list
	 */
	public void firstAgentAction(){
		Agent agent = spriteManager.getFirstAgent();
		
		/*trigger the wait event to send an update of the vision to the agent */
		eventManager.triggerEvent( new Wait(0),agent);
		
		Action agentAction = agent.getAction();

		for (ActionElement actionElement: agentAction.getActionElements()){

			/*
			Find a way of using polymorphism to do this instead of a switch
			 */



			/* this handles the turn actionElement */
			if ( actionElement instanceof Turn){
				//System.err.println("Current angle "+ agent.getCoordinates().angle + "add " + ((Turn) actionElement).getAngle());
				agent.getCoordinates().addToAngle(((Turn) actionElement).getAngle());
				//System.err.println("result "+ agent.getCoordinates().angle);
				agent.addTimeToKey(actionElement.duration());
				eventManager.triggerEvent(actionElement,agent);
			}

			/* this handles the move actionElement (THIS NEEDS TO BE IMPLEMENTED) */
			if ( actionElement instanceof Move){
				Move move = (Move) actionElement;
				if(isMovePossible(agent, move)){
					Coordinate coordinate = agent.getCoordinates();
					MoveDirection dir = MoveDirection.getDirectionFromAngle(coordinate.angle);
					coordinate.x += dir.getDx();
					coordinate.y += dir.getDy();
					
					
					agent.addTimeToKey(actionElement.duration());
					//Always trigger events AFTER EXECUTION OF THE ACTIONELEMENT
					eventManager.triggerEvent(actionElement,agent);
				}
			}

			/* handle the wait ActionElement */
			if ( actionElement instanceof Wait){
				agent.addTimeToKey( actionElement.duration());
				
				eventManager.triggerEvent(actionElement,agent);
			}

		}



		/* update the duration witch was spend to execute the agentAction */
		;

		/* put the agent back into the queue with a new duration */
		spriteManager.addAgent(agent);
		
		//System.err.println(agent.getCoordinates().toString());
	}


    public static void main(String [] args){
    	new Simulator();
    }
    
	private Map map;
	private SpriteManager spriteManager;
	private EventManager eventManager;
	private MainFrame mainFrame;
}
