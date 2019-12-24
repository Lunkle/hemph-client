package input.observer;

import input.event.InputEvent;

public abstract class InputObserver {

	/**
	 * The name of the observer. Optional to set.
	 */
	private String name;

	private InputObserver nextObserver;

	/**
	 * Whether or not this observer "consumes" an event. If it does then the event
	 * is not passed down to further observers.
	 */
	private boolean consumes = false;

	public abstract boolean handleEvent(InputEvent event);

	public void notifyObservers(InputEvent event) {
//		System.out.println(getNextObserver());
		if (event.isAlive() && getNextObserver() != null) {
			boolean passesCheck = getNextObserver().handleEvent(event);
//			System.out.println(this + " handling event: " + event);
			if (passesCheck && nextObserver.doesConsume()) {
//				System.out.println("Consumed input");
				return;
			}
//			System.out.println("Passing to next observer");
			nextObserver.notifyObservers(event);
		}
	}

	/**
	 * Add an observer to the chain of observers at location specified by index. If
	 * index is greater than the number of observers then it will be appended to the
	 * end. Takes O(n) time, where n is the index given.
	 * 
	 * @param newObserver the new observer
	 * @param index       the index at which the observer is to be placed
	 */
	public void addObserver(InputObserver newObserver, int index) {
		if (index == 0 || getNextObserver() == null) {
			newObserver.setNextObserver(nextObserver);
			this.setNextObserver(newObserver);
		} else {
			getNextObserver().addObserver(newObserver, index - 1);
		}
	}

	/**
	 * Remove an observer from the chain of observers if it exists. Takes O(n) time,
	 * where n is the length of the chain. Note that an observer cannot remove
	 * itself.
	 * 
	 * @param removeObserver the observer to be removed
	 */
	public void removeObserver(InputObserver removeObserver) {
		if (getNextObserver() != null) {
			if (getNextObserver().equals(removeObserver)) {
				setNextObserver(getNextObserver().getNextObserver());
			} else {
				getNextObserver().removeObserver(removeObserver);
			}
		}
	}

	public void setConsumes(boolean consumes) {
		this.consumes = consumes;
	}

	public boolean doesConsume() {
		return consumes;
	}

	public InputObserver getNextObserver() {
		return nextObserver;
	}

	public void setNextObserver(InputObserver observer) {
		nextObserver = observer;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		if (name == null) {
			return "Unnamed Input Observer";
		}
		return name;
	}

}
