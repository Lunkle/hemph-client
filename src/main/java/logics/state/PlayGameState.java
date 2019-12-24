package logics.state;

import graphics.gui.GUI;
import graphics.light.DirectionalLight;
import graphics.light.PointLight;
import graphics.model.Model;
import graphics.model.ModelBuilder;
import graphics.rendering.Camera.Directions;
import graphics.texture.Texture;
import graphics.transformation.WorldTransformation;
import graphics.vao.VAO;
import input.command.Command;
import input.command.KeyCommand;
import input.information.Keys;
import input.observer.KeyObserver;
import input.observer.MouseButtonObserver;
import input.observer.MouseMovementObserver;
import loading.loader.ResourcePack;
import logics.globe.GlobeEntity;
import logics.octree.RoomEntity;
import math.Vector3f;

public class PlayGameState extends GameState {

	private GlobeEntity globeEntity;

	private ResourcePack resourcePack;

	private MouseButtonObserver globeSelectionObserver;
	private MouseButtonObserver globeReleaseObserver;
	private MouseMovementObserver globeRotationObserver;

	// Testing varying light cycles, should probably delete later
	private DirectionalLight directionalLight1;

	public PlayGameState(ResourcePack resourcePack) {
		super();
		setFlagToClearObservers();
		this.resourcePack = resourcePack;
	}

	private void setLights() {
		// DirectionalLight directionalLight1 = new DirectionalLight(0, -1, 1f);
		directionalLight1 = new DirectionalLight(0, -1, 1f);
		directionalLight1.setAmbient(0.45f, 0.3f, 0.3f);
		directionalLight1.setDiffuse(1f, 1f, 1f);
		directionalLight1.setSpecular(0.6f, 0.33f, 0.16f);
		directionalLight1.setStrength(0.3f);
		addLight(directionalLight1);

		// Main light source
		DirectionalLight directionalLight2 = new DirectionalLight(0, 0, -1f);
		directionalLight2.setAmbient(0.1f, 0.1f, 0.1f);
		directionalLight2.setDiffuse(0.1f, 0.1f, 0.1f);
		directionalLight2.setSpecular(0.1f, 0.1f, 0.1f);
		directionalLight2.setStrength(3f);
		addLight(directionalLight2);

		// The candle
		PointLight pointLight1 = new PointLight(-3, 7.5f, -5);
		pointLight1.setAmbient(0.65f, 0.7f, 0.4f);
		pointLight1.setDiffuse(0.8f, 0f, 0f);
		pointLight1.setSpecular(0.6f, 0.33f, 0.16f);
		pointLight1.setStrength(10);
		pointLight1.setConstants(1.8f, 0.7f, 1.0f);
		addLight(pointLight1);
	}

	private void positionCamera() {
		getCamera().setPosition(0, 9f, 1f);
		getCamera().setRotation(30, 0, 0);
	}

	private void setMouseGlobeSelectionCommands() {
		globeSelectionObserver = globeEntity.getGlobeSelectionObserver(getMouse(), getCamera(), getProjectionWrapper());
		globeReleaseObserver = globeEntity.getGlobeReleaseObserver(getMouse(), getCamera(), getProjectionWrapper());
		globeRotationObserver = globeEntity.getGlobeRotationObserver(getMouse(), getCamera(), getProjectionWrapper());
		addMouseButtonObserver(globeSelectionObserver);
		addMouseButtonObserver(globeReleaseObserver);
		addMouseMovementObserver(globeRotationObserver);
	}

	private void setCameraMovementKeyCommands() {
		KeyObserver cameraControlKeyObserver = new KeyObserver();

		Command front = new Command(() -> getCamera().addDirection(Directions.FRONT));
		Command back = new Command(() -> getCamera().addDirection(Directions.BACK));
		Command left = new Command(() -> getCamera().addDirection(Directions.LEFT));
		Command right = new Command(() -> getCamera().addDirection(Directions.RIGHT));
		Command up = new Command(() -> getCamera().addDirection(Directions.UP));
		Command down = new Command(() -> getCamera().addDirection(Directions.DOWN));

		cameraControlKeyObserver.addCommand(Keys.KEY_W, new KeyCommand(front, back));
		cameraControlKeyObserver.addCommand(Keys.KEY_A, new KeyCommand(left, right));
		cameraControlKeyObserver.addCommand(Keys.KEY_S, new KeyCommand(back, front));
		cameraControlKeyObserver.addCommand(Keys.KEY_D, new KeyCommand(right, left));
		cameraControlKeyObserver.addCommand(Keys.KEY_Q, new KeyCommand(down, up));
		cameraControlKeyObserver.addCommand(Keys.KEY_E, new KeyCommand(up, down));
		addKeyObserver(cameraControlKeyObserver);
	}

