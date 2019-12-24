package input.event;

import input.information.EventTypes;

public class MouseScrollEvent extends InputEvent {

	private float offset;

	public MouseScrollEvent(float offset) {
		this.offset = offset;
	}

	public float getOffset() {
		return offset;
	}

	@Override
	public EventTypes getEventType() {
		return EventTypes.MOUSE_SCROLL;
	}

}
