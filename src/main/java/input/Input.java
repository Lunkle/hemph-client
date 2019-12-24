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

	KeyObserver keyExitEscapeObserver;
	MouseMovementObserver mousePositionUpdaterObserver;

	private Visual visuals;

	public Input(Mouse mouse, Visual visuals) {
		this.visuals = visuals;
		long windowID = visuals.getWindowID();

		keyCallback = new KeyCallback();
		keyExitEscapeObserver = new KeyEscapeExitObserver(visuals);
		keyCallback.addObserver(keyExitEscapeObserver);

		mouseButtonCallback = new MouseButtonCallback();

		mouseScrollCallback = new MouseScrollCallback();

		mouseMovementCallback = new MouseMovementCallback();
		mousePositionUpdaterObserver = new MouseMovementPositionUpdaterObserver(mouse);
		mouseMovementCallback.addObserver(mousePositionUpdaterObserver);

		GLFW.glfwSetKeyCallback(windowID, keyCallback);
		GLFW.glfwSetMouseButtonCallback(windowID, mouseButtonCallback);
		GLFW.glfwSetScrollCallback(windowID, mouseScrollCallback);
		GLFW.glfwSetCursorPosCallback(windowID, mouseMovementCallback);

		GLFW.glfwSetFramebufferSizeCallback(windowID, new ResizeCallback(visuals));

		hideCursor();
		showCursor();
	}

	public void clearKeyObservers() {
		keyCallback.clearObservers();
		keyCallback.addObserver(keyExitEscapeObserver);
	}

	public void clearMouseMovementObservers() {
		mouseMovementCallback.clearObservers();
		mouseMovementCallback.addObserver(mousePositionUpdaterObserver);
	}

	public void clearMouseButtonObservers() {
		mouseButtonCallback.clearObservers();
	}

	public void clearMouseScrollObservers() {
		mouseScrollCallback.clearObservers();
	}

	public void clearAllCallbackObservers() {
		clearKeyObservers();
		clearMouseMovementObservers();
		clearMouseButtonObservers();
		clearMouseScrollObservers();
	}

	public void addKeyObserver(KeyObserver keyObserver, int index) {
		keyCallback.addObserver(keyObserver, index);
	}

	public void addMouseMovementObserver(MouseMovementObserver mouseMovementObserver, int index) {
		mouseMovementCallback.addObserver(mouseMovementObserver, index);
	}

	public void addMouseButtonObserver(MouseButtonObserver mouseButtonObserver, int index) {
		mouseButtonCallback.addObserver(mouseButtonObserver, index);
	}

	public void addMouseScrollObserver(MouseScrollObserver mouseScrollObserver, int index) {
		mouseScrollCallback.addObserver(mouseScrollObserver, index);
	}

	public void appendKeyObserver(KeyObserver keyObserver) {
		keyCallback.addObserver(keyObserver);
	}

	public void appendMouseMovementObserver(MouseMovementObserver mouseMovementObserver) {
		mouseMovementCallback.addObserver(mouseMovementObserver);
	}

	public void appendMouseButtonObserver(MouseButtonObserver mouseButtonObserver) {
		mouseButtonCallback.addObserver(mouseButtonObserver);
	}

	public void appendMouseScrollObserver(MouseScrollObserver mouseScrollObserver) {
		mouseScrollCallback.addObserver(mouseScrollObserver);
	}

	public void removeKeyObserver(KeyObserver keyObserver) {
		keyCallback.removeObserver(keyObserver);
	}

	public void removeMouseMovementObserver(MouseMovementObserver mouseMovementObserver) {
		mouseMovementCallback.removeObserver(mouseMovementObserver);
	}

	public void removeMouseButtonObserver(MouseButtonObserver mouseButtonObserver) {
		mouseButtonCallback.removeObserver(mouseButtonObserver);
	}

	public void removeMouseScrollObserver(MouseScrollObserver mouseScrollObserver) {
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
