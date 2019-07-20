package graphics.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.entity.Entity;
import graphics.gui.GUI;
import graphics.model.Mesh;
import graphics.transformation.ViewTransformation;

public class GameScene {

	protected ViewTransformation camera;
	protected List<GUI> guis = new ArrayList<>();
	protected Map<Mesh, List<Entity>> meshEntityMap = new HashMap<>();

	public GameScene() {
		camera = new ViewTransformation(0, 0, 0, 0, 0, 0);
		guis = new ArrayList<>();
		meshEntityMap = new HashMap<>();
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

}
