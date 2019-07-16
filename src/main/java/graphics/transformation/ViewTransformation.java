package graphics.transformation;

import math.Matrix4f;
import math.Vector3f;

public class ViewTransformation extends Transformation {

	protected Vector3f position;
	protected float pitch;
	protected float yaw;
	protected float roll;

	public ViewTransformation(float posX, float posY, float posZ, float pitch, float yaw, float roll) {
		position = new Vector3f(posX, posY, posZ);
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	@Override
	protected void calculateMatrix() {
		matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 0, 1), matrix, matrix);
		Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);
		Matrix4f.translate(negativeCameraPos, matrix, matrix);
	}

}
