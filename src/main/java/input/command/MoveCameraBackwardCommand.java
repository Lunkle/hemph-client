package input.command;

import graphics.Camera;

public class MoveCameraBackwardCommand extends Command {

	public MoveCameraBackwardCommand() {
		commandID = "moveBackward";
	}

	@Override
	public void onPress() {
		state.getCamera().addDirection(Camera.Directions.BACK);
	}

	@Override
	public void onRelease() {
		state.getCamera().addDirection(Camera.Directions.FRONT);
	}

}
