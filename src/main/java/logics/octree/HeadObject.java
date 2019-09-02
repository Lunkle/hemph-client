package logics.octree;

import graphics.transformation.WorldTransformation;

/**
 * Head object is the game object that has no parent.
 * 
 * @author Donny
 *
 */
public abstract class HeadObject extends GameObject {

	@Override
	public WorldTransformation getWorldTransformation() {
		return getEntity().getWorldTransformation();
	}

}
