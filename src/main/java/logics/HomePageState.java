package logics;

import graphics.entity.Entity;
import graphics.gui.GUI;
import graphics.gui.GUIBuilder;
import graphics.loader.OBJLoader;
import graphics.model.Model;
import graphics.model.Texture;
import graphics.model.VAO;
import graphics.model.VAOBuilder;

public class HomePageState extends GameState {

	Entity cubeEntity;

	public HomePageState() {
		super();

		Texture texture = new Texture("xyzArrows");
		Texture guiTexture = new Texture("dukemascot");

		GUI testGUI = GUIBuilder.newInstance().setDimensions(500, 30, 100, 100).setTexture(guiTexture).create();
		GUI testGUI2 = GUIBuilder.newInstance().setDimensions(800, 300, 200, 300).setTexture(guiTexture).create();
		addGUI(testGUI);
		addGUI(testGUI2);

		float[] positions = new float[] { -1, 1, 1, 1, 1, 1, -1, -1, 1, 1, -1, 1, -1, 1, -1, 1, 1, -1, -1, -1, -1, 1, -1, -1 };
		float[] textCoords = new float[] { 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1 };
		float[] normals = new float[] { 0, 1, 3, 3, 1, 2, 8, 10, 11, 9, 8, 11, 12, 13, 7, 5, 12, 7, 14, 15, 6, 4, 14, 6, 16, 18, 19, 17, 16, 19, 4, 6, 7, 5, 4, 7, };
		int[] indices = new int[] { 0, 2, 1, 1, 2, 3, 1, 3, 5, 5, 3, 7, 5, 7, 4, 4, 7, 6, 4, 6, 0, 0, 6, 2, 4, 0, 5, 5, 0, 1, 7, 3, 6, 6, 3, 2 };
		VAO mesh = VAOBuilder.createVAO(positions, textCoords, normals, indices);
		mesh = OBJLoader.loadObjMesh("xyzArrows");
		Model model = new Model(mesh, texture);
		cubeEntity = new Entity(model, 0, 0, 0, 0, 0, 0, 1, 1, 1);
		cubeEntity.increasePosition(0, 0, -4);
		cubeEntity.increaseRotation(0, 0, 0);
		addEntity(cubeEntity);
	}

	@Override
	public void update() {
		cubeEntity.increaseRotation(-1, 1, 0);
		camera.update();
	}

}
