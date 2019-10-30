package input;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import graphics.Visual;
import input.callback.KeyCallback;
import input.callback.MouseButtonCallback;
import input.callback.MouseMovementCallback;
import input.callback.MouseScrollCallback;
import input.misc.ResizeCallback;
import input.mouse.Mouse;
import input.observer.KeyEscapeExitObserver;
import input.observer.KeyObserver;
import input.observer.MouseButtonObserver;
import input.observer.MouseMovementObserver;
import input.observer.MouseMovementPositionUpdaterObserver;
import input.observer.MouseScrollObserver;

public class Input {

	private MouseMovementObserver mouseMovementObserver;
	private MouseButtonObserver mouseButtonObserver;
	private MouseScrollObserver mouseScrollObserver;
	private KeyObserver keyObserver;
	private Visual visuals;

	public Input(Mouse mouse, Visual visuals) {
		this.visuals = visuals;
		long windowID = visuals.getWindowID();

		KeyCallback keyCallback = new KeyCallback();
		keyObserver = new KeyEscapeExitObserver(visuals);
		keyCallback.addObserver(keyObserver);
		GLFW.glfwSetKeyCallback(windowID, keyCallback);

		MouseMovementCallback mouseMovementCallback = new MouseMovementCallback();
		MouseButtonCallback mouseButtonCallback = new MouseButtonCallback();
		MouseScrollCallback mouseScrollCallback = new MouseScrollCallback();
		mouseMovementObserver = new MouseMovementPositionUpdaterObserver(mouse);
		mouseButtonObserver = new MouseButtonObserver();
		mouseScrollObserver = new MouseScrollObserver();
		mouseMovementCallback.addObserver(mouseMovementObserver);
		mouseButtonCallback.addObserver(mouseButtonObserver);
		mouseScrollCallback.addObserver(mouseScrollObserver);
		GLFW.glfwSetCursorPosCallback(windowID, mouseMovementCallback);
		GLFW.glfwSetMouseButtonCallback(windowID, mouseButtonCallback);
		GLFW.glfwSetScrollCallback(windowID, mouseScrollCallback);

		GLFW.glfwSetFramebufferSizeCallback(windowID, new ResizeCallback(visuals));

		hideCursor();
		showCursor();
	}

	public void clearKeyCallbackObservers() {
		keyObserver.clearObservers();
	}

	public void clearMouseMovementCallbackObservers() {
		mouseMovementObserver.clearObservers();
	}

	public void clearMouseButtonCallbackObservers() {
		mouseButtonObserver.clearObservers();
	}

	public void clearMouseScrollCallbackObservers() {
		mouseScrollObserver.clearObservers();
	}

	public void clearAllCallbackObservers() {
		clearKeyCallbackObservers();
		clearMouseMovementCallbackObservers();
		clearMouseButtonCallbackObservers();
		clearMouseScrollCallbackObservers();
	}

	public void addKeyCallbackObservers(List<KeyObserver> keyObservers) {
		for (KeyObserver observer : keyObservers) {
			keyObserver.addObserver(observer);
		}
	}

	public void addMouseMovementCallbackObservers(List<MouseMovementObserver> mouseMovementObservers) {
		for (MouseMovementObserver observer : mouseMovementObservers) {
			mouseMovementObserver.addObserver(observer);
		}
	}

	public void addMouseButtonCallbackObservers(List<MouseButtonObserver> mouseButtonObservers) {
		for (MouseButtonObserver observer : mouseButtonObservers) {
			mouseButtonObserver.addObserver(observer);
		}
	}

	public void addMouseScrollCallbackObservers(List<MouseScrollObserver> mouseScrollObservers) {
		for (MouseScrollObserver observer : mouseScrollObservers) {
			mouseScrollObserver.addObserver(observer);
		}
	}

	public void removeKeyCallbackObservers(List<KeyObserver> keyObservers) {
		for (KeyObserver observer : keyObservers) {
			keyObserver.removeObserver(observer);
		}
	}

	public void removeMouseMovementCallbackObservers(List<MouseMovementObserver> mouseMovementObservers) {
		for (MouseMovementObserver observer : mouseMovementObservers) {
			mouseMovementObserver.removeObserver(observer);
		}
	}

	public void removeMouseButtonCallbackObservers(List<MouseButtonObserver> mouseButtonObservers) {
		for (MouseButtonObserver observer : mouseButtonObservers) {
			mouseButtonObserver.removeObserver(observer);
		}
	}

	public void removeMouseScrollCallbackObservers(List<MouseScrollObserver> mouseScrollObservers) {
		for (MouseScrollObserver observer : mouseScrollObservers) {
			mouseScrollObserver.removeObserver(observer);
		}
	}

	public void handleEvents() {
		GLFW.glfwPollEvents();
	}

	public void hideCursor() {
		GLFW.glfwSetInputMode(visuals.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
	}

	public void showCursor() {
		GLFW.glfwSetInputMode(visuals.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}

}
