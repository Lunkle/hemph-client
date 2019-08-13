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
//		System.out.println(objectCounter);
		observer.handleEvent(type, input, action, data);
		if (nextNode != null) {
			nextNode.handle(type, input, action, data);
		}
	}

//	public static void displace(InputObserverNode originalHeadNode, InputObserverNode newHeadNode) {
//		InputObserverNode newNode = new InputObserverNode(newObserver);
//		newHeadNode.setNextNode(originalHeadNode);
//		originalHeadNode = newHeadNode;
//	}

}
