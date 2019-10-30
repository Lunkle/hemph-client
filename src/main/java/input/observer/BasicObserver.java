package input.observer;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public class BasicObserver implements InputObserver, InputObservee {

	/**
	 * The node containing this obsesrver.
	 */
	private InputObserverNode node;

	protected BasicObserver() {
		node = InputObserverNode.getEmptyObserverNode();
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
		newNode.setNextNode(node);
		node = newNode;
	}

	@Override
	public void removeObserver(InputObserver removeObserver) {
		if (node.getObserver().equals(removeObserver)) {
			node = node.getNextNode();
		}
		InputObserverNode currentNode = node;
		while (!currentNode.equals(InputObserverNode.getEmptyObserverNode())) {
			if (currentNode.getObserver().equals(removeObserver)) {
				currentNode.setNextNode(currentNode.getNextNode());
				break;
			}
		}
	}

	@Override
	public void clearObservers() {
		node = InputObserverNode.getEmptyObserverNode();
	}
}
