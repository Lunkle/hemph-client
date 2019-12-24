package input.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input.command.Command;
import input.event.InputEvent;
import input.event.MouseButtonEvent;
import input.information.Action;
import input.information.Key;

public class MouseButtonObserver extends InputObserver {

	private Map<Key, Map<Action, Map<MouseCheck, Command>>> commandMap;
	private Map<Key, Map<Action, List<MouseCheck>>> ordering;

	public MouseButtonObserver() {
		super();
		commandMap = new HashMap<>();
		commandMap.put(Key.MOUSE_LEFT, new HashMap<>());
		commandMap.get(Key.MOUSE_LEFT).put(Action.PRESS, new HashMap<>());
		commandMap.get(Key.MOUSE_LEFT).put(Action.RELEASE, new HashMap<>());
		commandMap.put(Key.MOUSE_RIGHT, new HashMap<>());
		commandMap.get(Key.MOUSE_RIGHT).put(Action.PRESS, new HashMap<>());
		commandMap.get(Key.MOUSE_RIGHT).put(Action.RELEASE, new HashMap<>());
		commandMap.put(Key.MOUSE_MIDDLE, new HashMap<>());
		commandMap.get(Key.MOUSE_MIDDLE).put(Action.PRESS, new HashMap<>());
		commandMap.get(Key.MOUSE_MIDDLE).put(Action.RELEASE, new HashMap<>());

		ordering = new HashMap<>();
		ordering.put(Key.MOUSE_LEFT, new HashMap<>());
		ordering.get(Key.MOUSE_LEFT).put(Action.PRESS, new ArrayList<>());
		ordering.get(Key.MOUSE_LEFT).put(Action.RELEASE, new ArrayList<>());
		ordering.put(Key.MOUSE_RIGHT, new HashMap<>());
		ordering.get(Key.MOUSE_RIGHT).put(Action.PRESS, new ArrayList<>());
		ordering.get(Key.MOUSE_RIGHT).put(Action.RELEASE, new ArrayList<>());
		ordering.put(Key.MOUSE_MIDDLE, new HashMap<>());
		ordering.get(Key.MOUSE_MIDDLE).put(Action.PRESS, new ArrayList<>());
		ordering.get(Key.MOUSE_MIDDLE).put(Action.RELEASE, new ArrayList<>());
	}

	@Override
	public boolean handleEvent(InputEvent event) {
		System.out.println(this);
		if (event instanceof MouseButtonEvent) {
			MouseButtonEvent mouseButtonEvent = (MouseButtonEvent) event;
			Key input = mouseButtonEvent.getButton();
			Action action = mouseButtonEvent.getAction();
			Map<MouseCheck, Command> mouseCheckMap = commandMap.get(input).get(action);
			for (MouseCheck check : ordering.get(input).get(action)) {
				if (check.checkMouse()) {
					mouseCheckMap.get(check).execute();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Adds an observer that listens for the specific mouseKey and action
	 * combination. It performs the mouseCheck, and if it returns true, it executes
	 * the command.
	 * 
	 * @param mouseKey
	 * @param action
	 * @param mouseCheck
	 * @param command
	 */
	public void addCheck(Key mouseKey, Action action, MouseCheck mouseCheck, Command command) {
		boolean mouseKeyCheck = commandMap.containsKey(mouseKey);
		boolean actionCheck = action != Action.UNKNOWN;
		if (mouseKeyCheck && actionCheck) {
			commandMap.get(mouseKey).get(action).put(mouseCheck, command);
			ordering.get(mouseKey).get(action).add(mouseCheck);
		}
	}

}
