package graphics.light.lightRegulator;

import graphics.light.Light;

public abstract class LightRegulator {

	private Light light;
	protected final float LENGTH_OF_A_DAY = 1000f;

	public LightRegulator(Light light) {
		this.light = light;
	}

	public Light getLight() {
		return light;
	}

	public abstract void update();

}