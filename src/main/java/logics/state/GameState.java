package logics.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.Visual;
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
import input.mouse.MousePicker;
import input.observer.KeyObserver;
import input.observer.MouseButtonObserver;
import input.observer.MouseMovementObserver;
import input.observer.MouseScrollObserver;

public abstract class GameState {

	protected static ResourceLoaderThread loaderThread;
	protected static GraphicsDataConnecter connecter;

	private static Mouse mouse;
	private Camera camera;
	private MousePicker mousePicker;
	private List<GUI> guis;
	private List<DirectionalLight> directionalLights;
	private List<PointLight> pointLights;
	private List<SpotLight> spotLights;
	private Map<VAO, List<Entity>> meshEntityMap;
	private List<KeyObserver> keyObservers;
	private List<MouseMovementObserver> mouseMovementObservers;
	private List<MouseButtonObserver> mouseButtonObservers;
	private List<MouseScrollObserver> mouseScrollObservers;
	private boolean clearObservers = false;

	public GameState() {
		camera = new Camera();
		mousePicker = new MousePicker(mouse, camera, Visual.getProjectionTransformation());
		guis = new ArrayList<>();
		directionalLights = new ArrayList<>();
		pointLights = new ArrayList<>();
		spotLights = new ArrayList<>();
		meshEntityMap = new HashMap<>();
		keyObservers = new ArrayList<>();
		mouseMovementObservers = new ArrayList<>();
		mouseButtonObservers = new ArrayList<>();
		mouseScrollObservers = new ArrayList<>();
	}

	protected void setMouse(Mouse mouse) {
		GameState.mouse = mouse;
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

	public void addKeyObserver(KeyObserver keyObserver) {
		synchronized (keyObservers) {
			keyObservers.add(keyObserver);
		}
	}

	public void addMouseMovementObserver(MouseMovementObserver mouseMovementObserver) {
		synchronized (mouseMovementObservers) {
			mouseMovementObservers.add(mouseMovementObserver);
		}
	}

	public void addMouseButtonObserver(MouseButtonObserver mouseButtonObserver) {
		synchronized (mouseButtonObservers) {
			mouseButtonObservers.add(mouseButtonObserver);
		}
	}

	public void addMouseScrollObserver(MouseScrollObserver mouseScrollObserver) {
		synchronized (mouseScrollObservers) {
			mouseScrollObservers.add(mouseScrollObserver);
		}
	}

	protected void setFlagToClearObservers() {
		clearObservers = true;
	}

	public void updateInputObservers(Input inputs) {
		if (clearObservers) {
			inputs.clearAllCallbackObservers();
			clearObservers = false;
		}
		if (keyObservers.size() > 0) {
			List<KeyObserver> copyKeyObservers = keyObservers;
			synchronized (keyObservers) {
				keyObservers = new ArrayList<>();
			}
			inputs.addKeyCallbackObservers(copyKeyObservers);
		}
		if (mouseMovementObservers.size() > 0) {
			List<MouseMovementObserver> copyMouseMovementObservers = mouseMovementObservers;
			synchronized (mouseMovementObservers) {
				mouseMovementObservers = new ArrayList<>();
			}
			inputs.addMouseMovementCallbackObservers(copyMouseMovementObservers);
		}
		if (mouseButtonObservers.size() > 0) {
			List<MouseButtonObserver> copyMouseButtonObservers = mouseButtonObservers;
			synchronized (mouseButtonObservers) {
				mouseButtonObservers = new ArrayList<>();
			}
			inputs.addMouseButtonCallbackObservers(copyMouseButtonObservers);
		}
		if (mouseScrollObservers.size() > 0) {
			List<MouseScrollObserver> copyMouseScrollObservers = mouseScrollObservers;
			synchronized (mouseScrollObservers) {
				mouseScrollObservers = new ArrayList<>();
			}
			inputs.addMouseScrollCallbackObservers(copyMouseScrollObservers);
		}
	}

	public MousePicker getMousePicker() {
		return mousePicker;
	}

	/**
	 * Will be called continually by the logics thread.
	 * 
	 * @return
	 */
	public abstract GameState update();

}
