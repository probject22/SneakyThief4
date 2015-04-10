package core.events;

/**
 * The basic event message
 * Created by Stan on 08/04/15.
 */
public class Event {
	public Event(double timeOccurt){
		this.timeOccurt = timeOccurt;
	}
	public double getTimeOccurt(){
		return this.timeOccurt;
	}
	private double timeOccurt;
}
