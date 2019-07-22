package input.command;

import org.lwjgl.glfw.GLFW;

import game.Visual;
import graphics.Camera;

public class MoveCameraForwardCommand extends Command {

	public MoveCameraForwardCommand() {
		commandID = "moveForward";
	}

	@Override
	public void onPress() {
		state.getCamera().addDirection(Camera.Directions.FRONT);
	}

	@Override
	public void onRelease() {
		GLFW.glfwSetWindowShouldClose(Visual.getWindowID(), true);
	}

}
