package input.command;

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
		state.getCamera().addDirection(Camera.Directions.BACK);
	}

}
