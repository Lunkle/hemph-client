package input.callback;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import input.event.InputEvent;
import input.event.MouseButtonEvent;
import input.information.Action;
import input.information.Key;
import input.observer.InputObservee;
import input.observer.InputObserver;

public class MouseButtonCallback extends GLFWMouseButtonCallback implements InputObservee {

	private InputObserver nextObserver;
	private InputObserver lastObserver;

	@Override
	public void invoke(long window, int button, int action, int mods) {
		Key buttonIdentification = Key.getKey(button);
		Action actionIdentification = Action.getAction(action);
		InputEvent event = new MouseButtonEvent(buttonIdentification, actionIdentification);
		notifyObservers(event);
	}

	@Override
	public void notifyObservers(InputEvent event) {
		if (nextObserver != null) {
			nextObserver.handleEvent(event);
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
