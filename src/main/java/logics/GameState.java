package logics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.entity.Entity;
import graphics.gui.GUI;
import graphics.light.DirectionalLight;
import graphics.light.Light;
import graphics.light.PointLight;
import graphics.light.SpotLight;
import graphics.model.VAO;
import graphics.rendering.Camera;
import math.Matrix4f;

public abstract class GameState {

	protected Camera camera;
	protected List<GUI> guis;
	protected List<DirectionalLight> directionalLights;
	protected List<PointLight> pointLights;
	protected List<SpotLight> spotLights;
	protected Map<VAO, List<Entity>> meshEntityMap;

	public GameState() {
		camera = new Camera();
		guis = new ArrayList<>();
		directionalLights = new ArrayList<>();
		pointLights = new ArrayList<>();
		spotLights = new ArrayList<>();
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

	public List<DirectionalLight> getDirectionalLights() {
		return directionalLights;
	}

	public List<PointLight> getPointLights() {
		return pointLights;
	}

	public List<SpotLight> getSpotLights() {
		return spotLights;
	}

	protected void addLight(Light light) {
		if (light instanceof DirectionalLight) {
			directionalLights.add((DirectionalLight) light);
		} else if (light instanceof SpotLight) {
			spotLights.add((SpotLight) light);
		} else if (light instanceof PointLight) {
			pointLights.add((PointLight) light);
		}
	}

	public Map<VAO, List<Entity>> getMeshes() {
		return meshEntityMap;
	}

	protected void addEntity(Entity entity) {
		VAO mesh = entity.getModel().getMesh();
		if (meshEntityMap.containsKey(mesh)) {
			meshEntityMap.get(mesh).add(entity);
		} else {
			List<Entity> entityList = new ArrayList<>();
			entityList.add(entity);
			meshEntityMap.put(mesh, entityList);
		}
	}

	public Matrix4f getViewMatrix() {
		return camera.getViewMatrix();
	}
}
