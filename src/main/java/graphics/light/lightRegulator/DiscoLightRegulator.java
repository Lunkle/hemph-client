package graphics.light.lightRegulator;

import org.lwjgl.glfw.GLFW;

import graphics.light.Light;

public class DiscoLightRegulator extends LightRegulator {

	private static final float TIME_UNTIL_CHANGE_COLOUR = 2f;
	private double lastTime = 0;
	private double timeSinceLastUpdate = 0;

	public DiscoLightRegulator(Light light) {
		super(light);
		light.setStrength(1);
		lastTime = GLFW.glfwGetTime();
	}

	@Override
	public void update() {
		double currentTime = GLFW.glfwGetTime();
		timeSinceLastUpdate += currentTime - lastTime;
		if (timeSinceLastUpdate > TIME_UNTIL_CHANGE_COLOUR) {
			setNewColour();
			timeSinceLastUpdate = timeSinceLastUpdate % TIME_UNTIL_CHANGE_COLOUR;
		}
		lastTime = currentTime;
	}

	public void setNewColour() {
		float red = (float) Math.random();
		float green = (float) Math.random();
		float blue = (float) Math.random();
		getLight().setDiffuse(red, green, blue);
		getLight().setAmbient(red, green, blue);
		getLight().setSpecular(red, green, blue);
	}

}
