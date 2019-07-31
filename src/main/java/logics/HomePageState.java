package logics;

import graphics.entity.Entity;
import graphics.gui.GUI;
import graphics.gui.GUIBuilder;
import graphics.light.DirectionalLight;
import graphics.light.Light;
import graphics.loader.OBJLoader;
import graphics.model.Model;
import graphics.model.ModelBuilder;
import graphics.model.Texture;
import graphics.model.VAO;

public class HomePageState extends GameState {

	Entity tableEntity;

	public HomePageState() {
		super();

		camera.setPosition(0, 11.5f, 1);
		camera.setRotation(45, 0, 0);

		GUI testGUI = GUIBuilder.newInstance().setDimensions(500, 30, 100, 100).setTexture("dukemascot").create();
		addGUI(testGUI);
		GUI testGUI2 = GUIBuilder.newInstance().setDimensions(800, 300, 200, 100).setTexture("table").create();
		addGUI(testGUI2);
//
		Texture texture = new Texture("table");
		Texture tableSpecularTexture = new Texture("tableSpecularMap");
		VAO tableMesh = OBJLoader.loadObjMesh("table");
		Model model = ModelBuilder.newInstance().setMesh(tableMesh).setDiffuseTexture(texture).setSpecularTexture(tableSpecularTexture).create();
		tableEntity = new Entity(model, 0, 0, -5, 0, 0, 0, 1, 1, 1);
		addEntity(tableEntity);

		Texture roomTexture = new Texture("room");
		Texture roomSpecularTexture = new Texture("roomSpecularMap");
		VAO roomMesh = OBJLoader.loadObjMesh("room");
		Model roomModel = ModelBuilder.newInstance().setMesh(roomMesh).setDiffuseTexture(roomTexture).setSpecularTexture(roomSpecularTexture).create();
		tableEntity = new Entity(roomModel, -0.5f, 0, -2, 0, 90, 0, 1, 1, 1);
		addEntity(tableEntity);

		Light directionalLight1 = new DirectionalLight(0, -1, 1f);
		directionalLight1.setAmbient(0.5f, 0.3f, 0.3f);
		directionalLight1.setDiffuse(1f, 1f, 1f);
		directionalLight1.setSpecular(0.6f, 0.33f, 0.16f);
		addLight(directionalLight1);

	}

	@Override
	public void update() {
		camera.update();
	}

}
