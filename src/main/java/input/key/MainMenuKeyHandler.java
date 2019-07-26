package input.key;

import org.lwjgl.glfw.GLFW;

public class MainMenuKeyHandler extends KeyHandler {

	@Override
	public void setKeyCommands() {
		addKeyCommand(GLFW.GLFW_KEY_ESCAPE, "closeWindow");
		addKeyCommand(GLFW.GLFW_KEY_W, "moveForward");
	}

}
