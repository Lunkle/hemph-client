package logics.octree;

import graphics.model.Model;
import graphics.transformation.WorldTransformation;

/**
 * Head object is the game object that has no parent.
 * 
 * @author Donny
 *
 */
public abstract class HeaderEntity extends GameEntity {

	public HeaderEntity(Model model, WorldTransformation worldTransformation) {
		super(model, worldTransformation);
	}

	@Override
	public WorldTransformation getAbsoluteWorldTransformation() {
		return getWorldTransformation();
	}

}
