package core;

import java.util.ArrayList;
import java.util.Random;

import gui.MainFrame;
import core.actions.Action;
import core.actions.ActionElement;
import core.actions.Move;
import core.actions.Turn;
import core.actions.Wait;
import core.events.EventManager;
import core.sprite.Agent;
import core.sprite.Guard;
import core.sprite.Sprite;
import core.sprite.SpriteManager;
import core.sprite.Thief;
import dataContainer.BlackBoard;
import dataContainer.Coordinate;
import dataContainer.GridState;
import dataContainer.MoveDirection;

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
    private double speed = 0.000001;
    
    public int preyWinLoss = 0;
    public double preyTime;

	public void setSpeed(double newSpeed) {
		speed = newSpeed;
	}
	public static void setStop(boolean newvalue) {
		stop = newvalue;
	}
	public static boolean getStop() {
		return stop;
	}
	public static void setPause(boolean newvalue) {
		pause = newvalue;
	}

	public static boolean getPause() {
		return pause;
	}

	public Simulator() {
		if (debug)
			System.err.println("The simulator has been started");

		 map = new Map();
		// map = new Map("test100.map");
		// map = map.maze(map.getMapWidth(),map.getMapHeight());
		//map = new Map("default.map");
		//map = new Map("empty.map");

		spriteManager = SpriteManager.instance();

		//addGuard(new Coordinate(19, 19, 0));	
		//addGuard(new Coordinate(18, 18, 0));
		//addGuard(new Coordinate(17, 17, 0));
		//addGuard(new Coordinate(16, 16, 0));
		//addThief(new Coordinate(1,1,0), map);

		eventManager = new EventManager(map);

		/* to get the agent list call spriteManager.getAgentList(); */

		/* gui stuff */
		mainFrame = new MainFrame();
		mainFrame.setMap(map);

		gameLoop();
	}
	
	//Use this for StiCo Experiment
	public Simulator(int guards, Map map){
		if (debug)
			System.err.println("The simulator has been started");

		// map = new Map();
		// map = new Map("test100.map");
		// map = map.maze(map.getMapWidth(),map.getMapHeight());
		this.map = map;

		spriteManager = SpriteManager.instance();

		for(int i =0;i<guards;i++){
			
			addGuard(new Coordinate((int)Math.random()*map.getMapWidth(), (int)Math.random()*map.getMapHeight(), 0));
		}

		eventManager = new EventManager(map);

		/* to get the agent list call spriteManager.getAgentList(); */

		/* gui stuff (DONT NEED LOL)
		mainFrame = new MainFrame();
		mainFrame.setMap(map);*/
		gameLoop();
	}
	
	//Use this for prey Experiment
		public Simulator(int guards,ArrayList<Coordinate> placable, Map map){
			if (debug)
				System.err.println("The simulator has been started");

			// map = new Map();
			// map = new Map("test100.map");
			// map = map.maze(map.getMapWidth(),map.getMapHeight());
			this.map = map;

			spriteManager = SpriteManager.instance();
			eventManager = new EventManager(map);
			
			
			GridState[][] grid = map.getCopyOfMap();
			Random randomGenerator = new Random();
			
			//Adding the thief
			addThief(new Coordinate(1,1,0));
			
			// deleting target and start coordinate from placables
			placable.remove(placable.size()-1);
			placable.remove(0);
			
			BlackBoard blackBoard = new BlackBoard();
			
			for(int i =0;i<guards;i++){
				addGuard(placable.remove(randomGenerator.nextInt(placable.size())),blackBoard);
				
			}

			

			/* to get the agent list call spriteManager.getAgentList(); */

			/* gui stuff */

			preyWinLoss = gameLoop(true);
			
			preyTime = 0;
			for(Agent s: spriteManager.getAgentList())
				if (s instanceof Thief){
					preyTime = s.getTimeKey();
					break;
				}
			
		}	
		
		
		
	public void addGuard(Coordinate coord, Map beliefMap, BlackBoard blackboard){
		Sprite sprite = new Guard(coord);
		((Agent) sprite).setBeliefMap(beliefMap);
		((Guard) sprite).setBlackBoard(blackboard);
		spriteManager.addAgent((Agent) sprite);
	}
	public void addGuard(Coordinate coord, BlackBoard blackboard){
		addGuard(coord, new BeliefMap(map), blackboard);
	}
	public void addGuard(Coordinate coord, Map beliefMap){
		addGuard(coord, beliefMap, new BlackBoard());
	}
	
	public void addGuard(Coordinate coord){
		addGuard(coord, new BeliefMap(map));
	}
	
	public void addThief(Coordinate coord, Map beliefMap){
		Sprite sprite = new Thief(coord);
		((Agent) sprite).setBeliefMap(beliefMap);
		spriteManager.addAgent((Agent) sprite);
	}
	
	public void addThief(Coordinate coord){
		addThief(coord, new BeliefMap(map));
	}


	private void gameLoop() {
		mainFrame.updateGui();
		// 0 = running, 1 = thieves win, 2 = thieves loss
		int winLossValue = 0;
		while (!stop) {
			while (pause && !stop) {
				sleep(0.1);
			}
			firstAgentAction();
			for (Agent f : spriteManager.getAgentList()) {
				if (f instanceof Thief) {
					if (((Thief) f).atGoal()) {
						winLossValue = 1;
					}
				}
				else if (f instanceof Guard)
				{
					if (((Guard)f).hasCaught())
						winLossValue = 2;
				}
			}
			if (winLossValue == 1) {
				System.out.println("VERY SNEAKY THIEF!");
				pause = !pause;
			}
			if (winLossValue == 2) {
				System.out.println("NOT A SNEAKY THIEF!");
				pause = !pause;
			}
			/* finish the move by updateting the gui */
			mainFrame.updateGui();
			sleep(speed);
			winLossValue = 0;
		}
		System.out.println("Stop");
		System.exit(0);
	}
	
	/**
	 * gameloop for prey tests
	 * @param something
	 */
	private int gameLoop(boolean something) {
		// 0 = running, 1 = thieves win, 2 = thieves loss
		int winLossValue = 0;
		while (winLossValue == 0 ) {
			firstAgentAction();
			for (Agent f : spriteManager.getAgentList()) {
				if (f instanceof Thief) {
					if (((Thief) f).atGoal()) {
						winLossValue = 1;
					}
				}
				else if (f instanceof Guard)
				{
					if (((Guard)f).hasCaught())
						winLossValue = 2;
				}
			}
			if (winLossValue == 1) {
				System.out.println("VERY SNEAKY THIEF!");
				return winLossValue; 
			}
			if (winLossValue == 2) {
				System.out.println("NOT A SNEAKY THIEF!");
				return winLossValue; 
			}
			/* finish the move by updateting the gui */
			winLossValue = 0;
		}
		System.out.println("Stop");
		return winLossValue; 
	}

	/**
	 * This puts the current thread into sleep
	 * 
	 * @param time
	 *            the time that the thread has to sleep in seconds
	 */
	private void sleep(double time) {
		time *= 1000;
		try {
			Thread.sleep((long) time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function checks if a move is possible TODO: check diagonal movement.
	 * this check should be done both on the map and against the list of agents
	 * 
	 * @param agent
	 *            The agent who want to do the move
	 * @param move
	 *            the move the agent wants to perform
	 * @return true if the move is possible else false
	 */
	private boolean isMovePossible(Agent agent, Move move) {

		Coordinate coordinate = agent.getCoordinates().clone();
		MoveDirection dir = MoveDirection
				.getDirectionFromAngle(coordinate.angle);
		coordinate.x += dir.getDx();
		coordinate.y += dir.getDy();
		GridState[][] tempMap = map.getCopyOfMap();
		if (coordinate.x < 0 && coordinate.y < 0)
			return false;
		if (!(coordinate.x < tempMap.length && coordinate.y < tempMap[0].length))
			return true;
		if (tempMap[coordinate.x][coordinate.y].moveable()) {
			for (Agent tempAgent : spriteManager.getAgentList()) {
				Coordinate coords = tempAgent.getCoordinates();
				if ((coords.x == coordinate.x && coords.y == coordinate.y))
					return false; // if there is an agent in the way
			}
			return true; // if there is nothing in the way
		}
		return false; // if the map spot is already occupied by an object
	}

	/**
	 * Get the action list of the agent who spend the least duration and execute
	 * the list
	 */
	public void firstAgentAction() {
		Agent agent = spriteManager.getFirstAgent();

		/* trigger the wait event to send an update of the vision to the agent */
		eventManager.triggerEvent(new Wait(0), agent);

		Action agentAction = agent.getAction();

		for (ActionElement actionElement : agentAction.getActionElements()) {

			/*
			 * Find a way of using polymorphism to do this instead of a switch
			 */

			/* this handles the turn actionElement */
			if (actionElement instanceof Turn) {
				// System.err.println("Current angle "+
				// agent.getCoordinates().angle + "add " + ((Turn)
				// actionElement).getAngle());
				agent.getCoordinates().addToAngle(
						((Turn) actionElement).getAngle());
				// System.err.println("result "+ agent.getCoordinates().angle);
				agent.addTimeToKey(actionElement.duration());
				eventManager.triggerEvent(actionElement, agent);
			}

			/*
			 * this handles the move actionElement (THIS NEEDS TO BE
			 * IMPLEMENTED)
			 */
			if (actionElement instanceof Move) {
				if (!(agent instanceof Guard) || !((Guard) agent).isInTower()) {
					Move move = (Move) actionElement;
					Coordinate coordinate;
					coordinate = agent.getCoordinates().clone();
					MoveDirection dir = MoveDirection
							.getDirectionFromAngle(coordinate.angle);
					coordinate.x += dir.getDx();
					coordinate.y += dir.getDy();
					if (isMovePossible(agent, move)) {
						coordinate = agent.getCoordinates();
						dir = MoveDirection
								.getDirectionFromAngle(coordinate.angle);
						coordinate.x += dir.getDx();
						coordinate.y += dir.getDy();
						double time = actionElement.duration();
						if (agent instanceof Thief)
							time *= map.getCopyOfMap()[coordinate.x][coordinate.y]
									.getThiefCost();
						else
							time *= map.getCopyOfMap()[coordinate.x][coordinate.y]
									.getCost();
						agent.addTimeToKey(time);
						// Always trigger events AFTER EXECUTION OF THE
						// ACTIONELEMENT
						eventManager.triggerEvent(actionElement, agent);
					} else {
						if (agent.getBeliefMap().getMap()[coordinate.x][coordinate.y] == GridState.unknown)
							agent.getBeliefMap().getMap()[coordinate.x][coordinate.y] = GridState.unknownOBJECT;
						// if a agent trys to go somewhere where it doesnt
						// belong give him a time penalty
						// this avoids that the simulator crashes
						agent.addTimeToKey(0.1);
					}

				}
			}

			/* handle the wait ActionElement */
			if (actionElement instanceof Wait) {
				if (agent instanceof Guard)
					if (map.getCopyOfMap()[agent.getCoordinates().x][agent
							.getCoordinates().y] == GridState.Sentry)
						if (actionElement.duration() > 3.0)
							if (((Guard) agent).isInTower())
								((Guard) agent).leaveTower();
							else
								((Guard) agent).enterTower();

				agent.addTimeToKey(actionElement.duration());

				eventManager.triggerEvent(actionElement, agent);
			}

		}

		/* update the duration witch was spend to execute the agentAction */
		;

		/* put the agent back into the queue with a new duration */
		spriteManager.addAgent(agent);

		// System.err.println(agent.getCoordinates().toString());
	}

	public static void main(String[] args) {
		new Simulator();
	}

	public static Map map;
	private SpriteManager spriteManager;
	private EventManager eventManager;
	private MainFrame mainFrame;
}
