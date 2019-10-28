package game;

import org.lwjgl.Version;

import graphics.Visual;
import graphics.gui.GUIBuilder;
import graphics.loader.GraphicsDataConnecter;
import graphics.loader.ResourceLoaderThread;
import input.Input;
import input.mouse.Mouse;
import logics.Logic;
import logics.state.GameState;
import logics.state.LoadingScreenState;

public class HemphApplication {

	private ResourceLoaderThread loaderThread;
	private GraphicsDataConnecter connecter;
	private GUIBuilder guiBuilder;
	private Mouse mouse;
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
		guiBuilder = new GUIBuilder();
		mouse = new Mouse();
		visuals = new Visual(guiBuilder);
		GameState state = new LoadingScreenState(mouse, loaderThread, connecter, visuals, guiBuilder);
		inputs = new Input(mouse, visuals);
		logics = new Logic(state, inputs);
	}

	private void loop() {
		logics.start();
		loaderThread.start();
		while (!visuals.shouldCloseWindow()) {
			connecter.connectResources();
			inputs.handleEvents();
			visuals.refresh(logics.getGameState());
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