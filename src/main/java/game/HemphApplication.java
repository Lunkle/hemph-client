package game;

import org.lwjgl.Version;

import graphics.Visual;
import input.Input;
import logics.Logic;
import logics.state.GameState;
import logics.state.GameStateWrapper;
import logics.state.LoadingScreenState;

public class HemphApplication {

	private GameStateWrapper stateWrapper;
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
		stateWrapper = new GameStateWrapper();
		GameState state = new LoadingScreenState();
		stateWrapper.setState(state);
		visuals = new Visual(stateWrapper);
		inputs = new Input(stateWrapper.getMouse(), visuals);
		logics = new Logic(stateWrapper, inputs);
		stateWrapper.setVisuals(visuals);
	}

	private void loop() {
		logics.start();
		stateWrapper.getLoaderThread().start();
		while (!visuals.shouldCloseWindow()) {
			stateWrapper.getConnecter().connectResources();
			inputs.handleEvents();
			visuals.refresh();
		}
	}

	private void cleanUp() {
		stateWrapper.cleanUp();
		logics.cleanUp();
		visuals.cleanUp();
	}

	public static void main(String[] args) {
		new HemphApplication().run();
	}

}