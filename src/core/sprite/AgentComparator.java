/**
 * 
 */
package core.sprite;

import java.util.Comparator;

import core.sprite.Agent;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class AgentComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		Agent a1 = (Agent)o1;
		Agent a2 = (Agent)o2;
		if(a1.getTimeKey() <  a2.getTimeKey()) return -1;
		if(a1.getTimeKey() == a2.getTimeKey()) return 0;
	    return 1;
	}

}
