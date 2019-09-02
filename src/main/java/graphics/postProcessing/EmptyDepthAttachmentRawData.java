package graphics.postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

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

}
