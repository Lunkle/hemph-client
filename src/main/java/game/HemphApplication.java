package game;

import org.lwjgl.Version;

import graphics.Visual;
import graphics.loader.GraphicsDataConnecter;
import graphics.loader.ResourceLoaderThread;
import input.Input;
import logics.Logic;
import logics.state.GameState;
import logics.state.LoadingScreenState;

public class HemphApplication {

	private ResourceLoaderThread loaderThread;
	private GraphicsDataConnecter connecter;
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
		loaderThread = new ResourceLoaderThread();
		connecter = new GraphicsDataConnecter();
		GameState state = new LoadingScreenState(loaderThread, connecter);
		visuals = new Visual();
		inputs = new Input();
		logics = new Logic(state, inputs);
	}

	private void loop() {
		logics.start();
		loaderThread.start();
		while (!visuals.shouldCloseWindow()) {
			inputs.handleEvents();
			visuals.refresh(logics.getGameState());
			connecter.connectResources();
		}
	}

	private void cleanUp() {
		loaderThread.end();
		logics.end();
		visuals.cleanUp();
	}

	public static void main(String[] args) {
		new HemphApplication().run();
	}

}