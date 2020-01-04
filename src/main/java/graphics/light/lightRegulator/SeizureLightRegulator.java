package graphics.light.lightRegulator;

import org.lwjgl.glfw.GLFW;

import graphics.light.Light;

public class SeizureLightRegulator extends LightRegulator {

	public SeizureLightRegulator(Light light) {
		super(light);
	}

	@Override
	public void update() {
		if (GLFW.glfwGetTime() % 2 < 1) {
			getLight().setStrength(300);
		} else {
			getLight().setStrength(0);
		}
	}

}
