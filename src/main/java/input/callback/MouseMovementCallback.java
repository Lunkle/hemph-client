package input.callback;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import input.event.InputEvent;
import input.event.MouseMovementEvent;
import input.observer.InputObservee;
import input.observer.InputObserver;

public class MouseMovementCallback extends GLFWCursorPosCallback implements InputObservee {

	private InputObserver nextObserver;
	private InputObserver lastObserver;

	@Override
	public void invoke(long window, double xPos, double yPos) {
		InputEvent event = new MouseMovementEvent((float) xPos, (float) yPos);
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
