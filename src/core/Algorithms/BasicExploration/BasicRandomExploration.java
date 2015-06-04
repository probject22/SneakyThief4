/**
 * 
 */
package core.Algorithms.BasicExploration;

import core.Map;
import core.sprite.Sprite;
import dataContainer.Coordinate;
import dataContainer.GridState;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class BasicRandomExploration extends BasicExploration {

	/**
	 * @param sprite
	 */
	public BasicRandomExploration(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param sprite
	 * @param beliefMap
	 */
	public BasicRandomExploration(Sprite sprite, Map beliefMap) {
		super(sprite, beliefMap);
		// TODO Auto-generated constructor stub
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
			if (grid[goal.x][goal.y] == GridState.unknown){
				//System.err.println(goal);
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
			//System.err.println("while true");
		}
	}

}
