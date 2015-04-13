package core;

import core.actions.Action;
import core.actions.ActionElement;
import core.actions.Move;
import core.actions.Turn;
import core.events.EventManager;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import gui.MainFrame;

/**
 *
 * A controller of the SpriteManager and the Map.
 *
 * Created by Stan on 08/04/15.
 */
public class Simulator {
	private boolean debug = true;
    
	public Simulator(){
		if (debug) System.err.println("The simulator has been started");
		
		map = new Map();
		
		spriteManager = SpriteManager.instance();
		spriteManager.addAgent(new Agent(new Coordinate(100,100,0.0)));
		spriteManager.addAgent(new Agent(new Coordinate(200,200,0.0)));
		eventManager = new EventManager(map);
		/*to get the agent list call spriteManager.getAgentList(); */
		
		/* gui stuff */
		MainFrame mainFrame = new MainFrame();
		mainFrame.setMap(map);
		mainFrame.updateGui();
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
		if (map.getCopyOfMap()[coordinate.x][coordinate.y].moveable()){
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
				//TODO check if turn is valid
				eventManager.triggerEvent(actionElement,agent);
			}

			/* this handles the move actionElement (THIS NEEDS TO BE IMPLEMENTED) */
			if ( actionElement instanceof Move){
				Move move = (Move) actionElement;
				if(isMovePossible(agent, move)){
					agent.move(move);

					//Always trigger events AFTER EXECUTION OF THE ACTIONELEMENT
					eventManager.triggerEvent(actionElement,agent);
				}
			}

			/* handle the wait ActionElement */

		}



		/* update the duration witch was spend to execute the agentAction */
		agent.addTimeToKey(agentAction.duration());

		/* put the agent back into the queue with a new duration */
		spriteManager.addAgent(agent);
	}


    public static void main(String [] args){
    	new Simulator();
    }
    
	private Map map;
	private SpriteManager spriteManager;
	private EventManager eventManager;
}
