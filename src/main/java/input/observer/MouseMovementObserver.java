package input.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input.command.Command;
import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public class MouseMovementObserver extends BasicObserver {

	private Map<MouseCheck, Command> commandMap;
	private List<MouseCheck> ordering;

	public MouseMovementObserver() {
		super();
		commandMap = new HashMap<>();
		ordering = new ArrayList<>();
	}

	@Override
	public void handleEvent(InputTypes type, Keys input, Actions action, float[] data) {
		if (type == InputTypes.MOUSE_MOVEMENT) {
			for (MouseCheck check : ordering) {
				if (check.checkMouse()) {
					commandMap.get(check).execute();
					return;
				}
			}
		}
		notifyObservers(type, input, action, data);
	}

	public void addCheck(MouseCheck mouseCheck, Command command) {
		commandMap.put(mouseCheck, command);
		ordering.add(mouseCheck);
	}

}
