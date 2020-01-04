package graphics.light.lightRegulator;

import graphics.light.Light;

public class LightRegulatorFactory {

	public LightRegulator getLightRegulator(Light light, LightRegulatorType type) {
		if (type == LightRegulatorType.SEIZURE) {
			return new SeizureLightRegulator(light);
		} else if (type == LightRegulatorType.NATURAL) {
			return new NaturalLightRegulator(light);
		} else if (type == LightRegulatorType.DISCO) {
			return new DiscoLightRegulator(light);
		} else if (type == LightRegulatorType.NONE) {
			return new NoneLightRegulator(light);
		}

		return null;
	}

}
