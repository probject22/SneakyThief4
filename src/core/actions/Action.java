package core.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stan on 08/04/15.
 */
public class Action {
	public Action(){
		actionElements = new ArrayList<ActionElement>();
	}
	
	public void addAction(ActionElement action){
		actionElements.add(action);
	}
    private List<ActionElement> actionElements;

}
