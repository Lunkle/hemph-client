package input.command;

import graphics.Camera;

public class MoveCameraRightCommand extends Command {

	public MoveCameraRightCommand() {
		commandID = "moveRight";
	}

	@Override
	public void onPress() {
		state.getCamera().addDirection(Camera.Directions.RIGHT);
	}

	@Override
	public void onRelease() {
		state.getCamera().addDirection(Camera.Directions.LEFT);
	}

}
