package input.observer;

import java.util.ArrayList;
import java.util.List;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;
import input.mouse.Component;
import input.mouse.Mouse;

public class MouseObserver implements InputObserver, InputObservee {

	private Mouse mouse;

	private InputObserverNode node;
	private List<MouseCheck> checks;
	private List<Component> components;

	public MouseObserver() {
		node = getEmptyObserverNode();
		components = new ArrayList<>();
	}

	@Override
	public void handleEvent(InputTypes type, Keys input, Actions action, float[] data) {
		System.out.println(true);
		if (type == InputTypes.MOUSE_BUTTON || type == InputTypes.UNKNOWN) {
			notifyObservers(type, input, action, data);
			return;
		}
		switch (type) {
			case MOUSE_BUTTON:
				for (MouseCheck check : checks) {
					if (check.checkMouse(data[0], data[1])) {
						break;
					}
				}
				break;
			case MOUSE_MOVEMENT:
				mouse.updatePosition(data[0], data[1]);
				System.out.println(mouse);
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
