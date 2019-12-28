package graphics.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import loading.texture.ByteBufferImageRawData;

public class ModelTexture extends Texture {

	public void setAsDiffuseTexture() {
		setTextureUnit(GL13.GL_TEXTURE0);
	}

	public void setAsSpecularTexture() {
		setTextureUnit(GL13.GL_TEXTURE1);
	}

	@Override
	public void interpret(ByteBufferImageRawData data) {
		setTextureID(GL11.glGenTextures()); // Create a new OpenGL texture
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTextureID());
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, data.getImageWidth(), data.getImageHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getImageBuffer()); // Upload the texture data
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D); // Generate Mipmap
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL15.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL15.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		data.freeBuffer();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		addTexture(this);
	}
}
