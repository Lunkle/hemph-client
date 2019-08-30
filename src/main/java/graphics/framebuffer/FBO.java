package graphics.framebuffer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

import graphics.texture.Texture;

public class FBO {

	private static List<Integer> fbos = new ArrayList<>();

	protected Texture texture;

	protected int fboID;
	protected int frameWidth;
	protected int frameHeight;

	public FBO() {
		fboID = GL30.glGenFramebuffers();
	}

	public void bindFBO() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fboID);
	}

	public static void bindDefaultFBO() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	public void deleteFBO() {
		GL30.glDeleteFramebuffers(fboID);
		fbos.remove(new Integer(fboID));
	}

	public Texture getTexture() {
		return texture;
	}

	public static void cleanUp() {
		for (int fbo : fbos) {
			GL30.glDeleteFramebuffers(fbo);
		}
	}

}
