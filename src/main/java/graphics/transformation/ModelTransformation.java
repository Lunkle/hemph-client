package graphics.transformation;

import math.Matrix4f;
import math.Vector3f;

public class ModelTransformation extends Transformation {

	private float posX = 0;
	private float posY = 0;
	private float posZ = 0;
	private float rotX = 0;
	private float rotY = 0;
	private float rotZ = 0;
	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;

	public ModelTransformation(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		calculateMatrix();
	}

	public Vector3f getPosition() {
		return new Vector3f(posX, posY, posY);
	}

	public void setPosition(float x, float y, float z) {
		posX = x;
		posY = y;
		posZ = z;
	}

	public void increasePosition(float dx, float dy, float dz) {
		posX += dx;
		posY += dy;
		posZ += dz;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	public float getScaleZ() {
		return scaleZ;
	}

	public void setScaleZ(float scaleZ) {
		this.scaleZ = scaleZ;
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
