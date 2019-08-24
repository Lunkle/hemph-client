package graphics.transformation;

import math.Matrix4f;
import math.Vector3f;

public class WorldTransformation extends Transformation {

	private float posX = 0;
	private float posY = 0;
	private float posZ = 0;
//	private Quaternion rotation;
	private float rotX = 0;
	private float rotY = 0;
	private float rotZ = 0;
	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;

	public WorldTransformation(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
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

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotation(float rotX, float rotY, float rotZ) {
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		setFlag();
	}

	public void increaseRotation(float dx, float dy, float dz) {
		setRotation(rotX + dx, rotY + dy, rotZ + dz);
	}

	public float getScaleX() {
		return scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public float getScaleZ() {
		return scaleZ;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
		setFlag();
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
		setFlag();
	}

	public void setScaleZ(float scaleZ) {
		this.scaleZ = scaleZ;
		setFlag();
	}

	@Override
	protected void calculateMatrix() {
		matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(posX, posY, posZ), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotX), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotY), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotZ), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scaleX, scaleY, scaleZ), matrix, matrix);
	}

}
