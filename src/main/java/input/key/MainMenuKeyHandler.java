package input.key;

import org.lwjgl.glfw.GLFW;

public class MainMenuKeyHandler extends KeyHandler {

	@Override
	public void setKeyCommands() {
		addKeyCommand(GLFW.GLFW_KEY_ESCAPE, "closeWindow");
		addKeyCommand(GLFW.GLFW_KEY_W, "moveForward");
		addKeyCommand(GLFW.GLFW_KEY_S, "moveBackward");
		addKeyCommand(GLFW.GLFW_KEY_A, "moveLeft");
		addKeyCommand(GLFW.GLFW_KEY_D, "moveRight");
		addKeyCommand(GLFW.GLFW_KEY_E, "moveUp");
		addKeyCommand(GLFW.GLFW_KEY_Q, "moveDown");
	}

}
