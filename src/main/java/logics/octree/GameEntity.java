package logics.octree;

import graphics.model.Model;
import graphics.transformation.WorldTransformation;

public abstract class GameEntity {

	private Model model;
	private WorldTransformation worldTransformation;

	public GameEntity(Model model, WorldTransformation worldTransformation) {
		this.model = model;
		this.worldTransformation = worldTransformation;
	}

	public Model getModel() {
		return model;
	}

	public WorldTransformation getWorldTransformation() {
		return worldTransformation;
	}

//	public abstract WorldTransformation getAbsoluteWorldTransformation();
	public WorldTransformation getAbsoluteWorldTransformation() {
		return worldTransformation;
	}

	public abstract void update();

}
