package input.observer;

import java.util.ArrayList;
import java.util.List;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;
import input.mouse.Component;
import input.mouse.Mouse;

public class MouseObserver implements InputObserver, InputObservee {

	private InputObserverNode node;
	private Mouse mouse;
	private List<Component> components;

	public MouseObserver() {
		node = getEmptyObserverNode();
		components = new ArrayList<>();
	}

	@Override
	public void handleEvent(InputTypes type, Keys input, Actions action, float[] data) {
		if (type == InputTypes.KEY || type == InputTypes.UNKNOWN) {

		}
		switch (type) {
			case MOUSE_BUTTON:

				break;
			case MOUSE_MOVEMENT:
				break;
			case MOUSE_SCROLL:
				break;
			default:
				notifyObservers(type, input, action, data);
				break;
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
