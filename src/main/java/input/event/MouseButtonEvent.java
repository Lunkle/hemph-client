package input.event;

import input.information.Action;
import input.information.EventTypes;
import input.information.Key;

public class MouseButtonEvent implements InputEvent {

	private Key button;
	private Action action;

	public MouseButtonEvent(Key button, Action action) {
		this.button = button;
		this.action = action;
	}

	public Key getButton() {
		return button;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public EventTypes getEventType() {
		return EventTypes.MOUSE_BUTTON;
	}

}
