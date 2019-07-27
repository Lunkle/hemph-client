package graphics.light;

import math.Vector3f;

public class PointLight extends Light {

	private float posX;
	private float posY;
	private float posZ;

	public PointLight(float posX, float posY, float posZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public Vector3f getPosition() {
		return new Vector3f(posX, posY, posZ);
	}
}
