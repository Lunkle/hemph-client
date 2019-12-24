package logics.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.Visual;
import graphics.gui.GUI;
import graphics.gui.GUIBuilder;
import graphics.light.DirectionalLight;
import graphics.light.PointLight;
import graphics.light.SpotLight;
import graphics.rendering.Camera;
import graphics.transformation.ProjectionWrapper;
import graphics.vao.VAO;
import input.Input;
import input.mouse.Mouse;
import input.observer.KeyObserver;
import input.observer.MouseButtonObserver;
import input.observer.MouseMovementObserver;
import input.observer.MouseScrollObserver;
import loading.loader.GraphicsDataConnecter;
import loading.loader.ResourceLoaderThread;
import logics.octree.GameEntity;
import logics.octree.Octree;
import logics.octree.RoomEntity;

public abstract class GameState {

	private GameStateWrapper gameStateWrapper;

	private Camera camera;
	private List<GUI> guis;

	private List<DirectionalLight> directionalLights;
	private List<PointLight> pointLights;
	private List<SpotLight> spotLights;

	private Octree octree;
	private Map<VAO, List<GameEntity>> roomMeshEntityMap;

	private List<KeyObserver> toAddKeyObservers;
	private List<MouseMovementObserver> toAddMouseMovementObservers;
	private List<MouseButtonObserver> toAddMouseButtonObservers;
	private List<MouseScrollObserver> toAddMouseScrollObservers;

	private List<KeyObserver> toRemoveKeyObservers;
	private List<MouseMovementObserver> toRemoveMouseMovementObservers;
	private List<MouseButtonObserver> toRemoveMouseButtonObservers;
	private List<MouseScrollObserver> toRemoveMouseScrollObservers;

	private boolean clearObservers = false;

	public GameState() {
		camera = new Camera();
		guis = new ArrayList<>();
		directionalLights = new ArrayList<>();
		pointLights = new ArrayList<>();
		spotLights = new ArrayList<>();
		toAddKeyObservers = new ArrayList<>();
		toAddMouseMovementObservers = new ArrayList<>();
		toAddMouseButtonObservers = new ArrayList<>();
		toAddMouseScrollObservers = new ArrayList<>();
		toRemoveKeyObservers = new ArrayList<>();
		toRemoveMouseMovementObservers = new ArrayList<>();
		toRemoveMouseButtonObservers = new ArrayList<>();
		toRemoveMouseScrollObservers = new ArrayList<>();
		octree = new Octree();
		roomMeshEntityMap = new HashMap<>();
	}

	public void setGameStateWrapper(GameStateWrapper gameStateWrapper) {
		this.gameStateWrapper = gameStateWrapper;
	}

	public Mouse getMouse() {
		return gameStateWrapper.getMouse();
	}

	public Camera getCamera() {
		return camera;
	}

	public List<GUI> getGuis() {
		return guis;
	}

	public Visual getVisuals() {
		return gameStateWrapper.getVisuals();
	}

	public ResourceLoaderThread getLoaderThread() {
		return gameStateWrapper.getLoaderThread();
	}

	public GUIBuilder getGuiBuilder() {
		return gameStateWrapper.getGuiBuilder();
	}

	public GraphicsDataConnecter getConnecter() {
		return gameStateWrapper.getConnecter();
	}

	public ProjectionWrapper getProjectionWrapper() {
		return getVisuals().getProjectionWrapper();
	}

	protected void addGUI(GUI gui) {
		guis.add(gui);
		if (gui.getOnPress() != null) {
			addMouseButtonObserver(gui.getOnPress());
		}
		if (gui.getOnRelease() != null) {
			addMouseButtonObserver(gui.getOnRelease());
		}
		if (gui.getOnHover() != null) {
			addMouseMovementObserver(gui.getOnHover());
		}
	}

	public List<DirectionalLight> getDirectionalLights() {
		return directionalLights;
	}

	public List<PointLight> getPointLights() {
		return pointLights;
	}

	public List<SpotLight> getSpotLights() {
		return spotLights;
	}

