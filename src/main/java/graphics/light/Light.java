package graphics.light;

import math.Vector3f;

public abstract class Light {

	private float ambientRed;
	private float ambientGreen;
	private float ambientBlue;
	private float diffuseRed;
	private float diffuseGreen;
	private float diffuseBlue;
	private float specularRed;
	private float specularGreen;
	private float specularBlue;
	private float strength;

	public Light() {
		setAmbient(0, 0, 0);
		setDiffuse(0, 0, 0);
		setSpecular(0, 0, 0);
		setStrength(1);
	}

	public Vector3f getAmbient() {
		return new Vector3f(ambientRed, ambientGreen, ambientBlue);
	}

	public void setAmbient(float red, float green, float blue) {
		ambientRed = red;
		ambientGreen = green;
		ambientBlue = blue;
	}

	public Vector3f getDiffuse() {
		return new Vector3f(diffuseRed, diffuseGreen, diffuseBlue);
	}

	public void setDiffuse(float red, float green, float blue) {
		diffuseRed = red;
		diffuseGreen = green;
		diffuseBlue = blue;
	}

	public Vector3f getSpecular() {
		return new Vector3f(specularRed, specularGreen, specularBlue);
	}

	public void setSpecular(float red, float green, float blue) {
		specularRed = red;
		specularGreen = green;
		specularBlue = blue;
	}

	public float getStrength() {
		return strength;
	}

	public void setStrength(float strength) {
		this.strength = strength;
	}

}
