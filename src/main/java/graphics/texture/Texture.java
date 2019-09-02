package graphics.texture;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import graphics.loader.InterpretedData;
import graphics.postProcessing.EmptyTextureRawData;

public class Texture implements InterpretedData {

	private static List<Integer> textures = new ArrayList<>();

	private int textureID;
	private int textureUnit;

	public void setImage(ByteBufferImageRawData rawImage) {
		interpret(rawImage); // Interpret the image data
	}

	public void setAsDiffuseTexture() {
		textureUnit = GL13.GL_TEXTURE0;
	}

	public void setAsSpecularTexture() {
		textureUnit = GL13.GL_TEXTURE1;
	}

	public void activateTexture() {
		GL13.glActiveTexture(textureUnit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}

	/**
	 * Gets the handle of the texture
	 */
	public int getID() {
		return textureID;
	}

	@Override
	public void interpret(ByteBufferImageRawData data) {
		textureID = GL11.glGenTextures(); // Create a new OpenGL texture
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, data.getImageWidth(), data.getImageHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getImageBuffer()); // Upload the texture data
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D); // Generate Mipmap
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL15.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL15.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		data.freeBuffer();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		textures.add(textureID); // Add to list to be deleted when the program is done
	}

	@Override
	public void interpret(EmptyTextureRawData data) {
		textureID = GL11.glGenTextures(); // Create a new OpenGL texture
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, data.getInternalFormat(), data.getTextureWidth(), data.getTextureHeight(), 0, data.getFormat(), data.getType(), (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		textures.add(textureID); // Add to list to be deleted when the program is done
	}

	public static void cleanup() {
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}

}
