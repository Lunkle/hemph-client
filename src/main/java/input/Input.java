package input;

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

	KeyCallback keyCallback;
	MouseMovementCallback mouseMovementCallback;
	MouseButtonCallback mouseButtonCallback;
	MouseScrollCallback mouseScrollCallback;
	private Visual visuals;

	public Input(Mouse mouse, Visual visuals) {
		this.visuals = visuals;
		long windowID = visuals.getWindowID();

		keyCallback = new KeyCallback();
		KeyObserver keyObserver = new KeyEscapeExitObserver(visuals);
		keyCallback.addObserver(keyObserver);
		GLFW.glfwSetKeyCallback(windowID, keyCallback);

		mouseMovementCallback = new MouseMovementCallback();
		mouseButtonCallback = new MouseButtonCallback();
		mouseScrollCallback = new MouseScrollCallback();

		MouseMovementObserver mouseMovementObserver = new MouseMovementPositionUpdaterObserver(mouse);
		mouseMovementCallback.addObserver(mouseMovementObserver);

		GLFW.glfwSetCursorPosCallback(windowID, mouseMovementCallback);
		GLFW.glfwSetMouseButtonCallback(windowID, mouseButtonCallback);
		GLFW.glfwSetScrollCallback(windowID, mouseScrollCallback);

		GLFW.glfwSetFramebufferSizeCallback(windowID, new ResizeCallback(visuals));

		hideCursor();
		showCursor();
	}

	public void clearKeyCallbackObservers() {
		keyCallback.getNextObserver().setNextObserver(null);
	}

	public void clearMouseMovementCallbackObservers() {
		mouseMovementCallback.getNextObserver().setNextObserver(null);
	}

	public void clearMouseButtonCallbackObservers() {
		mouseButtonCallback.clearObservers();
	}

	public void clearMouseScrollCallbackObservers() {
		mouseScrollCallback.clearObservers();
	}

	public void clearAllCallbackObservers() {
		clearKeyCallbackObservers();
		clearMouseMovementCallbackObservers();
		clearMouseButtonCallbackObservers();
		clearMouseScrollCallbackObservers();
	}

	public void addKeyCallbackObserver(KeyObserver keyObserver, int index) {
		keyCallback.addObserver(keyObserver, index);
	}

	public void addMouseMovementCallbackObserver(MouseMovementObserver mouseMovementObserver, int index) {
		mouseMovementCallback.addObserver(mouseMovementObserver, index);
	}

	public void addMouseButtonCallbackObserver(MouseButtonObserver mouseButtonObserver, int index) {
		mouseButtonCallback.addObserver(mouseButtonObserver, index);
	}

	public void addMouseScrollCallbackObserver(MouseScrollObserver mouseScrollObserver, int index) {
		mouseScrollCallback.addObserver(mouseScrollObserver, index);
	}

	public void appendKeyCallbackObserver(KeyObserver keyObserver) {
		keyCallback.addObserver(keyObserver);
	}

	public void appendMouseMovementCallbackObserver(MouseMovementObserver mouseMovementObserver) {
		mouseMovementCallback.addObserver(mouseMovementObserver);
	}

	public void appendMouseButtonCallbackObserver(MouseButtonObserver mouseButtonObserver) {
		mouseButtonCallback.addObserver(mouseButtonObserver);
	}

	public void appendMouseScrollCallbackObserver(MouseScrollObserver mouseScrollObserver) {
		mouseScrollCallback.addObserver(mouseScrollObserver);
	}

	public void removeKeyCallbackObserver(KeyObserver keyObserver) {
		keyCallback.removeObserver(keyObserver);
	}

	public void removeMouseMovementCallbackObserver(MouseMovementObserver mouseMovementObserver) {
		mouseMovementCallback.removeObserver(mouseMovementObserver);
	}

	public void removeMouseButtonCallbackObserver(MouseButtonObserver mouseButtonObserver) {
		mouseButtonCallback.removeObserver(mouseButtonObserver);
	}

	public void removeMouseScrollCallbackObserver(MouseScrollObserver mouseScrollObserver) {
		mouseScrollCallback.removeObserver(mouseScrollObserver);
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
