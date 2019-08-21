package input;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import graphics.Visual;
import input.callback.KeyCallback;
import input.callback.MouseButtonCallback;
import input.callback.MouseMovementCallback;
import input.callback.MouseScrollCallback;
import input.misc.ResizeHandler;
import input.mouse.Mouse;
import input.observer.KeyEscapeExitObserver;
import input.observer.KeyObserver;
import input.observer.MouseObserver;
import input.observer.MousePositionUpdaterObserver;

public class Input {

	private Mouse mouse;
	private MouseObserver mouseObserver;
	private KeyObserver keyObserver;

	public Input(Mouse mouse) {
		long windowID = Visual.getWindowID();
		this.mouse = mouse;

		KeyCallback keyCallback = new KeyCallback();
		keyObserver = new KeyEscapeExitObserver();
		keyCallback.addObserver(keyObserver);
		GLFW.glfwSetKeyCallback(windowID, keyCallback);

		MouseMovementCallback mouseMovementCallback = new MouseMovementCallback();
		MouseButtonCallback mouseButtonCallback = new MouseButtonCallback();
		MouseScrollCallback mouseScrollCallback = new MouseScrollCallback();
		mouseObserver = new MousePositionUpdaterObserver(mouse);
		mouseMovementCallback.addObserver(mouseObserver);
		mouseButtonCallback.addObserver(mouseObserver);
		mouseScrollCallback.addObserver(mouseObserver);
		GLFW.glfwSetCursorPosCallback(windowID, mouseMovementCallback);
		GLFW.glfwSetMouseButtonCallback(windowID, mouseButtonCallback);
		GLFW.glfwSetScrollCallback(windowID, mouseScrollCallback);

		GLFW.glfwSetFramebufferSizeCallback(Visual.getWindowID(), new ResizeHandler());

		hideCursor();
		showCursor();
	}

	public void clearKeyCallbackObservers() {
		keyObserver.clearObservers();
	}

	public void clearMouseCallbackObservers() {

		mouseObserver.clearObservers();
	}

	public void addKeyCallbackObservers(List<KeyObserver> keyObservers) {
		// Add all of the key observers
		for (KeyObserver observer : keyObservers) {
			keyObserver.addObserver(observer);
		}
	}

	public void addMouseCallbackObservers(List<MouseObserver> mouseObservers) {
		// Add all of the mouse observers
		for (MouseObserver observer : mouseObservers) {
			mouseObserver.addObserver(observer);
		}
	}

	public void handleEvents() {
		GLFW.glfwPollEvents();
	}

	public void hideCursor() {
		GLFW.glfwSetInputMode(Visual.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
	}

	public void showCursor() {
		GLFW.glfwSetInputMode(Visual.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}

}