	protected void addLight(DirectionalLight light) {
		directionalLights.add(light);
	}

	protected void addLight(PointLight light) {
		pointLights.add(light);
	}

	protected void addLight(SpotLight light) {
		spotLights.add(light);
	}

	public void addGameEntity(GameEntity gameEntity) {
//		octree.insert(gameEntity);
		// TODO
		VAO mesh = gameEntity.getModel().getMesh();
		if (roomMeshEntityMap.containsKey(mesh)) {
			roomMeshEntityMap.get(mesh).add(gameEntity);
		} else {
			List<GameEntity> entityList = new ArrayList<>();
			entityList.add(gameEntity);
			roomMeshEntityMap.put(mesh, entityList);
		}
	}

	public void addGameEntity(RoomEntity room) {
		VAO mesh = room.getModel().getMesh();
		if (roomMeshEntityMap.containsKey(mesh)) {
			roomMeshEntityMap.get(mesh).add(room);
		} else {
			List<GameEntity> entityList = new ArrayList<>();
			entityList.add(room);
			roomMeshEntityMap.put(mesh, entityList);
		}
	}

	public void removeGameEntity(RoomEntity room) {
		VAO mesh = room.getModel().getMesh();
		if (roomMeshEntityMap.containsKey(mesh)) {
			roomMeshEntityMap.get(mesh).remove(room);
		}
	}

	public Map<VAO, List<GameEntity>> getMeshLists() {
		Map<VAO, List<GameEntity>> meshesMap = new HashMap<>();
		for (VAO mesh : roomMeshEntityMap.keySet()) {
			meshesMap.put(mesh, roomMeshEntityMap.get(mesh));
		}
		return meshesMap;
	}

	public Map<VAO, List<GameEntity>> getRoomMeshes() {
		return roomMeshEntityMap;
	}

	public void addKeyObserver(KeyObserver keyObserver) {
		synchronized (toAddKeyObservers) {
			toAddKeyObservers.add(keyObserver);
		}
	}

	public void addMouseMovementObserver(MouseMovementObserver mouseMovementObserver) {
		synchronized (toAddMouseMovementObservers) {
			toAddMouseMovementObservers.add(mouseMovementObserver);
		}
	}

	public void addMouseButtonObserver(MouseButtonObserver mouseButtonObserver) {
		synchronized (toAddMouseButtonObservers) {
			toAddMouseButtonObservers.add(mouseButtonObserver);
		}
	}

	public void addMouseScrollObserver(MouseScrollObserver mouseScrollObserver) {
		synchronized (toAddMouseScrollObservers) {
			toAddMouseScrollObservers.add(mouseScrollObserver);
		}
	}

	public void removeKeyObserver(KeyObserver keyObserver) {
		synchronized (toRemoveKeyObservers) {
			toRemoveKeyObservers.add(keyObserver);
		}
	}

	public void removeMouseMovementObserver(MouseMovementObserver mouseMovementObserver) {
		synchronized (toRemoveMouseMovementObservers) {
			toRemoveMouseMovementObservers.add(mouseMovementObserver);
		}
	}

	public void removeMouseButtonObserver(MouseButtonObserver mouseButtonObserver) {
		synchronized (toRemoveMouseButtonObservers) {
			toRemoveMouseButtonObservers.add(mouseButtonObserver);
		}
	}

	public void removeMouseScrollObserver(MouseScrollObserver mouseScrollObserver) {
		synchronized (toRemoveMouseScrollObservers) {
			toRemoveMouseScrollObservers.add(mouseScrollObserver);
		}
	}

	protected void setFlagToClearObservers() {
		clearObservers = true;
	}

	/**
	 * 
	 * Takes in an input handler to be updated. Any new input observers will be
	 * added to the input handler accordingly.
	 * 
	 * @param the input handler to be updated
	 */
	public void updateInputObservers(Input inputs) {
		if (clearObservers) {
			inputs.clearAllCallbackObservers();
			clearObservers = false;
		}
		addObservers(inputs);
		removeObservers(inputs);
	}

