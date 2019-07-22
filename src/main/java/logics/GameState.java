package logics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.Camera;
import graphics.entity.Entity;
import graphics.gui.GUI;
import graphics.model.Mesh;
import math.Matrix4f;

public abstract class GameState {

	protected Camera camera;
	protected List<GUI> guis = new ArrayList<>();
	protected Map<Mesh, List<Entity>> meshEntityMap = new HashMap<>();

	public GameState() {
		camera = new Camera();
		guis = new ArrayList<>();
		meshEntityMap = new HashMap<>();
	}

	public abstract void update();

	public Camera getCamera() {
		return camera;
	}

	public List<GUI> getGuis() {
		return guis;
	}

	protected void addGUI(GUI gui) {
		guis.add(gui);
	}

	public Map<Mesh, List<Entity>> getMeshes() {
		return meshEntityMap;
	}

	protected void addEntity(Entity entity) {
		List<Mesh> meshes = entity.getModel().getMeshes();
		for (Mesh mesh : meshes) {
			if (meshEntityMap.containsKey(mesh)) {
				meshEntityMap.get(mesh).add(entity);
			} else {
				List<Entity> entityList = new ArrayList<>();
				entityList.add(entity);
				meshEntityMap.put(mesh, entityList);
			}
		}
	}

	public Matrix4f getViewMatrix() {
		return camera.getViewMatrix();
	}
}
