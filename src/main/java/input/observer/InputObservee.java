package input.observer;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

/**
 * An interface for what an observee should be able to do. An observee is
 * something that can be observed, i.e. it can notify any observers that a
 * certain thing has happened. What the observers do with that information is
 * none of the observee's concern.
 * 
 * @author Donny
 *
 */
public interface InputObservee {

	/**
	 * Notifying the observers of someting that has happened.
	 * 
	 * @param type   The type of input (Example: Mouse)
	 * @param input  The actual input (Example: Left Click)
	 * @param action Specifications of the action on the input (Example: Release)
	 * @param data   Additional data (Example: X-Position, Y-Position)
	 */
	public void notifyObservers(InputTypes type, Keys input, Actions action, float[] data);

	/**
	 * Add an observer to this observee.
	 * 
	 * @param newObserver The new observer
	 */
	public void addObserver(InputObserver newObserver);

	/**
	 * Remove an observer.
	 * 
	 * @param removeObserver The observer to be removed
	 */
	public void removeObserver(InputObserver removeObserver);

	/**
	 * Remove all observers.
	 */
	public void clearObservers();

}
