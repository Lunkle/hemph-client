package input.observer;

import input.event.InputEvent;
import input.event.MouseMovementEvent;
import input.mouse.Mouse;

public class MouseMovementPositionUpdaterObserver extends MouseMovementObserver {

	private Mouse mouse;

	public MouseMovementPositionUpdaterObserver(Mouse mouse) {
		super();
		this.mouse = mouse;
	}

	@Override
	public boolean handleEvent(InputEvent event) {
		if (event instanceof MouseMovementEvent) {
			MouseMovementEvent mouseMovementEvent = (MouseMovementEvent) event;
			mouse.updatePosition(mouseMovementEvent.getXPos(), mouseMovementEvent.getYPos());
		}
		return true;
	}

}
