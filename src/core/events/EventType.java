/**
 * 
 */
package core.events;

/**
 * I am not sure what this is supposed to do? (Stan) Moves and turns are no event types, they are action
 * elements. furthermore, we have classes and subclasses for both events and actionelements, so why the enum?
 * won't this only lead to confusion?
 *
 *
 * @author ing. Robert Stevens
 * @begin 9 apr. 2015
 * @version 1.0
 * @changes
 * @todo 
 */
public enum EventType {
	Move,
	Turn;

}
