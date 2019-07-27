package graphics.transformation;

import math.Matrix4f;
import math.Vector3f;

public class ViewTransformation extends Transformation {

	private float posX = 0;
	private float posY = 0;
	private float posZ = 0;
	protected float pitch;
	protected float yaw;
	protected float roll;

	public ViewTransformation(float posX, float posY, float posZ, float pitch, float yaw, float roll) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	public Vector3f getPosition() {
		return new Vector3f(posX, posY, posZ);
	}

	public void setPosition(float x, float y, float z) {
		posX = x;
		posY = y;
		posZ = z;
		setFlag();
	}

	public void increasePosition(float dx, float dy, float dz) {
		setPosition(posX + dx, posY + dy, posZ + dz);
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

	public void setRotation(float rotX, float rotY, float rotZ) {
		pitch = rotX;
		roll = rotY;
		yaw = rotZ;
	}

	@Override
	protected void calculateMatrix() {
		matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(roll), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 0, 1), matrix, matrix);
		Vector3f negativeCameraPos = new Vector3f(-posX, -posY, -posZ);
		Matrix4f.translate(negativeCameraPos, matrix, matrix);
	}

}
