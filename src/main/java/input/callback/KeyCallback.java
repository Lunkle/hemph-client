package input.callback;

import org.lwjgl.glfw.GLFWKeyCallback;

import input.event.InputEvent;
import input.event.KeyEvent;
import input.information.Action;
import input.information.Key;
import input.observer.InputObservee;
import input.observer.InputObserver;

public class KeyCallback extends GLFWKeyCallback implements InputObservee {

	private InputObserver nextObserver;
	private InputObserver lastObserver;

	/**
	 * This is the function that is internally called when a key is pressed.
	 */
	@Override
	public final void invoke(long window, int key, int scancode, int action, int mods) {
		Key keyIdentification = Key.getKey(key);
		Action actionIdentification = Action.getAction(action);
		InputEvent keyEvent = new KeyEvent(keyIdentification, actionIdentification);
		if (keyIdentification != Key.UNKNOWN) {
			notifyObservers(keyEvent);
		}
	}

	@Override
	public void notifyObservers(InputEvent event) {
		if (nextObserver != null) {
			boolean passesCheck = nextObserver.handleEvent(event);
			if (passesCheck && nextObserver.doesConsume()) {
				return;
			}
			nextObserver.notifyObservers(event);
		}
	}

	@Override
	public InputObserver getNextObserver() {
		return nextObserver;
	}

	@Override
	public InputObserver getLastObserver() {
		return lastObserver;
	}

	@Override
	public void setNextObserver(InputObserver observer) {
		nextObserver = observer;
	}

	@Override
	public void setLastObserver(InputObserver observer) {
		lastObserver = observer;
	}

}
