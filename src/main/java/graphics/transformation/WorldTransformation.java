package graphics.transformation;

import math.Matrix4f;
import math.Vector3f;

public class WorldTransformation extends Transformation {

	private Vector3f position;
	private Vector3f scale;
	private UnitQuaternion quaternion;

	/**
	 * Constructor for the WorldTransformation class.
	 * 
	 * @param the offset or position
	 * @param the rotation quaternion
	 * @param the scaling vector
	 */
	public WorldTransformation(Vector3f position, UnitQuaternion quaternion, Vector3f scale) {
		this.position = position;
		this.quaternion = quaternion;
		this.scale = scale;
		raiseFlag();
	}

	public WorldTransformation(float posX, float posY, float posZ, Vector3f axisOfRotation, float angle, float scaleX, float scaleY, float scaleZ) {
		this(new Vector3f(posX, posY, posZ), new UnitQuaternion(axisOfRotation, angle / 2), new Vector3f(scaleX, scaleY, scaleZ));
	}

	/**
	 * The actual position of the world transformation will not be able to be set
	 * through modifying the returned position vector.
	 * 
	 * @return the position of the world transformation
	 */
	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	/**
	 * Sets the position of the transformation to the given three values.
	 * 
	 * @param new x
	 * @param new y
	 * @param new z
	 */
	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
		raiseFlag();
	}

	/**
	 * Increases the position of the transformation by the given values.
	 * 
	 * @param change in x
	 * @param change in y
	 * @param change in z
	 */
	public void increasePosition(float dx, float dy, float dz) {
		Vector3f addVector = new Vector3f(dx, dy, dz);
		position.set(Vector3f.add(position, addVector));
		raiseFlag();
	}

	/**
	 * Getter for the rotation quaternion.
	 * 
	 * @return the rotation quaternion
	 */
	public UnitQuaternion getQuaternion() {
		return quaternion;
	}

	public void setRotation(Vector3f axisOfRotation, float angle) {
		quaternion.setAngleAxis(axisOfRotation, angle / 2);
		raiseFlag();
	}

	public void increaseRotation(Vector3f axisOfRotation, float angle) {
		quaternion.applyRotation(axisOfRotation, angle);
		raiseFlag();
	}

	public void increaseRotation(UnitQuaternion rotation) {
		quaternion.applyRotation(rotation);
		raiseFlag();
	}

	public Vector3f getScale() {
		return scale;
	}

	@Override
	protected void calculateMatrix() {
		matrix = new Matrix4f();
		Matrix4f.translate(position, matrix, matrix);
		matrix = Matrix4f.multiply(matrix, quaternion.toRotationMatrix());
		Matrix4f.scale(scale, matrix, matrix);
	}

	/**
	 * The second transformation should be local to the first. Think of t1 as a
	 * globe's transformation while t2 is a transformation of a person relative to
	 * the globe.
	 * 
	 * @param the first transformation
	 * @param the second transformation
	 * @return the combined transformation
	 */
	public static WorldTransformation getCombinedTransformation(WorldTransformation t1, WorldTransformation t2) {
		UnitQuaternion combinedQuaternion = t2.getQuaternion().multiply(t1.getQuaternion()); // Rotate the quaternion of t2 by the quaternion of t1
		Vector3f rotatedPosition = t1.getQuaternion().rotateVector3f(t2.position); // Rotate the offset using t1's quaternion
		WorldTransformation combinedTransformation = new WorldTransformation(rotatedPosition, combinedQuaternion, t2.getScale()); // Create the combined transformation
		return combinedTransformation; // Return it
	}

}
