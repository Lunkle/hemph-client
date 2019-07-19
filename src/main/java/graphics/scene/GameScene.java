package graphics.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.entity.Entity;
import graphics.entity.Light;
import graphics.gui.GUI;
import graphics.model.TexturedModel;
import graphics.transformation.ViewTransformation;

public class GameScene {

	protected ViewTransformation camera;
	protected List<GUI> guis = new ArrayList<>();
	protected Map<TexturedModel, List<Entity>> entities;
	protected List<Light> lights;

	public GameScene() {
		camera = new ViewTransformation(0, 0, 0, 0, 0, 0);
		guis = new ArrayList<>();
		entities = new HashMap<>();
		lights = new ArrayList<>();
	}

	public ViewTransformation getCamera() {
		return camera;
	}

	public List<GUI> getGuis() {
		return guis;
	}

	protected void addGUI(GUI gui) {
		guis.add(gui);
	}

	public Map<TexturedModel, List<Entity>> getEntities() {
		return entities;
	}

	protected void addEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = getEntities().get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			getEntities().put(entityModel, newBatch);
		}
	}

	public List<Light> getLights() {
		return lights;
	}

	protected void addLight(Light light) {
		lights.add(light);
	}

}