	private void addObservers(Input inputs) {
		if (toAddKeyObservers.size() > 0) {
			List<KeyObserver> copyKeyObservers = toAddKeyObservers;
			synchronized (toAddKeyObservers) {
				toAddKeyObservers = new ArrayList<>();
			}
			for (KeyObserver copyObserver : copyKeyObservers) {
				inputs.appendKeyCallbackObserver(copyObserver);
			}
		}
		if (toAddMouseMovementObservers.size() > 0) {
			List<MouseMovementObserver> copyMouseMovementObservers = toAddMouseMovementObservers;
			synchronized (toAddMouseMovementObservers) {
				toAddMouseMovementObservers = new ArrayList<>();
			}
			for (MouseMovementObserver copyObserver : copyMouseMovementObservers) {
				inputs.appendMouseMovementCallbackObserver(copyObserver);
			}
		}
		if (toAddMouseButtonObservers.size() > 0) {
			List<MouseButtonObserver> copyMouseButtonObservers = toAddMouseButtonObservers;
			synchronized (toAddMouseButtonObservers) {
				toAddMouseButtonObservers = new ArrayList<>();
			}
			for (MouseButtonObserver copyObserver : copyMouseButtonObservers) {
				inputs.appendMouseButtonCallbackObserver(copyObserver);
			}
		}
		if (toAddMouseScrollObservers.size() > 0) {
			List<MouseScrollObserver> copyMouseScrollObservers = toAddMouseScrollObservers;
			synchronized (toAddMouseScrollObservers) {
				toAddMouseScrollObservers = new ArrayList<>();
			}
			for (MouseScrollObserver copyObserver : copyMouseScrollObservers) {
				inputs.appendMouseScrollCallbackObserver(copyObserver);
			}
		}
	}

	private void removeObservers(Input inputs) {
		if (toRemoveKeyObservers.size() > 0) {
			List<KeyObserver> copyKeyObservers = toRemoveKeyObservers;
			synchronized (toRemoveKeyObservers) {
				toRemoveKeyObservers = new ArrayList<>();
			}
			for (KeyObserver copyObserver : copyKeyObservers) {
				inputs.removeKeyCallbackObserver(copyObserver);
			}
		}
		if (toRemoveMouseMovementObservers.size() > 0) {
			List<MouseMovementObserver> copyMouseMovementObservers = toRemoveMouseMovementObservers;
			synchronized (toRemoveMouseMovementObservers) {
				toRemoveMouseMovementObservers = new ArrayList<>();
			}
			for (MouseMovementObserver copyObserver : copyMouseMovementObservers) {
				inputs.removeMouseMovementCallbackObserver(copyObserver);
			}
		}
		if (toRemoveMouseButtonObservers.size() > 0) {
			List<MouseButtonObserver> copyMouseButtonObservers = toRemoveMouseButtonObservers;
			synchronized (toRemoveMouseButtonObservers) {
				toRemoveMouseButtonObservers = new ArrayList<>();
			}
			for (MouseButtonObserver copyObserver : copyMouseButtonObservers) {
				inputs.removeMouseButtonCallbackObserver(copyObserver);
			}
		}
		if (toRemoveMouseScrollObservers.size() > 0) {
			List<MouseScrollObserver> copyMouseScrollObservers = toRemoveMouseScrollObservers;
			synchronized (toRemoveMouseScrollObservers) {
				toRemoveMouseScrollObservers = new ArrayList<>();
			}
			for (MouseScrollObserver copyObserver : copyMouseScrollObservers) {
				inputs.removeMouseScrollCallbackObserver(copyObserver);
			}
		}
	}

	public final void transition(GameState state) {
		gameStateWrapper.setState(state);
		state.setGameStateWrapper(gameStateWrapper);
		state.initialize();
	}

	public abstract void updateWindowDimensions(int windowWidth, int windowHeight);

	/**
	 * Will be called once upon transitioning
	 */
	protected abstract void initialize();

	/**
	 * Will be called continually by the logics thread.
	 */
	public abstract void update();

}
