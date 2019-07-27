package logics;

import graphics.entity.Entity;
import graphics.gui.GUI;
import graphics.gui.GUIBuilder;
import graphics.light.DirectionalLight;
import graphics.light.Light;
import graphics.loader.OBJLoader;
import graphics.model.Model;
import graphics.model.Texture;

public class HomePageState extends GameState {

	Entity cubeEntity;

	public HomePageState() {
		super();

		camera.setPosition(0, 11.5f, 1);
		camera.setRotation(45, 0, 0);

		Texture guiTexture1 = new Texture("dukemascot");
		GUI testGUI = GUIBuilder.newInstance().setDimensions(500, 30, 100, 100).setTexture(guiTexture1).create();
		addGUI(testGUI);
		Texture guiTexture2 = new Texture("table");
		GUI testGUI2 = GUIBuilder.newInstance().setDimensions(800, 300, 200, 100).setTexture(guiTexture2).create();
		addGUI(testGUI2);

		Texture texture = new Texture("table");
		Model model = new Model(OBJLoader.loadObjMesh("table"), texture);
		cubeEntity = new Entity(model, 0, 0, -5, 0, 0, 0, 1, 1, 1);
		addEntity(cubeEntity);

		Light directionalLight1 = new DirectionalLight(1, -1, 0);
		directionalLight1.setAmbient(0.5f, 0.4f, 0.2f);
		directionalLight1.setDiffuse(1f, 0.9f, 0.45f);
		directionalLight1.setSpecular(1f, 0.9f, 0.97f);
		addLight(directionalLight1);

	}

	@Override
	public void update() {
		camera.update();
		System.out.println(camera.getPosition());
	}

}
