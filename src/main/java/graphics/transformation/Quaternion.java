package graphics.transformation;

import math.Matrix4f;
import math.Vector3f;

public class Quaternion {

	private float w;
	private float x;
	private float y;
	private float z;

	/**
	 * Creates an empty quaternion with w = 1 and x, y, z = 0.
	 */
	public Quaternion() {
		this.w = 1;
		this.w = 0;
		this.w = 0;
		this.w = 0;
	}

	/**
	 * Creates an quaternion with axis and angle as specified.
	 * 
	 */
	public Quaternion(Vector3f axis, float theta) {
		setAngleAxis(axis, theta);
	}

	/**
	 * Creates an quaternion with components as specified.
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 */
	public Quaternion(float w, float x, float y, float z) {
		setComponents(w, x, y, z);
	}

	/**
	 * Sets this quaternion through individual components.
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @return this quaternion
	 */
	public Quaternion setComponents(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
		normalize();
		return this;
	}

	/**
	 * Sets this quaternion by an angle of rotation about an axis.
	 * 
	 * @param theta
	 * @param axisX
	 * @param axisY
	 * @param axisZ
	 * @return this quaternion
	 */
	public Quaternion setAngleAxis(float axisX, float axisY, float axisZ, float theta) {
		double angle = Math.toRadians(theta);
		this.w = (float) Math.cos(angle);
		Vector3f axis = new Vector3f(axisX, axisY, axisZ);
		axis.normalise();
		axis.scale((float) Math.sin(angle));
		this.x = axis.x;
		this.y = axis.y;
		this.z = axis.z;
		return this;
	}

	/**
	 * Sets this quaternion by an angle of rotation about an axis.
	 * 
	 * @param theta
	 * @param axisX
	 * @param axisY
	 * @param axisZ
	 * @return this quaternion
	 */
	public Quaternion setAngleAxis(Vector3f axisOfRotation, float theta) {
		return setAngleAxis(axisOfRotation.x, axisOfRotation.y, axisOfRotation.z, theta);
	}

	/**
	 * Normalizes the quaternion.
	 * 
	 * @return this quaternion
	 */
	public Quaternion normalize() {
		float mag = (float) Math.sqrt(w * w + x * x + y * y + z * z);
		w /= mag;
		x /= mag;
		y /= mag;
		z /= mag;
		return this;
	}

	/**
	 * Gets the invertse of this quaternion;
	 * 
	 * @return the inverse quaternion
	 */
	public Quaternion inverse() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * Multiplies this quaternion by the parameter quaternion.
	 * 
	 * @param quaternion
	 * @return the resultant quaternion
	 */
	public Quaternion multiply(Quaternion quaternion) {
		float newW = -x * quaternion.x - y * quaternion.y - z * quaternion.z + w * quaternion.w;
		float newX = x * quaternion.w + y * quaternion.z - z * quaternion.y + w * quaternion.x;
		float newY = -x * quaternion.z + y * quaternion.w + z * quaternion.x + w * quaternion.y;
		float newZ = x * quaternion.y - y * quaternion.x + z * quaternion.w + w * quaternion.z;
		return new Quaternion(newW, newX, newY, newZ);
	}

	/**
	 * Converts the quaternion to a 4x4 matrix representing the exact same rotation
	 * as this quaternion. (The rotation is only contained in the top-left 3x3 part,
	 * but a 4x4 matrix is returned here for convenience seeing as it will be
	 * multiplied with other 4x4 matrices).
	 * 
	 * More detailed explanation here:
	 * http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToMatrix/
	 * 
	 * @return The rotation matrix which represents the exact same rotation as this
	 *         quaternion.
	 */
	public Matrix4f toRotationMatrix() {
		Matrix4f matrix = new Matrix4f();
		final float xy = x * y;
		final float xz = x * z;
		final float xw = x * w;
		final float yz = y * z;
		final float yw = y * w;
		final float zw = z * w;
		final float xSquared = x * x;
		final float ySquared = y * y;
		final float zSquared = z * z;
		matrix.m00 = 1 - 2 * (ySquared + zSquared);
		matrix.m01 = 2 * (xy - zw);
		matrix.m02 = 2 * (xz + yw);
		matrix.m03 = 0;
		matrix.m10 = 2 * (xy + zw);
		matrix.m11 = 1 - 2 * (xSquared + zSquared);
		matrix.m12 = 2 * (yz - xw);
		matrix.m13 = 0;
		matrix.m20 = 2 * (xz - yw);
		matrix.m21 = 2 * (yz + xw);
		matrix.m22 = 1 - 2 * (xSquared + ySquared);
		matrix.m23 = 0;
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		matrix.m33 = 1;
		return matrix;
	}

