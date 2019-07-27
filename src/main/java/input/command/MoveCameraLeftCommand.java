package input.command;

import graphics.Camera;

public class MoveCameraLeftCommand extends Command {

	public MoveCameraLeftCommand() {
		commandID = "moveLeft";
	}

	@Override
	public void onPress() {
		state.getCamera().addDirection(Camera.Directions.LEFT);
	}

	@Override
	public void onRelease() {
		state.getCamera().addDirection(Camera.Directions.RIGHT);
	}

}
