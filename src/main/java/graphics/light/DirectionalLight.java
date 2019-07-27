package graphics.light;

import math.Vector3f;

public class DirectionalLight extends Light {

	private float dirX;
	private float dirY;
	private float dirZ;

	public DirectionalLight(float dirX, float dirY, float dirZ) {
		this.dirX = dirX;
		this.dirY = dirY;
		this.dirZ = dirZ;
		normalize();
	}

	private void normalize() {
		Vector3f direction = getDirection();
		direction.normalise();
		dirX = direction.x;
		dirY = direction.y;
		dirZ = direction.z;
	}

	public Vector3f getDirection() {
		return new Vector3f(dirX, dirY, dirZ);
	}
}
