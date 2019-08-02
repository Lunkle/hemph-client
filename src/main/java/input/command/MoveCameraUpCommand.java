package input.command;

import graphics.rendering.Camera;

public class MoveCameraUpCommand extends Command {

	public MoveCameraUpCommand() {
		commandID = "moveUp";
	}

	@Override
	public void onPress() {
		state.getCamera().addDirection(Camera.Directions.UP);
	}

	@Override
	public void onRelease() {
		state.getCamera().addDirection(Camera.Directions.DOWN);
	}

}
