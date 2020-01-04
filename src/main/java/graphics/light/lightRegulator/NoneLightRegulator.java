package graphics.light.lightRegulator;

import graphics.light.Light;

public class NoneLightRegulator extends LightRegulator {

	public NoneLightRegulator(Light light) {
		super(light);
	}

	@Override
	public void update() {}

}
