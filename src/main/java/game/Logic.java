package game;

import org.lwjgl.glfw.GLFW;

import logics.GameState;
import logics.HomePageState;

public class Logic extends Thread {

	private double updatesPerSecond = 30.0;
	private double targetUpdateTime = 1.0 / updatesPerSecond;
	private double previousUpdateDuration = 0.0;
	private double previousUpdateEndTime = 0.0;
	private double accumulator = 0.0;
	private int updateCounter = 1;

	private GameState gameState;

	private boolean isDone = false;

	public Logic() {
		gameState = new HomePageState();
	}

	@Override
	public void run() {
		while (!isDone) {
			previousUpdateDuration = GLFW.glfwGetTime() - previousUpdateEndTime;
			accumulator += previousUpdateDuration;
			double averageUpdateDuration = previousUpdateEndTime / updateCounter;
			int numUpdates = (int) ((accumulator + averageUpdateDuration) / targetUpdateTime);
			for (int i = 0; i < numUpdates; i++) {
				gameState.update();
				accumulator -= targetUpdateTime;
			}
			updateCounter += numUpdates;
			previousUpdateEndTime += previousUpdateDuration;
		}
		cleanUp();
	}

	public void cleanUp() {
	}

	public GameState getGameState() {
		return gameState;
	}

	public void end() {
		isDone = true;
	}

	public double getAlpha() {
		return accumulator / targetUpdateTime;
	}

}
