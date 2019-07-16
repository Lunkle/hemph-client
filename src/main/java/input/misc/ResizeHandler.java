package input.misc;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

import game.Visual;

public class ResizeHandler extends GLFWFramebufferSizeCallback {

	@Override
	public void invoke(long windowID, int width, int height) {
		Visual.setWindowWidth(width);
		Visual.setWindowHeight(height);
	}

}
