package input.observer;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;
import input.mouse.Mouse;

public class MousePositionUpdaterObserver extends MouseObserver {

	private Mouse mouse;

	public MousePositionUpdaterObserver(Mouse mouse) {
		super();
		this.mouse = mouse;
	}

	@Override
	public void handleEvent(InputTypes type, Keys input, Actions action, float[] data) {
		if (type == InputTypes.MOUSE_MOVEMENT) {
			mouse.updatePosition(data[0], data[1]);
		}
		notifyObservers(type, input, action, data);
	}

}
