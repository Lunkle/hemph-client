package input.command;

import org.lwjgl.glfw.GLFW;

import game.Visual;

public class CloseWindowCommand extends Command {

	public CloseWindowCommand() {
		commandID = "closeWindow";
	}

	@Override
	public void onPress() {

	}

	@Override
	public void onRelease() {
		GLFW.glfwSetWindowShouldClose(Visual.getWindowID(), true);
	}

}
