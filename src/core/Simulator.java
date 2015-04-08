package core;

import gui.MainFrame;

/**
 * Created by Stan on 08/04/15.
 */
public class Simulator {
	private boolean debug = true;
    
	public Simulator(){
		if (debug) System.err.println("The simulator has been started");
		map = new Map();
		MainFrame mainFrame = new MainFrame();
		mainFrame.setMap(map);
	}
	
    public static void main(String [] args){
    	new Simulator();
    }
    
	private java.util.TreeMap<Double, core.sprite.Agent> Agents;
	private Map map;
}
