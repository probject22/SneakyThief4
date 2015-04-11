package core.events;

/**
 * The basic event message. An event is the principal unit of information handed
 * to the agents to update their beliefs about the world they live in.
 * Created by Stan on 08/04/15.
 */
public class Event {
	public Event(double timeStamp){
		this.timeStamp = timeStamp;
	}
	public double getTimeStamp(){
		return this.timeStamp;
	}

	/**
	 * The duration at which the event is generated
	 */
	private double timeStamp;
}