	@Override
	protected void initialize() {
		setCameraMovementKeyCommands();
		positionCamera();
		setLights();

		Texture globeTexture = resourcePack.getTexture("worldTexture");
		Texture globeSpecularTexture = resourcePack.getTexture("globeSpecularMap");
		VAO globeMesh = resourcePack.getMesh("globeMesh");
		Model globeModel = ModelBuilder.newInstance().setMesh(globeMesh).setDiffuseTexture(globeTexture).setSpecularTexture(globeSpecularTexture).create();
		WorldTransformation globeTransformation = new WorldTransformation(0, 7.9f, -5, new Vector3f(0, 1, 0), 0, 1, 1, 1);
		globeEntity = new GlobeEntity(globeModel, globeTransformation);
		addGameEntity(globeEntity);

		setMouseGlobeSelectionCommands();

		Texture duke = resourcePack.getTexture("dukeTexture");
		GUI testGUI = getGuiBuilder().newInstance().setDimensions(10, 10, 100, 100).setTexture(duke).create();
		addGUI(testGUI);

		Texture wood = resourcePack.getTexture("tableTexture");
		GUI testGUI2 = getGuiBuilder().newInstance().setDimensions(1170, 10, 100, 100).setTexture(wood).create();
		Command returnToTableViewCommand = new Command(() -> {
			globeEntity.defocusGlobe();
			getCamera().setTargetPosition(0, 9f, 1f);
			// getCamera().setRotation(30, 0, 0);
		});
		testGUI2.setOnReleaseCommand(getMouse(), returnToTableViewCommand, getProjectionWrapper());
		addGUI(testGUI2);

		Texture tableSpecularTexture = resourcePack.getTexture("tableSpecularMap");
		VAO tableMesh = resourcePack.getMesh("tableMesh");
		Model model = ModelBuilder.newInstance().setMesh(tableMesh).setDiffuseTexture(wood).setSpecularTexture(tableSpecularTexture).create();
		WorldTransformation tableWorldTransformation = new WorldTransformation(0, 0, -5, new Vector3f(0, 1, 0), 0, 1, 1, 1);
		RoomEntity tableEntity = new RoomEntity(model, tableWorldTransformation);
		addGameEntity(tableEntity);

		Texture roomTexture = resourcePack.getTexture("roomTexture");
		Texture roomSpecularTexture = resourcePack.getTexture("roomSpecularMap");
		VAO roomMesh = resourcePack.getMesh("roomMesh");
		Model roomModel = ModelBuilder.newInstance().setMesh(roomMesh).setDiffuseTexture(roomTexture).setSpecularTexture(roomSpecularTexture).create();
		WorldTransformation roomTransformation = new WorldTransformation(-2, 0, -4, new Vector3f(0, 1, 0), -90, 1.2f, 1.2f, 1.4f);
		RoomEntity roomEntity = new RoomEntity(roomModel, roomTransformation);
		addGameEntity(roomEntity);

		Texture globeStandTexture = resourcePack.getTexture("globeStandTexture");
		Texture globeStandSpecularTexture = resourcePack.getTexture("globeStandSpecularMap");
		VAO globeStandMesh = resourcePack.getMesh("globeStandMesh");
		Model globeStandModel = ModelBuilder.newInstance().setMesh(globeStandMesh).setDiffuseTexture(globeStandTexture).setSpecularTexture(globeStandSpecularTexture).create();
		WorldTransformation globeStandTransformation = new WorldTransformation(-1.0f, 4.9633f, -5.2f, new Vector3f(0, 1, 0), -45, 0.3f, 0.3f, 0.3f);
		RoomEntity globeStandEntity = new RoomEntity(globeStandModel, globeStandTransformation);
		addGameEntity(globeStandEntity);

		Texture treeTexture = resourcePack.getTexture("treeTexture");
		Texture treeSpecularTexture = resourcePack.getTexture("treeSpecularMap");
		VAO treeMesh = resourcePack.getMesh("treeMesh");
		Model treeModel = ModelBuilder.newInstance().setMesh(treeMesh).setDiffuseTexture(treeTexture).setSpecularTexture(treeSpecularTexture).create();
		WorldTransformation treeTransformation = new WorldTransformation(-1.0f, 4.9633f, -1.8f, new Vector3f(0, 1, 0), -45, 0.1f, 0.1f, 0.1f);
		RoomEntity treeEntity = new RoomEntity(treeModel, treeTransformation);
		addGameEntity(treeEntity);

		Texture candleTexture = resourcePack.getTexture("candleTexture");
		Texture candleSpecularTexture = resourcePack.getTexture("candleSpecularMap");
		VAO candleMesh = resourcePack.getMesh("candleMesh");
		Model candleModel = ModelBuilder.newInstance().setMesh(candleMesh).setDiffuseTexture(candleTexture).setSpecularTexture(candleSpecularTexture).create();
		WorldTransformation candleTransformation = new WorldTransformation(-3.0f, 4.9633f, -5.2f, new Vector3f(0, 1, 0), -45, 1f, 1f, 1f);
		RoomEntity candleEntity = new RoomEntity(candleModel, candleTransformation);
		addGameEntity(candleEntity);
	}

	@Override
	public void updateWindowDimensions(int windowWidth, int windowHeight) {}

	@Override
	public void update() {
		// directionalLight1.setStrength((float) (0.3f +
		// Math.sin(GLFW.glfwGetTime())));
		globeEntity.update();
		getCamera().update();
	}

}
