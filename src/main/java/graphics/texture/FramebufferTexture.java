package graphics.texture;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import loading.framebuffer.EmptyTextureRawData;

public class FramebufferTexture extends Texture {

	@Override
	public void interpret(EmptyTextureRawData data) {
		setTextureID(GL11.glGenTextures()); // Create a new OpenGL texture
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTextureID());
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, data.getInternalFormat(), data.getTextureWidth(), data.getTextureHeight(), 0, data.getFormat(), data.getType(), (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		addTexture(this);
	}

}
