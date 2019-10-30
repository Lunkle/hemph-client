package input.callback;

import org.lwjgl.glfw.GLFWScrollCallback;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;
import input.observer.InputObservee;
import input.observer.InputObserver;
import input.observer.InputObserverNode;

public class MouseScrollCallback extends GLFWScrollCallback implements InputObservee {

	private InputObserverNode node;

	public MouseScrollCallback() {
		node = InputObserverNode.getEmptyObserverNode();
	}

	@Override
	public void invoke(long window, double xOffset, double yOffset) {
		notifyObservers(InputTypes.MOUSE_SCROLL, Keys.UNKNOWN, Actions.UNKNOWN, new float[] { (float) yOffset });
	}

	@Override
	public void notifyObservers(InputTypes type, Keys input, Actions action, float[] data) {
		if (node != null)
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
