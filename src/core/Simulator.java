package core;

import core.actions.*;
import core.events.EventManager;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;
import gui.MainFrame;

/**
 *
 * A controller of the SpriteManager and the Map.
 *
 * Created by Stan on 08/04/15.
 */
public class Simulator {
	private boolean debug = true;
    private boolean stop = false;
    private boolean pause = false;
    private double speed = 1.0;
	public Simulator(){
		if (debug) System.err.println("The simulator has been started");
		
		map = new Map();
		
		spriteManager = SpriteManager.instance();
		spriteManager.addAgent(new Agent(new Coordinate(100,100,0.0)));
		spriteManager.addAgent(new Agent(new Coordinate(200,200,0.0)));
		eventManager = new EventManager(map);
		/*to get the agent list call spriteManager.getAgentList(); */
		
		/* gui stuff */
		mainFrame = new MainFrame();
		mainFrame.setMap(map);
		
		gameLoop();
	}
	
	private void gameLoop(){
		while (!stop){
			firstAgentAction();
			/* finish the move by updateting the gui */
			mainFrame.updateGui();
			sleep(speed);
			while (pause){
				sleep(0.1);
			}
				
		}
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
		Coordinate coordinate = agent.getCoordinates();
		coordinate.x += move.direction().getDx();
		coordinate.y += move.direction().getDy();
		GridState[][] tempMap = map.getCopyOfMap();
		if (coordinate.x < 0 && coordinate.y < 0)
			return false;
		if (!(coordinate.x < tempMap.length && coordinate.y < tempMap[0].length))
			return true;
		if (tempMap[coordinate.x][coordinate.y].moveable()){
			for (Agent tempAgent: spriteManager.getAgentList()){
				Coordinate coords = tempAgent.getCoordinates();
				if ((coords.x == coordinate.x || coords.y == coordinate.y))
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
		Action agentAction = agent.getAction();
		double timeSpend = 0;

		for (ActionElement actionElement: agentAction.getActionElements()){

			/*
			Find a way of using polymorphism to do this instead of a switch
			 */



			/* this handles the turn actionElement */
			if ( actionElement instanceof Turn){
				agent.getCoordinates().addToAngle(((Turn) actionElement).getAngle());
				
				timeSpend += actionElement.duration();
				eventManager.triggerEvent(actionElement,agent);
			}

			/* this handles the move actionElement (THIS NEEDS TO BE IMPLEMENTED) */
			if ( actionElement instanceof Move){
				Move move = (Move) actionElement;
				if(isMovePossible(agent, move)){
					agent.move(move);

					timeSpend += actionElement.duration();
					
					//Always trigger events AFTER EXECUTION OF THE ACTIONELEMENT
					eventManager.triggerEvent(actionElement,agent);
				}
			}

			/* handle the wait ActionElement */
			if ( actionElement instanceof Wait){
				timeSpend += actionElement.duration();
				
				eventManager.triggerEvent(actionElement,agent);
			}

		}



		/* update the duration witch was spend to execute the agentAction */
		agent.addTimeToKey(timeSpend);

		/* put the agent back into the queue with a new duration */
		spriteManager.addAgent(agent);
	}


    public static void main(String [] args){
    	new Simulator();
    }
    
	private Map map;
	private SpriteManager spriteManager;
	private EventManager eventManager;
	private MainFrame mainFrame;
}
