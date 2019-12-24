package logics.state;

import graphics.Visual;
import graphics.gui.GUIBuilder;
import input.Input;
import input.mouse.Mouse;
import loading.loader.GraphicsDataConnecter;
import loading.loader.ResourceLoaderThread;

public class GameStateWrapper {

	private GameState state;

	private Visual visuals;
	private Input inputs;

	private ResourceLoaderThread loaderThread;
	private GraphicsDataConnecter connecter;
	private GUIBuilder guiBuilder;
	private Mouse mouse;

	public GameStateWrapper() {
		loaderThread = new ResourceLoaderThread();
		connecter = new GraphicsDataConnecter();
		guiBuilder = new GUIBuilder();
		mouse = new Mouse();
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
		state.setGameStateWrapper(this);
	}

	public Visual getVisuals() {
		return visuals;
	}

	public void setVisuals(Visual visuals) {
		this.visuals = visuals;
	}

	public Input getInput() {
		return inputs;
	}

	public void setInputs(Input inputs) {
		this.inputs = inputs;
	}

	public ResourceLoaderThread getLoaderThread() {
		return loaderThread;
	}

	public GraphicsDataConnecter getConnecter() {
		return connecter;
	}

	public GUIBuilder getGuiBuilder() {
		return guiBuilder;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public void cleanUp() {
		loaderThread.end();
	}
}
