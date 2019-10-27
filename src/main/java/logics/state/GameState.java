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
import graphics.loader.GraphicsDataConnecter;
import graphics.loader.ResourceLoaderThread;
import graphics.rendering.Camera;
import graphics.transformation.ProjectionTransformation;
import graphics.vao.VAO;
import input.Input;
import input.mouse.Mouse;
import input.observer.KeyObserver;
import input.observer.MouseButtonObserver;
import input.observer.MouseMovementObserver;
import input.observer.MouseScrollObserver;
import logics.octree.GameEntity;
import logics.octree.Octree;
import logics.octree.RoomEntity;

public abstract class GameState {

	protected static ResourceLoaderThread loaderThread;
	protected static GraphicsDataConnecter connecter;
	private static Mouse mouse;
	private static GUIBuilder guiBuilder;

	private Visual visuals;
	private Camera camera;
	private List<GUI> guis;

	private List<DirectionalLight> directionalLights;
	private List<PointLight> pointLights;
	private List<SpotLight> spotLights;

	private Octree octree;
	private Map<VAO, List<GameEntity>> roomMeshEntityMap;

	private List<KeyObserver> keyObservers;
	private List<MouseMovementObserver> mouseMovementObservers;
	private List<MouseButtonObserver> mouseButtonObservers;
	private List<MouseScrollObserver> mouseScrollObservers;
	private boolean clearObservers = false;

	public GameState() {
		camera = new Camera();
		guis = new ArrayList<>();
		directionalLights = new ArrayList<>();
		pointLights = new ArrayList<>();
		spotLights = new ArrayList<>();
		keyObservers = new ArrayList<>();
		mouseMovementObservers = new ArrayList<>();
		mouseButtonObservers = new ArrayList<>();
		mouseScrollObservers = new ArrayList<>();
		octree = new Octree();
		roomMeshEntityMap = new HashMap<>();
	}

	protected static void setMouse(Mouse mouse) {
		GameState.mouse = mouse;
	}

	protected void setVisuals(Visual visuals) {
		this.visuals = visuals;
	}

	protected static void setLoadingThread(ResourceLoaderThread loaderThread) {
		GameState.loaderThread = loaderThread;
	}

	protected static void setConnecter(GraphicsDataConnecter connecter) {
		GameState.connecter = connecter;
	}

	protected static void setGuiBuilder(GUIBuilder guiBuilder) {
		GameState.guiBuilder = guiBuilder;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public Camera getCamera() {
		return camera;
	}

	public List<GUI> getGuis() {
		return guis;
	}

	public Visual getVisuals() {
		return visuals;
	}

	public static GUIBuilder getGuiBuilder() {
		return guiBuilder;
	}

	public ProjectionTransformation getProjectionTransformation() {
		return visuals.getProjectionTransformation();
	}

	protected void addGUI(GUI gui) {
		guis.add(gui);
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
		// Temporary, need to remove later
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

	public List<Map<VAO, List<GameEntity>>> getMeshLists() {
		// TODO this is very complex owo use octree frustum intersection uwu
		// The smaller the index of each map in the list, the more blurred it will be
		// when rendered.
		List<Map<VAO, List<GameEntity>>> meshesList = new ArrayList<>();
		meshesList.add(roomMeshEntityMap);
		return meshesList;
	}

	public Map<VAO, List<GameEntity>> getRoomMeshes() {
		return roomMeshEntityMap;
	}

	public void addKeyObserver(KeyObserver keyObserver) {
		synchronized (keyObservers) {
			keyObservers.add(keyObserver);
		}
	}

	public void addMouseMovementObserver(MouseMovementObserver mouseMovementObserver) {
		synchronized (mouseMovementObservers) {
			mouseMovementObservers.add(mouseMovementObserver);
		}
	}

	public void addMouseButtonObserver(MouseButtonObserver mouseButtonObserver) {
		synchronized (mouseButtonObservers) {
			mouseButtonObservers.add(mouseButtonObserver);
		}
	}

	public void addMouseScrollObserver(MouseScrollObserver mouseScrollObserver) {
		synchronized (mouseScrollObservers) {
			mouseScrollObservers.add(mouseScrollObserver);
		}
	}

	protected void setFlagToClearObservers() {
		clearObservers = true;
	}

	public void updateInputObservers(Input inputs) {
		if (clearObservers) {
			inputs.clearAllCallbackObservers();
			clearObservers = false;
		}
		if (keyObservers.size() > 0) {
			List<KeyObserver> copyKeyObservers = keyObservers;
			synchronized (keyObservers) {
				keyObservers = new ArrayList<>();
			}
			inputs.addKeyCallbackObservers(copyKeyObservers);
		}
		if (mouseMovementObservers.size() > 0) {
			List<MouseMovementObserver> copyMouseMovementObservers = mouseMovementObservers;
			synchronized (mouseMovementObservers) {
				mouseMovementObservers = new ArrayList<>();
			}
			inputs.addMouseMovementCallbackObservers(copyMouseMovementObservers);
		}
		if (mouseButtonObservers.size() > 0) {
			List<MouseButtonObserver> copyMouseButtonObservers = mouseButtonObservers;
			synchronized (mouseButtonObservers) {
				mouseButtonObservers = new ArrayList<>();
			}
			inputs.addMouseButtonCallbackObservers(copyMouseButtonObservers);
		}
		if (mouseScrollObservers.size() > 0) {
			List<MouseScrollObserver> copyMouseScrollObservers = mouseScrollObservers;
			synchronized (mouseScrollObservers) {
				mouseScrollObservers = new ArrayList<>();
			}
			inputs.addMouseScrollCallbackObservers(copyMouseScrollObservers);
		}
	}

	/**
	 * Will be called continually by the logics thread.
	 * 
	 * @return
	 */
	public abstract GameState update();

}
