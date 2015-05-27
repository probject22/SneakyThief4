/**
 * 
 */
package core.Algorithms;

import core.BeliefMap;
import core.Map;
import core.actions.Action;
import dataContainer.Coordinate;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public interface Exploration {
	Coordinate getGoal();
	void setBeliefMap(Map beliefMap);
}
