package logics.state;

import graphics.Visual;
import graphics.gui.GUIBuilder;
import graphics.loader.GraphicsDataConnecter;
import graphics.loader.ResourceLoaderThread;
import input.mouse.Mouse;

public class GameStateWrapper {

	private GameState state;

	private Visual visuals;
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

	public void setVisuals(Visual visuals) {
		this.visuals = visuals;
	}

	public void setState(GameState state) {
		this.state = state;
		state.setGameStateWrapper(this);
	}

	public Visual getVisuals() {
		return visuals;
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
