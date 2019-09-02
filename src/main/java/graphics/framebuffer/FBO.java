package graphics.framebuffer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import graphics.postProcessing.EmptyColourAttachmentRawData;
import graphics.postProcessing.EmptyDepthAttachmentRawData;
import graphics.postProcessing.EmptyTextureRawData;
import graphics.texture.Texture;

public class FBO {

	private static List<Integer> fbos = new ArrayList<>();

	private Texture colourTexture;
	private Texture depthTexture;

	private int fboID;
	private int frameWidth;
	private int frameHeight;

	public FBO(int frameWidth, int frameHeight) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		fboID = GL30.glGenFramebuffers();
		bindFBO();
		attachColourTexture();
		attachDepthTexture();
		bindDefaultFBO();
	}

	private void attachColourTexture() {
		colourTexture = new Texture();
		EmptyTextureRawData emptyColourAttachmentRawData = new EmptyColourAttachmentRawData(getFrameWidth(), getFrameHeight());
		colourTexture.interpret(emptyColourAttachmentRawData);
		GL32.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, colourTexture.getID(), 0);
	}

	private void attachDepthTexture() {
		depthTexture = new Texture();
		EmptyTextureRawData emptyDepthAttachmentRawData = new EmptyDepthAttachmentRawData(getFrameWidth(), getFrameHeight());
		depthTexture.interpret(emptyDepthAttachmentRawData);
		GL32.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthTexture.getID(), 0);
	}

	public void bindFBO() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fboID);
	}

	public static void bindDefaultFBO() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public Texture getColourTexture() {
		return colourTexture;
	}

	public Texture getDepthTexture() {
		return depthTexture;
	}

	public void deleteFBO() {
		GL30.glDeleteFramebuffers(fboID);
		fbos.remove(new Integer(fboID));
	}

	public static void cleanUp() {
		for (int fbo : fbos) {
			GL30.glDeleteFramebuffers(fbo);
		}
	}

}
