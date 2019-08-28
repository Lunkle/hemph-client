package game;

import org.lwjgl.Version;

import graphics.Visual;
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
	private Mouse mouse;
	private Input inputs;
	private Logic logics;
	private Visual visuals;

	public void run() {
		System.out.println("LWJGL Version: " + Version.getVersion());

//		UnitQuaternion yAxis = new UnitQuaternion(0, 0, 1, 0);
//		UnitQuaternion test = new UnitQuaternion(new Vector3f(0, 1, 0), 90);
//		UnitQuaternion reult = yAxis.multiply(test);
//
//		System.out.println(yAxis);
//		System.out.println(test);
//		System.out.println(reult);

		init();
		loop();
		cleanUp();
	}

	private void init() {
		loaderThread = new ResourceLoaderThread();
		connecter = new GraphicsDataConnecter();
		mouse = new Mouse();
		GameState state = new LoadingScreenState(mouse, loaderThread, connecter);
		visuals = new Visual();
		inputs = new Input(mouse);
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