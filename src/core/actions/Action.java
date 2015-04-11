package core.actions;

import java.util.HashSet;
import java.util.Set;

/**
 * Actions are things that an agent performs in one single go. One action consists of
 * multiple ActionElements, but of every ActionElement there can only be one of that
 * type (so for instance only one move per action, one turn per action, etc.).
 *
 *
 * Created by Stan on 08/04/15.
 */
public class Action {
	public Action(){
		actionElements = new HashSet<>();
	}

	/**
	 * Adds an ActionElement to the Action. Checks whether the action element already
	 * contains an element of that type, if so it replaces the previous one with the
	 * new one (so for instance, if there already is a move in the action, that move
	 * is replaced by the new move).
	 * @param action
	 */
	public void addActionElement(ActionElement action){
		//TODO: implement checking duplicate actiontypes
		actionElements.add(action);
	}
	public Set<ActionElement> getActionElements(){
		return actionElements;
	}
    private Set<ActionElement> actionElements;

	/**
	 *
	 * @return the duration of the action
	 */
	public double duration(){
		double totalDuration = 0;
		for (ActionElement actionElement : actionElements) {
			totalDuration += actionElement.duration();
		}
		return totalDuration;
	}

}
