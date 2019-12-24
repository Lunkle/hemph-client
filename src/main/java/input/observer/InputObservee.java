package input.observer;

import input.event.InputEvent;

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
	 * Notifying the observers of someting that has happened. Takes O(n) time, where
	 * n is the number of observers of this observee.
	 * 
	 * @param event input event
	 */
	public void notifyObservers(InputEvent event);

	/**
	 * Gets the very next observer of this observee, or null if it has no observers.
	 * 
	 * @return the next observer, or null
	 */
	public InputObserver getNextObserver();

	/**
	 * Gets the very last observer of this observee, or null if it has no observers.
	 * 
	 * @return the next observer, or null
	 */
	public InputObserver getLastObserver();

	/**
	 * Sets the very next observer of this observee to the given observer.
	 * 
	 * @param observer the new observer
	 */
	public void setNextObserver(InputObserver observer);

	/**
	 * Sets the very last observer of this observee to the given observer.
	 * 
	 * @param observer the new observer
	 */
	public void setLastObserver(InputObserver observer);

	/**
	 * Append an observer to this observee's chain of observers. Takes O(1) time.
	 * 
	 * @param newObserver the new observer
	 */
	public default void addObserver(InputObserver newObserver) {
//		System.out.println("Adding " + newObserver.getClass() + ", " + newObserver);
		if (getLastObserver() == null) {
			setNextObserver(newObserver);
			setLastObserver(newObserver);
		} else {
			getLastObserver().setNextObserver(newObserver);
//			System.out.println("Observer after " + getLastObserver() + " is " + getLastObserver().getNextObserver());
			setLastObserver(newObserver);
		}
//		System.out.println("Last Observer: " + getLastObserver());
//		System.out.println("First Observer: " + getNextObserver());
	}

	/**
	 * Add an observer to the observer at the index location down the chain of
	 * observers. Takes O(n) time.
	 * 
	 * @param newObserver the new observer
	 * @param index       the index at which to input it
	 */
	public default void addObserver(InputObserver newObserver, int index) {
		if (index == 0 || getNextObserver() == null) {
			addObserver(newObserver);
		} else {
			getNextObserver().addObserver(newObserver, index - 1);
		}
	}

	/**
	 * Remove a given observer from the chain of observers. Note that an observer
	 * cannot remove itself. Takes O(n) time.
	 * 
	 * @param removeObserver the observer to be removed
	 */
	public default void removeObserver(InputObserver removeObserver) {
		if (getNextObserver() != null) {
			if (getNextObserver().equals(removeObserver)) {
				if (getLastObserver().equals(removeObserver)) {
					setLastObserver(null);
				}
				setNextObserver(getNextObserver().getNextObserver());
			} else {
				getNextObserver().removeObserver(removeObserver);
			}
		}
	}

	/**
	 * Remove all observers. Takes O(1) time.
	 */
	public default void clearObservers() {
		setNextObserver(null);
		setLastObserver(null);
	}
}
