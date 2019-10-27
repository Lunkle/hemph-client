package logics.octree;

import graphics.model.Model;
import graphics.transformation.WorldTransformation;

public class DependentEntity extends GameEntity {

	private GameEntity parent;

	private boolean isDirty = true;
	private WorldTransformation absoluteWorldTransformation;

	public DependentEntity(GameEntity parent, Model model, WorldTransformation localWorldTransformation) {
		super(model, localWorldTransformation);
		this.parent = parent;
	}

	@Override
	public WorldTransformation getAbsoluteWorldTransformation() {
		if (isDirty) {
			absoluteWorldTransformation = WorldTransformation.getCombinedTransformation(getParent().getAbsoluteWorldTransformation(), getWorldTransformation());
		}
		return absoluteWorldTransformation;
	}

	public GameEntity getParent() {
		return parent;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
