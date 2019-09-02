package logics.room;

import graphics.transformation.WorldTransformation;
import logics.octree.GameObject;

public class RoomObject extends GameObject {

	@Override
	public WorldTransformation getWorldTransformation() {
		return getEntity().getWorldTransformation();
	}

	@Override
	public void update() {}

}
