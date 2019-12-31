package graphics.transformation;

import math.Matrix4f;
import math.Vector3f;

public class ViewTransformation extends Transformation {

	private Vector3f position;
	private UnitQuaternion quaternion;

	public ViewTransformation(Vector3f position, UnitQuaternion quaternion) {
		this.position = position;
		this.quaternion = quaternion;
	}

	public ViewTransformation(float posX, float posY, float posZ, Vector3f axisOfRotation, float angle) {
		this(new Vector3f(posX, posY, posZ), new UnitQuaternion(axisOfRotation, angle / 2));
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
		raiseFlag();
	}

	public void increasePosition(float dx, float dy, float dz) {
		setPosition(position.x + dx, position.y + dy, position.z + dz);
		raiseFlag();
	}

	public UnitQuaternion getRotation() {
		return new UnitQuaternion(quaternion);
	}

	public void setRotation(UnitQuaternion q) {
		quaternion = new UnitQuaternion(q);
		raiseFlag();
	}

	public void setRotation(Vector3f axis, float theta) {
		quaternion = new UnitQuaternion(axis, theta / 2);
		raiseFlag();
	}

	public void increasePosition(Vector3f axis, float rot) {
		quaternion = quaternion.multiply(new UnitQuaternion(axis, rot));
		raiseFlag();
	}

	@Override
	protected void calculateMatrix() {
		matrix = new Matrix4f();
		matrix = Matrix4f.multiply(matrix, quaternion.toRotationMatrix());
		Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);
		Matrix4f.translate(negativeCameraPos, matrix, matrix);
	}

}
