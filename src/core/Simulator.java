package core;

import java.util.Map.Entry;

import core.events.EventManager;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import gui.MainFrame;

/**
 * Created by Stan on 08/04/15.
 */
public class Simulator {
	private boolean debug = true;
    
	public Simulator(){
		if (debug) System.err.println("The simulator has been started");
		
		map = new Map();
		
		spriteManager = new SpriteManager(map);
		spriteManager.addAgent(new Agent(new Coordinate(1,1,0.0)));
		spriteManager.addAgent(new Agent(new Coordinate(2,2,0.0)));
		eventManager = new EventManager(spriteManager.getAgentList());
		spriteManager.setEventManager(eventManager);
		/*to get the agent list call spriteManager.getAgentList(); */
		
		/* gui stuff */
		MainFrame mainFrame = new MainFrame();
		mainFrame.setMap(map);
	}
	
    public static void main(String [] args){
    	new Simulator();
    }
    
	private Map map;
	private SpriteManager spriteManager;
	private EventManager eventManager;
}
