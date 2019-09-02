package logics.octree;

import graphics.entity.Entity;
import graphics.transformation.WorldTransformation;

public abstract class GameObject {

	private GameObject parent;

	private Entity entity;
	private WorldTransformation worldTransformation;

	public GameObject(WorldTransformation worldTransformation) {}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public WorldTransformation getWorldTransformation() {
		entity.getWorldTransformation().applyTransformation(parent.getWorldTransformation());
		return entity.getWorldTransformation();
	}

	public abstract void update();

}
