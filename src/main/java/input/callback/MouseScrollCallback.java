package input.callback;

import org.lwjgl.glfw.GLFWScrollCallback;

import input.event.InputEvent;
import input.event.MouseScrollEvent;
import input.observer.InputObservee;
import input.observer.InputObserver;

public class MouseScrollCallback extends GLFWScrollCallback implements InputObservee {

	private InputObserver nextObserver;
	private InputObserver lastObserver;

	@Override
	public void invoke(long window, double xOffset, double yOffset) {
		InputEvent event = new MouseScrollEvent((float) yOffset);
		notifyObservers(event);
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
