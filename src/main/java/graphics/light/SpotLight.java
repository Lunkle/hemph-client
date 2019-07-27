package graphics.light;

import math.Vector3f;

public class SpotLight extends Light {

	private float posX;
	private float posY;
	private float posZ;
	private float dirX;
	private float dirY;
	private float dirZ;
	private float angle;

	public SpotLight(float posX, float posY, float posZ, float dirX, float dirY, float dirZ, float angle) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.dirX = dirX;
		this.dirY = dirY;
		this.dirZ = dirZ;
		this.angle = angle;
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

	public Vector3f getPosition() {
		return new Vector3f(posX, posY, posZ);
	}

	public float getAngle() {
		return angle;
	}
}