	/**
	 * Extracts the rotation part of a transformation matrix and converts it to a
	 * quaternion using the magic of maths.
	 * 
	 * More detailed explanation here:
	 * http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToQuaternion/index.htm
	 * 
	 * @param matrix - the transformation matrix containing the rotation which this
	 *               quaternion shall represent.
	 */
	public static Quaternion fromMatrix(Matrix4f matrix) {
		float w, x, y, z;
		float diagonal = matrix.m00 + matrix.m11 + matrix.m22;
		if (diagonal > 0) {
			float w4 = (float) (Math.sqrt(diagonal + 1f) * 2f);
			w = w4 / 4f;
			x = (matrix.m21 - matrix.m12) / w4;
			y = (matrix.m02 - matrix.m20) / w4;
			z = (matrix.m10 - matrix.m01) / w4;
		} else if ((matrix.m00 > matrix.m11) && (matrix.m00 > matrix.m22)) {
			float x4 = (float) (Math.sqrt(1f + matrix.m00 - matrix.m11 - matrix.m22) * 2f);
			w = (matrix.m21 - matrix.m12) / x4;
			x = x4 / 4f;
			y = (matrix.m01 + matrix.m10) / x4;
			z = (matrix.m02 + matrix.m20) / x4;
		} else if (matrix.m11 > matrix.m22) {
			float y4 = (float) (Math.sqrt(1f + matrix.m11 - matrix.m00 - matrix.m22) * 2f);
			w = (matrix.m02 - matrix.m20) / y4;
			x = (matrix.m01 + matrix.m10) / y4;
			y = y4 / 4f;
			z = (matrix.m12 + matrix.m21) / y4;
		} else {
			float z4 = (float) (Math.sqrt(1f + matrix.m22 - matrix.m00 - matrix.m11) * 2f);
			w = (matrix.m10 - matrix.m01) / z4;
			x = (matrix.m02 + matrix.m20) / z4;
			y = (matrix.m12 + matrix.m21) / z4;
			z = z4 / 4f;
		}
		return new Quaternion(w, x, y, z);
	}

	/**
	 * Interpolates between two quaternion rotations and returns the resulting
	 * quaternion rotation. The interpolation method here is "nlerp", or
	 * "normalized-lerp". Another mnethod that could be used is "slerp", and you can
	 * see a comparison of the methods here:
	 * https://keithmaggio.wordpress.com/2011/02/15/math-magician-lerp-slerp-and-nlerp/
	 * 
	 * and here:
	 * http://number-none.com/product/Understanding%20Slerp,%20Then%20Not%20Using%20It/
	 * 
	 * @param a
	 * @param b
	 * @param blend - a value between 0 and 1 indicating how far to interpolate
	 *              between the two quaternions.
	 * @return The resulting interpolated rotation in quaternion format.
	 */
	public static Quaternion interpolate(Quaternion a, Quaternion b, float blend) {
		Quaternion result = new Quaternion(1, 0, 0, 0);
		float dot = a.w * b.w + a.x * b.x + a.y * b.y + a.z * b.z;
		float blendI = 1f - blend;
		if (dot < 0) {
			result.w = blendI * a.w + blend * -b.w;
			result.x = blendI * a.x + blend * -b.x;
			result.y = blendI * a.y + blend * -b.y;
			result.z = blendI * a.z + blend * -b.z;
		} else {
			result.w = blendI * a.w + blend * b.w;
			result.x = blendI * a.x + blend * b.x;
			result.y = blendI * a.y + blend * b.y;
			result.z = blendI * a.z + blend * b.z;
		}
		result.normalize();
		return result;
	}

}
