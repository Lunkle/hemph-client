package input.callback;

import org.lwjgl.glfw.GLFWKeyCallback;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;
import input.observer.InputObservee;
import input.observer.InputObserver;
import input.observer.InputObserverNode;

public class KeyCallback extends GLFWKeyCallback implements InputObservee {

	private InputObserverNode node;

	public KeyCallback() {
		node = getEmptyObserverNode();
	}

	@Override
	public final void invoke(long window, int key, int scancode, int action, int mods) {
		Keys keyIdentification = Keys.getKey(key);
		Actions actionIdentification = Actions.getAction(action);
		if (keyIdentification != Keys.UNKNOWN) {
			notifyObservers(InputTypes.KEY, keyIdentification, actionIdentification, new float[0]);
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

	public void clearObservers() {
		node = getEmptyObserverNode();
	}

}
