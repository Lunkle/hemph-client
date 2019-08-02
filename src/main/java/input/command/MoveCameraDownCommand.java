package input.command;

import graphics.rendering.Camera;

public class MoveCameraDownCommand extends Command {

	public MoveCameraDownCommand() {
		commandID = "moveDown";
	}

	@Override
	public void onPress() {
		state.getCamera().addDirection(Camera.Directions.DOWN);
	}

	@Override
	public void onRelease() {
		state.getCamera().addDirection(Camera.Directions.UP);
	}

}
