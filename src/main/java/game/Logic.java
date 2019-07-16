package game;

import org.lwjgl.glfw.GLFW;

import logics.GameState;

public class Logic extends Thread {

	private double updatesPerSecond = 15.0;
	private double targetUpdateTime = 1.0 / updatesPerSecond;
	private double previousUpdateDuration = 0.0;
	private double previousUpdateEndTime = 0.0;
	private double accumulator = 0.0;
	private int updateCounter = 1;

	private GameState state;

	private boolean isDone = false;

	@Override
	public void run() {
		init();
		while (!isDone) {
			previousUpdateDuration = previousUpdateEndTime - GLFW.glfwGetTime();
			accumulator += previousUpdateDuration;
			double averageUpdateDuration = previousUpdateDuration / updateCounter;
			int numUpdates = (int) ((accumulator + averageUpdateDuration) / targetUpdateTime);
			for (int i = 0; i < numUpdates; i++) {
				state.update();
				accumulator -= targetUpdateTime;
			}
			updateCounter += numUpdates;
			previousUpdateEndTime += previousUpdateDuration;
		}
		cleanUp();
	}

	private void init() {
		state = new GameState();
	}

	public void cleanUp() {
	}

	public void end() {
		isDone = true;
	}

	public double getAlpha() {
		return accumulator / targetUpdateTime;
	}

}
