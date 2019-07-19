package input.misc;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL11;

import game.Visual;

public class ResizeHandler extends GLFWFramebufferSizeCallback {

	@Override
	public void invoke(long windowID, int width, int height) {
		Visual.setWindowWidth(width);
		Visual.setWindowHeight(height);
		GL11.glViewport(0, 0, Visual.getWindowWidth(), Visual.getWindowHeight());
	}

}
