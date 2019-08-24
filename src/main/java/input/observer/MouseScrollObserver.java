package input.observer;

import java.util.HashMap;
import java.util.Map;

import input.command.KeyCommand;
import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public class MouseScrollObserver extends BasicObserver {

	private Map<MouseCheck, KeyCommand> commandMap;

	public MouseScrollObserver() {
		super();
		commandMap = new HashMap<>();
	}

	@Override
	public void handleEvent(InputTypes type, Keys input, Actions action, float[] data) {
		if (!(type == InputTypes.MOUSE_SCROLL)) {
			notifyObservers(type, input, action, data);
			return;
		}
		KeyCommand command = null;
		for (MouseCheck check : commandMap.keySet()) {
			if (check.checkMouse()) {
				command = commandMap.get(check);
				break;
			}
		}
		if (command == null) {
			notifyObservers(type, input, action, data);
			return;
		}
		switch (action) {
			case PRESS:
				command.onPress.execute();
				break;
			case RELEASE:
				command.onRelease.execute();
				break;
			default:
				return;
		}
	}

}
