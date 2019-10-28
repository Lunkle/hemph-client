package graphics.light;

import math.Vector3f;

public class PointLight extends Light {

	private Vector3f position;
	private Vector3f constants;

	public PointLight(float posX, float posY, float posZ) {
		super();
		position = new Vector3f(posX, posY, posZ);
		constants = new Vector3f(0, 0, 1);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(float posX, float posY, float posZ) {
		position.set(posX, posY, posZ);
	}

	public Vector3f getConstants() {
		return constants;
	}

	public void setQuadratic(float quadratic) {
		constants.setX(quadratic);
	}

	public void setLinear(float linear) {
		constants.setY(linear);
	}

	public void setConstant(float constant) {
		constants.setZ(constant);
	}

	/**
	 * 
	 * Setting the attenuation variables.
	 * 
	 * @param quadratic
	 * @param linear
	 * @param constant
	 */
	public void setConstants(float quadratic, float linear, float constant) {
		constants.set(quadratic, linear, constant);
	}

}
