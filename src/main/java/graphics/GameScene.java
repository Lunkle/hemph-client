package graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.entity.Entity;
import graphics.entity.Light;
import graphics.model.TexturedModel;
import graphics.transformation.ViewTransformation;

public class GameScene {

	private ViewTransformation camera;
	private Map<TexturedModel, List<Entity>> entities;
	private List<Light> lights;

	public GameScene() {
		camera = new ViewTransformation(0, 0, 0, 0, 0, 0);
		entities = new HashMap<>();
		lights = new ArrayList<>();
	}

	public void addEntity(Entity entity) {
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

	public void addLight(Light light) {
		lights.add(light);
	}

	public ViewTransformation getCamera() {
		return camera;
	}

	public List<Light> getLights() {
		return lights;
	}

	public Map<TexturedModel, List<Entity>> getEntities() {
		return entities;
	}

}
