package input.observer;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public class BasicObserver implements InputObserver, InputObservee {

	private InputObserverNode lastNode;
	private InputObserverNode node;

	protected BasicObserver() {
		node = getEmptyObserverNode();
		lastNode = node;
	}

	@Override
	public void handleEvent(InputTypes type, Keys input, Actions action, float[] data) {

	}

	@Override
	public void notifyObservers(InputTypes type, Keys input, Actions action, float[] data) {
		node.handle(type, input, action, data);
	}

	@Override
	public void addObserver(InputObserver newObserver) {
		InputObserverNode newNode = new InputObserverNode(newObserver);
		lastNode.setNextNode(newNode);
		lastNode = newNode;
	}

	@Override
	public void clearObservers() {
		node = getEmptyObserverNode();
		lastNode = node;
	}
}
