package input.event;

import input.information.EventTypes;

public abstract class InputEvent {

	private boolean isAlive = true;

	public abstract EventTypes getEventType();

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

}
