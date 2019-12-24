package input.event;

import input.information.EventTypes;

public class MouseMovementEvent implements InputEvent {

	private float xPos;
	private float yPos;

	public MouseMovementEvent(float xPos, float yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public float getXPos() {
		return xPos;
	}

	public float getYPos() {
		return yPos;
	}

	@Override
	public EventTypes getEventType() {
		return EventTypes.MOUSE_MOVEMENT;
	}

}
