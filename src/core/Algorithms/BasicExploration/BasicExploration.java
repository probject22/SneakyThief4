/**
 * 
 */
package core.Algorithms.BasicExploration;

import core.BeliefMap;
import core.DebugConstants;
import core.Map;
import core.Algorithms.Exploration;
import core.actions.Action;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class BasicExploration implements Exploration {
	private boolean debug = DebugConstants.basicExplorationDebug;
	private Map beliefMap;
	
	/**
	 * 
	 */
	public BasicExploration() {
	}
	
	public BasicExploration(BeliefMap beliefMap) {
		this();
		setBeliefMap(beliefMap);
	}

	/** 
	 * @see core.Algorithms.Exploration#setBeliefMap(core.BeliefMap)
	 */
	@Override
	public void setBeliefMap(BeliefMap beliefMap) {
		this.beliefMap = beliefMap;

	}

	/**
	 * @see core.Algorithms.Exploration#getAction()
	 */
	@Override
	public Action getAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
