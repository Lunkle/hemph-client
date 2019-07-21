package logics;

import graphics.entity.Entity;
import graphics.gui.GUI;
import graphics.gui.GUIBuilder;
import graphics.model.Mesh;
import graphics.model.Model;
import graphics.model.Texture;

public class HomePageState extends GameState {

	Entity cubeEntity;

	public HomePageState() {
		super();

		GUI testGUI = GUIBuilder.newInstance().setDimensions(500, 30, 600, 300).create();
		GUI testGUI2 = GUIBuilder.newInstance().setDimensions(800, 300, 200, 300).create();
		addGUI(testGUI);
		addGUI(testGUI2);

		float[] positions = new float[] { -1, 1, 1, 1, 1, 1, -1, -1, 1, 1, -1, 1, -1, 1, -1, 1, 1, -1, -1, -1, -1, 1, -1, -1 };
		float[] textCoords = new float[] { 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.5f, 0.5f, 1.0f, 0.5f, };
		int[] indices = new int[] { 0, 2, 1, 1, 2, 3, 1, 3, 5, 5, 3, 7, 5, 7, 4, 4, 7, 6, 4, 6, 0, 0, 6, 2, 4, 0, 5, 5, 0, 1, 7, 3, 6, 6, 3, 2 };
		float[] normals = new float[] { 0, 1, 3, 3, 1, 2, 8, 10, 11, 9, 8, 11, 12, 13, 7, 5, 12, 7, 14, 15, 6, 4, 14, 6, 16, 18, 19, 17, 16, 19, 4, 6, 7, 5, 4, 7, };
		Mesh mesh = new Mesh(indices, positions, textCoords, normals);
		Model model = new Model(new Texture("dukemascot"));
		model.addMesh(mesh);
		cubeEntity = new Entity(model, 0, 0, 0, 0, 0, 0, 1, 1, 1);
		cubeEntity.increasePosition(0, 0, -4);
		cubeEntity.increaseRotation(-45, 0, 0);
		addEntity(cubeEntity);
	}

	@Override
	public void update() {
		cubeEntity.increaseRotation(-1, 1, 0);
	}

}
