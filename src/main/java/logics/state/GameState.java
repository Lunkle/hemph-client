package logics.state;

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
import graphics.loader.GraphicsDataConnecter;
import graphics.loader.ResourceLoaderThread;
import graphics.rendering.Camera;
import graphics.vao.VAO;
import input.Input;
import input.mouse.Mouse;
import input.observer.KeyObserver;
import input.observer.MouseObserver;
import math.Matrix4f;

public abstract class GameState {

	protected static ResourceLoaderThread loaderThread;
	protected static GraphicsDataConnecter connecter;

	private Mouse mouse;
	private Camera camera;
	private List<GUI> guis;
	private List<DirectionalLight> directionalLights;
	private List<PointLight> pointLights;
	private List<SpotLight> spotLights;
	private Map<VAO, List<Entity>> meshEntityMap;
	private List<KeyObserver> keyObservers;
	private List<MouseObserver> mouseObservers;
	private boolean clearObservers = false;

	public GameState() {
		mouse = new Mouse();
		camera = new Camera();
		guis = new ArrayList<>();
		directionalLights = new ArrayList<>();
		pointLights = new ArrayList<>();
		spotLights = new ArrayList<>();
		meshEntityMap = new HashMap<>();
		keyObservers = new ArrayList<>();
		mouseObservers = new ArrayList<>();
	}

	protected static void setLoadingThread(ResourceLoaderThread loaderThread) {
		GameState.loaderThread = loaderThread;
	}

	protected static void setConnecter(GraphicsDataConnecter connecter) {
		GameState.connecter = connecter;
	}

	public Mouse getMouse() {
		return mouse;
	}

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

	public void addKeyObserver(KeyObserver keyObserver) {
		synchronized (keyObservers) {
			keyObservers.add(keyObserver);
		}
	}

	public void addMouseObserver(MouseObserver mouseObserver) {
		synchronized (mouseObservers) {
			mouseObservers.add(mouseObserver);
		}
	}

	protected void setFlagToClearObservers() {
		clearObservers = true;
	}

	public void updateInputObservers(Input inputs) {
		if (clearObservers) {
			inputs.clearKeyCallbackObservers();
			inputs.clearMouseCallbackObservers();
			clearObservers = false;
		}
		if (keyObservers.size() > 0) {
			List<KeyObserver> copyKeyObservers = keyObservers;
			synchronized (keyObservers) {
				keyObservers = new ArrayList<>();
			}
			inputs.addKeyCallbackObservers(copyKeyObservers);
		}
		if (mouseObservers.size() > 0) {
			List<MouseObserver> copyMouseObservers = mouseObservers;
			synchronized (mouseObservers) {
				keyObservers = new ArrayList<>();
			}
			inputs.addMouseCallbackObservers(copyMouseObservers);
		}
	}

	/**
	 * Will be called continually by the logics thread.
	 * 
	 * @return
	 */
	public abstract GameState update();

}
