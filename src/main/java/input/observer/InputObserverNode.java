package input.observer;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public class InputObserverNode {

	private InputObserver observer;
	private InputObserverNode nextNode;

	public InputObserverNode(InputObserver observer) {
		this.observer = observer;
	}

	public InputObserver getObserver() {
		return observer;
	}

	public InputObserverNode getNextNode() {
		return nextNode;
	}

	public void setNextNode(InputObserverNode nextNode) {
		this.nextNode = nextNode;
	}

	public void handle(InputTypes type, Keys input, Actions action, float[] data) {
		observer.handleEvent(type, input, action, data);
		if (nextNode != null) {
			nextNode.handle(type, input, action, data);
		}
	}

	/**
	 * Get the node containing the dumbest observer of all time.
	 * 
	 * @return Said node with dumb observer
	 */
	public static InputObserverNode getEmptyObserverNode() {
		return new EmptyObserverNode();
	}

}
