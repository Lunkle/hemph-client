package input.observer;

import java.util.HashMap;
import java.util.Map;

import input.command.KeyCommand;
import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public class KeyObserver implements InputObserver, InputObservee {

	private InputObserverNode node;
	private Map<Keys, KeyCommand> commandMap;

	public KeyObserver() {
		node = getEmptyObserverNode();
		commandMap = new HashMap<>();
	}

	public void addCommand(Keys input, KeyCommand command) {
		commandMap.put(input, command);
	}

	public KeyCommand getCommand(Keys input) {
		return commandMap.get(input);
	}

	@Override
	public void handleEvent(InputTypes type, Keys input, Actions action, float[] data) {
		if (type == InputTypes.KEY) {
			KeyCommand command = getCommand(input);
			if (command != null) {
				if (action == Actions.PRESS)
					command.onPress.execute();
				else if (action == Actions.RELEASE)
					command.onRelease.execute();
			}
		} else {
			notifyObservers(type, input, action, data);
		}
	}

	@Override
	public void notifyObservers(InputTypes type, Keys input, Actions action, float[] data) {
		node.handle(type, input, action, data);
	}

	@Override
	public void addObserver(InputObserver newObserver) {
		InputObserverNode newNode = new InputObserverNode(newObserver);
		newNode.setNextNode(node);
		node = newNode;
	}

	@Override
	public void clearObservers() {
		node = getEmptyObserverNode();
	}
}
