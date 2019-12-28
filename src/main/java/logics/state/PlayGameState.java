package logics.state;

import org.lwjgl.glfw.GLFW;

import graphics.gui.GUI;
import graphics.light.DirectionalLight;
import graphics.light.PointLight;
import graphics.model.Model;
import graphics.model.ModelBuilder;
import graphics.rendering.Camera.Directions;
import graphics.skybox.Skybox;
import graphics.texture.ModelTexture;
import graphics.transformation.WorldTransformation;
import graphics.vao.VAO;
import input.command.Command;
import input.command.KeyCommand;
import input.information.Key;
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
		getCamera().setRotation(10, 0, 0);
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
		cameraControlKeyObserver.setName("Camera Key Control Observer");
		Command front = new Command(() -> getCamera().addDirection(Directions.FRONT));
		Command back = new Command(() -> getCamera().addDirection(Directions.BACK));
		Command left = new Command(() -> getCamera().addDirection(Directions.LEFT));
		Command right = new Command(() -> getCamera().addDirection(Directions.RIGHT));
		Command up = new Command(() -> getCamera().addDirection(Directions.UP));
		Command down = new Command(() -> getCamera().addDirection(Directions.DOWN));
		cameraControlKeyObserver.addCommand(Key.KEY_W, new KeyCommand(front, back));
		cameraControlKeyObserver.addCommand(Key.KEY_A, new KeyCommand(left, right));
		cameraControlKeyObserver.addCommand(Key.KEY_S, new KeyCommand(back, front));
		cameraControlKeyObserver.addCommand(Key.KEY_D, new KeyCommand(right, left));
		cameraControlKeyObserver.addCommand(Key.KEY_Q, new KeyCommand(down, up));
		cameraControlKeyObserver.addCommand(Key.KEY_E, new KeyCommand(up, down));
		addKeyObserver(cameraControlKeyObserver);
	}

	@Override
	public void initialize() {
		clearObservers();
		setCameraMovementKeyCommands();
		positionCamera();
		setLights();

		Skybox skybox = new Skybox(resourcePack.getSkyboxTexture("skybox"), 1);
		addSkybox(skybox);

		ModelTexture globeTexture = resourcePack.getModelTexture("worldTexture");
		ModelTexture globeSpecularTexture = resourcePack.getModelTexture("globeSpecularMap");
		VAO globeMesh = resourcePack.getMesh("globeMesh");
		Model globeModel = ModelBuilder.newInstance().setMesh(globeMesh).setDiffuseTexture(globeTexture).setSpecularTexture(globeSpecularTexture).create();
		WorldTransformation globeTransformation = new WorldTransformation(0, 7.9f, -5, new Vector3f(0, 1, 0), 0, 1, 1, 1);
		globeEntity = new GlobeEntity(globeModel, globeTransformation);
		addGameEntity(globeEntity);

		ModelTexture duke = resourcePack.getModelTexture("dukeTexture");
		GUI testGUI = getGuiBuilder().newInstance().setDimensions(10, 10, 100, 100).setTexture(duke).create();
		testGUI.setName("Mascot Button");
		addGUI(testGUI);

		ModelTexture wood = resourcePack.getModelTexture("tableTexture");
		GUI testGUI2 = getGuiBuilder().newInstance().setDimensions(1170, 10, 100, 100).setTexture(wood).create();
		testGUI2.setName("Wood Button");
		Command returnToTableViewCommand = new Command(() -> {
			globeEntity.defocusGlobe();
			getCamera().setTargetPosition(0, 9f, 1f);
			// getCamera().setRotation(30, 0, 0);
		});
		testGUI2.setOnReleaseCommand(getMouse(), returnToTableViewCommand, getProjectionWrapper());
		addGUI(testGUI2);

		setMouseGlobeSelectionCommands();

		// MouseScrollObserver mouseScrollObserver = new MouseScrollObserver();
		// addMouseScrollObserver(mouseScrollObserver);

		addRoomEntityIntoScene("tableTexture", "tableSpecularMap", "tableMesh", new WorldTransformation(0, 0, -5, new Vector3f(0, 1, 0), 0, 1, 1, 1));
//		addRoomEntityIntoScene("roomTexture", "roomSpecularMap", "roomMesh", new WorldTransformation(-2, 0, -4, new Vector3f(0, 1, 0), -90, 1.2f, 1.2f, 1.4f));
		addRoomEntityIntoScene("globeStandTexture", "globeStandSpecularMap", "globeStandMesh", new WorldTransformation(-1.0f, 4.9633f, -5.2f, new Vector3f(0, 1, 0), -45, 0.3f, 0.3f, 0.3f));
		addRoomEntityIntoScene("treeSpringTexture", "treeSpringSpecularMap", "treeSpringMesh", new WorldTransformation(-3f, 4.9633f, -3f, new Vector3f(0, 1, 0), 0, 0.05f, 0.05f, 0.05f));
		addRoomEntityIntoScene("treeSummerTexture", "treeSummerSpecularMap", "treeSummerMesh", new WorldTransformation(-1f, 4.9633f, -3f, new Vector3f(0, 1, 0), 0, 0.05f, 0.05f, 0.05f));
		addRoomEntityIntoScene("treeFallTexture", "treeFallSpecularMap", "treeFallMesh", new WorldTransformation(1f, 4.9633f, -3f, new Vector3f(0, 1, 0), 0, 0.05f, 0.05f, 0.05f));
		addRoomEntityIntoScene("treeWinterTexture", "treeWinterSpecularMap", "treeWinterMesh", new WorldTransformation(3f, 4.9633f, -3f, new Vector3f(0, 1, 0), 0, 0.05f, 0.05f, 0.05f));
		addRoomEntityIntoScene("candleTexture", "candleSpecularMap", "candleMesh", new WorldTransformation(-3.0f, 4.9633f, -5.2f, new Vector3f(0, 1, 0), -45, 1f, 1f, 1f));
	}

	public void addRoomEntityIntoScene(String textureName, String specularTextureName, String meshName, WorldTransformation transformation) {
		ModelTexture texture = resourcePack.getModelTexture(textureName);
		if (texture == null) {
			throw new RuntimeException("Texture '" + textureName + "' not found");
		}
		ModelTexture specularTexture = resourcePack.getModelTexture(specularTextureName);
		if (specularTexture == null) {
			throw new RuntimeException("Specular texture '" + specularTextureName + "' not found");
		}
		VAO mesh = resourcePack.getMesh(meshName);
		if (mesh == null) {
			throw new RuntimeException("Mesh '" + meshName + "' not found");
		}
		Model model = ModelBuilder.newInstance().setMesh(mesh).setDiffuseTexture(texture).setSpecularTexture(specularTexture).create();
		RoomEntity entity = new RoomEntity(model, transformation);
		addGameEntity(entity);
	}

	@Override
	public void updateWindowDimensions(int windowWidth, int windowHeight) {}

	@Override
	public void update() {
		directionalLight1.setStrength((float) (0.3f + Math.sin(0.02 * GLFW.glfwGetTime())));
		globeEntity.update();
		getCamera().update();
		getCamera().getViewTransformation().setRotation(getCamera().getViewTransformation().getPitch(), getCamera().getViewTransformation().getRoll() + 0.2f, getCamera().getViewTransformation().getYaw());
	}

}
