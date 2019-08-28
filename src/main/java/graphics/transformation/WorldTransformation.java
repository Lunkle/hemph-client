package graphics.transformation;

import math.Matrix4f;
import math.Vector3f;

public class WorldTransformation extends Transformation {

	private float posX = 0;
	private float posY = 0;
	private float posZ = 0;
	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;
	private UnitQuaternion quaternion;

	public WorldTransformation(float posX, float posY, float posZ, Vector3f axisOfRotation, float angle, float scaleX, float scaleY, float scaleZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		quaternion = new UnitQuaternion(axisOfRotation, angle / 2);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		setFlag();
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

	public UnitQuaternion getQuaternion() {
		return quaternion;
	}

	public void setRotation(Vector3f axisOfRotation, float angle) {
		quaternion.setAngleAxis(axisOfRotation, angle / 2);
		setFlag();
	}

	public void increaseRotation(Vector3f axisOfRotation, float angle) {
		quaternion.applyRotation(axisOfRotation, angle);
		setFlag();
	}

	public void increaseRotation(UnitQuaternion rotation) {
		quaternion.applyRotation(rotation);
		setFlag();
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
		Matrix4f.translate(new Vector3f(posX, posY, posZ), matrix, matrix);
		matrix = Matrix4f.multiply(matrix, quaternion.toRotationMatrix());
		Matrix4f.scale(new Vector3f(scaleX, scaleY, scaleZ), matrix, matrix);
	}

}
