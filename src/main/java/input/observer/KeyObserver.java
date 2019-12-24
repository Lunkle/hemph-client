package input.observer;

import java.util.HashMap;
import java.util.Map;

import input.command.KeyCommand;
import input.event.InputEvent;
import input.event.KeyEvent;
import input.information.Action;
import input.information.Key;

public class KeyObserver extends InputObserver {

	private Map<Key, KeyCommand> commandMap;

	public KeyObserver() {
		super();
		commandMap = new HashMap<>();
	}

	public void addCommand(Key input, KeyCommand command) {
		commandMap.put(input, command);
	}

	public KeyCommand getCommand(Key input) {
		return commandMap.get(input);
	}

	@Override
	public boolean handleEvent(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			KeyCommand command = getCommand(keyEvent.getKey());
//			System.out.println(command);
			if (command != null) {
				if (keyEvent.getAction() == Action.PRESS) {
					command.onPress.execute();
				} else if (keyEvent.getAction() == Action.RELEASE) {
					command.onRelease.execute();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String s = "";
		for (Key key : commandMap.keySet()) {
			s += key.toString() + " ";
		}
		return s;
	}

}
