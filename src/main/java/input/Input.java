package input;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import graphics.Visual;
import input.callback.KeyCallback;
import input.callback.MouseButtonCallback;
import input.callback.MouseMovementCallback;
import input.callback.MouseScrollCallback;
import input.command.Command;
import input.command.KeyCommand;
import input.information.Keys;
import input.misc.ResizeHandler;
import input.observer.KeyObserver;
import input.observer.MouseObserver;

public class Input {

	KeyCallback keyCallback;
	MouseMovementCallback mouseMovementCallback;
	MouseButtonCallback mouseButtonCallback;
	MouseScrollCallback mouseScrollCallback;

	public Input() {
		long windowID = Visual.getWindowID();

		keyCallback = new KeyCallback();
		mouseMovementCallback = new MouseMovementCallback();
		mouseButtonCallback = new MouseButtonCallback();
		mouseScrollCallback = new MouseScrollCallback();
		GLFW.glfwSetKeyCallback(windowID, keyCallback);
		GLFW.glfwSetCursorPosCallback(windowID, mouseMovementCallback);
		GLFW.glfwSetMouseButtonCallback(windowID, mouseButtonCallback);
		GLFW.glfwSetScrollCallback(windowID, mouseScrollCallback);
		GLFW.glfwSetFramebufferSizeCallback(Visual.getWindowID(), new ResizeHandler());

		hideCursor();
		showCursor();
	}

	public void clearKeyCallbackObservers() {
		keyCallback.clearObservers();
		// Default close window key command should be in effect at all times
		KeyObserver escapeCloseWindow = new KeyObserver();
		Command closeWindow = new Command(() -> GLFW.glfwSetWindowShouldClose(Visual.getWindowID(), true));
		escapeCloseWindow.addCommand(Keys.KEY_ESC, new KeyCommand(new Command(() -> {}), closeWindow));
		keyCallback.addObserver(escapeCloseWindow);
	}

	public void clearMouseCallbackObservers() {
		mouseMovementCallback.clearObservers();
		mouseMovementCallback.clearObservers();
		mouseMovementCallback.clearObservers();
	}

	public void addKeyCallbackObservers(List<KeyObserver> keyObservers) {
		// Add all of the key observers
		for (KeyObserver keyObserver : keyObservers) {
			keyCallback.addObserver(keyObserver);
		}
	}

	public void addMouseCallbackObservers(List<MouseObserver> mouseObservers) {
		// Add all of the mouse observers
		for (MouseObserver mouseObserver : mouseObservers) {
			mouseMovementCallback.addObserver(mouseObserver);
			mouseButtonCallback.addObserver(mouseObserver);
			mouseScrollCallback.addObserver(mouseObserver);
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
