/**
 * 
 */
package core.Algorithms.BasicExploration;

import java.util.ArrayList;
import java.util.Random;

import core.BeliefMap;
import core.DebugConstants;
import core.Map;
import core.Algorithms.Exploration;
import core.actions.Action;
import core.sprite.Sprite;
import dataContainer.Coordinate;
import dataContainer.GridState;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class BasicExploration implements Exploration {
	private boolean debug = DebugConstants.basicExplorationDebug;
	private Map beliefMap;
	private ArrayList<Coordinate> toVisit = new ArrayList<Coordinate>();
	private Sprite sprite;
	private Coordinate goal;
	private Random randomGenerator = new Random();
	private GridState[][] grid;
	/**
	 * 
	 */
	public BasicExploration(Sprite sprite) {
		this.sprite = sprite;
		beliefMap = Map.mapClass;
	}
	
	public BasicExploration(Sprite sprite, Map beliefMap) {
		this(sprite);
		setBeliefMap(beliefMap);
	}

	/** 
	 * @see core.Algorithms.Exploration#setBeliefMap(core.BeliefMap)
	 */
	@Override
	public void setBeliefMap(Map beliefMap) {
		this.beliefMap = beliefMap;
		grid = beliefMap.getCopyOfMap();
		int widht = grid.length;
		int height = grid[0].length;
		toVisit = new ArrayList<Coordinate>();
		for (int i = 0; i < widht; i++)
			for (int j = 0; j < height; j++)
				toVisit.add(new Coordinate(i,j,0));

	}

	/**
	 * @see core.Algorithms.Exploration#getAction()
	 */
	@Override
	public Coordinate getGoal() {
		Coordinate currentCoord = sprite.getCoordinates();
		if (goal != null && currentCoord.equals(goal)){
			goal = null;
		}
		for (int i = 0; i < toVisit.size(); i++){
			Coordinate coord = toVisit.get(i);
		//for (Coordinate coord: toVisit){
			if (currentCoord.equals(coord))
				toVisit.remove(i);
		}
		while (true){
			if (toVisit.size() == 0)
				return null;
			if (goal == null)
				goal = toVisit.get(randomGenerator.nextInt(toVisit.size()));
			if (grid[goal.x][goal.y].moveable()){
				System.err.println(goal);
				return goal;
			}
			else{
				for (int i = 0; i < toVisit.size(); i++){
					if(goal == toVisit.get(i)){
						toVisit.remove(i);
						break;
					}
				}
				goal = null;
			}	
			System.err.println("while true");
		}
	}

}
