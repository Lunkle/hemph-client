package logics.state;

import graphics.entity.Entity;
import graphics.gui.GUI;
import graphics.gui.GUIBuilder;
import graphics.light.DirectionalLight;
import graphics.light.Light;
import graphics.loader.ResourcePack;
import graphics.model.Model;
import graphics.model.ModelBuilder;
import graphics.rendering.Camera.Directions;
import graphics.texture.Texture;
import graphics.vao.VAO;
import input.command.Command;
import input.command.KeyCommand;
import input.information.Keys;
import input.observer.KeyObserver;

public class TableTopState extends GameState {

	private Entity globeEntity;

	public TableTopState(ResourcePack resourcePack) {
		setFlagToClearObservers();

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

		getCamera().setPosition(0, 11f, 2f);
		getCamera().setRotation(40, 0, 0);

		Texture duke = resourcePack.getTexture("dukeTexture");
		GUI testGUI = GUIBuilder.newInstance().setDimensions(10, 10, 100, 100).setTexture(duke).create();
		addGUI(testGUI);
//
		Texture wood = resourcePack.getTexture("tableTexture");
		GUI testGUI2 = GUIBuilder.newInstance().setDimensions(1170, 10, 100, 100).setTexture(wood).create();
		addGUI(testGUI2);
//
		Texture tableSpecularTexture = resourcePack.getTexture("tableSpecularMap");
		VAO tableMesh = resourcePack.getMesh("tableMesh");
		Model model = ModelBuilder.newInstance().setMesh(tableMesh).setDiffuseTexture(wood).setSpecularTexture(tableSpecularTexture).create();
		Entity tableEntity = new Entity(model, 0, 0, -5, 0, 0, 0, 1, 1, 1);
		addEntity(tableEntity);
//
		Texture roomTexture = resourcePack.getTexture("roomTexture");
		Texture roomSpecularTexture = resourcePack.getTexture("roomSpecularMap");
		VAO roomMesh = resourcePack.getMesh("roomMesh");
		Model roomModel = ModelBuilder.newInstance().setMesh(roomMesh).setDiffuseTexture(roomTexture).setSpecularTexture(roomSpecularTexture).create();
		Entity roomEntity = new Entity(roomModel, -3, 0, -4, 0, 90, 0, 1.2f, 1.2f, 1.4f);
		addEntity(roomEntity);
//
//		Texture globeTexture = new Texture(new ByteBufferImageRawData("green"));
//		Texture globeSpecularTexture = new Texture(new ByteBufferImageRawData("globeSpecularMap"));
//		VAO globeMesh = new Globe(4).getMesh();
//		Model globeModel = ModelBuilder.newInstance().setMesh(globeMesh).setDiffuseTexture(globeTexture).setSpecularTexture(globeSpecularTexture).create();
//		globeEntity = new Entity(globeModel, 0, 6, -5, 0, 0, 0, 1, 1, 1);
//		addEntity(globeEntity);

		Light directionalLight1 = new DirectionalLight(0, -1, 1f);
		directionalLight1.setAmbient(0.5f, 0.3f, 0.3f);
		directionalLight1.setDiffuse(1f, 1f, 1f);
		directionalLight1.setSpecular(0.6f, 0.33f, 0.16f);
		addLight(directionalLight1);

	}

	@Override
	public GameState update() {
//		globeEntity.increaseRotation(1, 1, 0);
		getCamera().update();
		return this;
	}

}
