package graphics.texture;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import loading.skybox.SkyboxTextureRawData;

public class SkyboxTexture extends Texture {

	public void setAsFirstTexture() {
		setTextureUnit(GL13.GL_TEXTURE0);
	}

	public void setAsSecondTexture() {
		setTextureUnit(GL13.GL_TEXTURE1);
	}

	@Override
	public void interpret(SkyboxTextureRawData data) {
		setTextureID(GL11.glGenTextures()); // Create a new OpenGL texture
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, getTextureID());
		int[] widths = data.getWidths();
		int[] heights = data.getHeights();
		ByteBuffer[] buffers = data.getByteBuffers();
		for (int i = 0; i < 6; i++) {
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, widths[i], heights[i], 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffers[i]);
		}
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL13.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL13.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL13.GL_TEXTURE_WRAP_R, GL12.GL_CLAMP_TO_EDGE);
		addTexture(this);
	}

}
