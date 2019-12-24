package input.event;

import input.information.Action;
import input.information.EventTypes;
import input.information.Key;

public class KeyEvent extends InputEvent {

	private Key key;
	private Action action;

	public KeyEvent(Key key, Action action) {
		this.key = key;
		this.action = action;
	}

	public Key getKey() {
		return key;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public EventTypes getEventType() {
		return EventTypes.KEY;
	}

	@Override
	public String toString() {
		return key + " " + action;
	}

}
