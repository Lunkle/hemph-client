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
import graphics.skybox.Skybox;
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

	private List<Skybox> skyboxes;

	public GameState() {
		camera = new Camera();
		guis = new ArrayList<>();
		directionalLights = new ArrayList<>();
		pointLights = new ArrayList<>();
		spotLights = new ArrayList<>();
		octree = new Octree();
		roomMeshEntityMap = new HashMap<>();
		skyboxes = new ArrayList<>();
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

	public GameStateWrapper getGameStateWrapper() {
		return gameStateWrapper;
	}

	public List<Skybox> getSkyboxes() {
		return skyboxes;
	}

	public void addSkybox(Skybox skybox) {
		skyboxes.add(skybox);
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
		getGameStateWrapper().getInput().appendKeyObserver(keyObserver);
	}

	public void addMouseMovementObserver(MouseMovementObserver mouseMovementObserver) {
		getGameStateWrapper().getInput().appendMouseMovementObserver(mouseMovementObserver);
	}

	public void addMouseButtonObserver(MouseButtonObserver mouseButtonObserver) {
		getGameStateWrapper().getInput().appendMouseButtonObserver(mouseButtonObserver);
	}

	public void addMouseScrollObserver(MouseScrollObserver mouseScrollObserver) {
		getGameStateWrapper().getInput().appendMouseScrollObserver(mouseScrollObserver);
	}

	public void removeKeyObserver(KeyObserver keyObserver) {
		getGameStateWrapper().getInput().removeKeyObserver(keyObserver);
	}

	public void removeMouseMovementObserver(MouseMovementObserver mouseMovementObserver) {
		getGameStateWrapper().getInput().removeMouseMovementObserver(mouseMovementObserver);
	}

	public void removeMouseButtonObserver(MouseButtonObserver mouseButtonObserver) {
		getGameStateWrapper().getInput().removeMouseButtonObserver(mouseButtonObserver);
	}

	public void removeMouseScrollObserver(MouseScrollObserver mouseScrollObserver) {
		getGameStateWrapper().getInput().removeMouseScrollObserver(mouseScrollObserver);
	}

	protected void clearObservers() {
		GameStateWrapper gameStateWrapper = getGameStateWrapper();
		Input input = gameStateWrapper.getInput();
		input.clearAllCallbackObservers();
	}

	public final void transition(GameState state) {
		gameStateWrapper.setState(state);
		state.initialize();
	}

	public abstract void updateWindowDimensions(int windowWidth, int windowHeight);

	/**
	 * Will be called once upon transitioning
	 */
	public abstract void initialize();

	/**
	 * Will be called continually by the logics thread.
	 */
	public abstract void update();
}
