package game;

import org.lwjgl.glfw.GLFW;

import input.key.KeyHandler;
import input.key.MainMenuKeyHandler;
import input.misc.ResizeHandler;

public class Input {

	public Input() {
		GLFW.glfwSetKeyCallback(Visual.getWindowID(), new MainMenuKeyHandler());
		GLFW.glfwSetFramebufferSizeCallback(Visual.getWindowID(), new ResizeHandler());
	}

	public Input(KeyHandler keyHandler) {
		GLFW.glfwSetKeyCallback(Visual.getWindowID(), keyHandler);
	}

	public void handle() {
		GLFW.glfwPollEvents();
	}

}
