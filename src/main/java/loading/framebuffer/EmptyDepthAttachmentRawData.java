package loading.framebuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import graphics.texture.FramebufferTexture;
import loading.loader.InterpretedData;

public class EmptyDepthAttachmentRawData extends EmptyTextureRawData {

	public EmptyDepthAttachmentRawData(int textureWidth, int textureHeight) {
		super(textureWidth, textureHeight);
	}

	@Override
	public int getInternalFormat() {
		return GL14.GL_DEPTH_COMPONENT32;
	}

	@Override
	public int getFormat() {
		return GL11.GL_DEPTH_COMPONENT;
	}

	@Override
	public int getType() {
		return GL11.GL_FLOAT;
	}

	@Override
	public InterpretedData newInterpretedData() {
		return new FramebufferTexture();
	}

}
