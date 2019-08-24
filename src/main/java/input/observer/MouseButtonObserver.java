package input.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input.command.Command;
import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public class MouseButtonObserver extends BasicObserver {

	private Map<Keys, Map<Actions, Map<MouseCheck, Command>>> commandMap;
	private Map<Keys, Map<Actions, List<MouseCheck>>> ordering;

	public MouseButtonObserver() {
		super();
		commandMap = new HashMap<>();
		commandMap.put(Keys.MOUSE_LEFT, new HashMap<>());
		commandMap.get(Keys.MOUSE_LEFT).put(Actions.PRESS, new HashMap<>());
		commandMap.get(Keys.MOUSE_LEFT).put(Actions.RELEASE, new HashMap<>());
		commandMap.put(Keys.MOUSE_RIGHT, new HashMap<>());
		commandMap.get(Keys.MOUSE_RIGHT).put(Actions.PRESS, new HashMap<>());
		commandMap.get(Keys.MOUSE_RIGHT).put(Actions.RELEASE, new HashMap<>());
		commandMap.put(Keys.MOUSE_MIDDLE, new HashMap<>());
		commandMap.get(Keys.MOUSE_MIDDLE).put(Actions.PRESS, new HashMap<>());
		commandMap.get(Keys.MOUSE_MIDDLE).put(Actions.RELEASE, new HashMap<>());

		ordering = new HashMap<>();
		ordering.put(Keys.MOUSE_LEFT, new HashMap<>());
		ordering.get(Keys.MOUSE_LEFT).put(Actions.PRESS, new ArrayList<>());
		ordering.get(Keys.MOUSE_LEFT).put(Actions.RELEASE, new ArrayList<>());
		ordering.put(Keys.MOUSE_RIGHT, new HashMap<>());
		ordering.get(Keys.MOUSE_RIGHT).put(Actions.PRESS, new ArrayList<>());
		ordering.get(Keys.MOUSE_RIGHT).put(Actions.RELEASE, new ArrayList<>());
		ordering.put(Keys.MOUSE_MIDDLE, new HashMap<>());
		ordering.get(Keys.MOUSE_MIDDLE).put(Actions.PRESS, new ArrayList<>());
		ordering.get(Keys.MOUSE_MIDDLE).put(Actions.RELEASE, new ArrayList<>());
	}

	@Override
	public void handleEvent(InputTypes type, Keys input, Actions action, float[] data) {
		boolean typeCorrect = type == InputTypes.MOUSE_BUTTON;
		boolean inputCorrect = input == Keys.MOUSE_LEFT || input == Keys.MOUSE_MIDDLE || input == Keys.MOUSE_RIGHT;
		boolean actionCorrect = action == Actions.PRESS || action == Actions.RELEASE;
		if (typeCorrect && inputCorrect && actionCorrect) {
			Map<MouseCheck, Command> mouseCheckMap = commandMap.get(input).get(action);
			for (MouseCheck check : ordering.get(input).get(action)) {
				if (check.checkMouse()) {
					mouseCheckMap.get(check).execute();
					return;
				}
			}
		}
		notifyObservers(type, input, action, data);
	}

	public void addCheck(Keys mouseKey, Actions action, MouseCheck mouseCheck, Command command) {
		boolean mouseKeyCheck = commandMap.containsKey(mouseKey);
		boolean actionCheck = action != Actions.UNKNOWN;
		if (mouseKeyCheck && actionCheck) {
			commandMap.get(mouseKey).get(action).put(mouseCheck, command);
			ordering.get(mouseKey).get(action).add(mouseCheck);
		}
	}

}
