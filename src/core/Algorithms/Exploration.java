/**
 * 
 */
package core.Algorithms;

import core.BeliefMap;
import core.actions.Action;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public interface Exploration {
	void setBeliefMap(BeliefMap beliefMap);
	Action getAction();
}
