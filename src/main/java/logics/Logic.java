package logics;

import org.lwjgl.glfw.GLFW;

import input.Input;
import logics.state.GameState;

public class Logic extends Thread {

	private double updatesPerSecond = 30.0;
	private double targetUpdateTime = 1.0 / updatesPerSecond;
	private double previousUpdateDuration = 0.0;
	private double previousUpdateEndTime = 0.0;
	private double accumulator = 0.0;
	private int updateCounter = 1;

	private GameState state;
	private Input inputs;

	private boolean isDone = false;

	public Logic(GameState state, Input inputs) {
		this.state = state;
		this.inputs = inputs;
	}

	@Override
	public void run() {
		while (!isDone) {
			previousUpdateDuration = GLFW.glfwGetTime() - previousUpdateEndTime;
			accumulator += previousUpdateDuration;
			double averageUpdateDuration = previousUpdateEndTime / updateCounter;
			int numUpdates = (int) ((accumulator + averageUpdateDuration) / targetUpdateTime);
			for (int i = 0; i < numUpdates; i++) {
				state.updateInputObservers(inputs);
				state = state.update();
				accumulator -= targetUpdateTime;
			}
			updateCounter += numUpdates;
			previousUpdateEndTime += previousUpdateDuration;
		}
		cleanUp();
	}

	public void cleanUp() {}

	public GameState getGameState() {
		return state;
	}

	public double getAlpha() {
		return accumulator / targetUpdateTime;
	}

	public void end() {
		isDone = true;
		synchronized (this) {
			notify();
		}
	}

}
