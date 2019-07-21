package game;

import org.lwjgl.Version;

import input.command.CommandLoader;

public class HemphApplication {

	private CommandLoader commandLoader;

	private Input inputs;
	private Logic logics;
	private Visual visuals;

	public void run() {
		System.out.println("LWJGL Version: " + Version.getVersion());
		init();
		loop();
		cleanUp();
	}

	private void init() {
		commandLoader = new CommandLoader();
		visuals = new Visual();
		logics = new Logic();
		inputs = new Input();
		visuals.setGameState(logics.getGameState());
	}

	private void loop() {
		commandLoader.start();
		logics.start();
		while (!visuals.shouldCloseWindow()) {
			inputs.handle();
			visuals.refresh();
		}
	}

	private void cleanUp() {
		logics.end();
		visuals.cleanUp();
	}

	public static void main(String[] args) {
		new HemphApplication().run();
	}

}