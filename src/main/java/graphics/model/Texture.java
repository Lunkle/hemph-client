package graphics.model;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Texture {

	private static List<Integer> textures = new ArrayList<>();

	private int textureID;
	private int textureWidth;
	private int textureHeight;

	public Texture(String fileName) {
		textureID = GL11.glGenTextures(); // Create a new OpenGL texture
		loadTexture(fileName); // Loads the file into opengl
		textures.add(textureID); // Add to list to be deleted when the program is done
	}

	/**
	 * Gets the handle of the texture
	 */
	public int getID() {
		return textureID;
	}

	/**
	 * Loads a png texture from src/main/resources/textures
	 */
	private void loadTexture(String fileName) {
		ByteBuffer imageBuffer;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1); // Buffer to store width
			IntBuffer h = stack.mallocInt(1); // Buffer to store height
			IntBuffer comp = stack.mallocInt(1); // Buffer to store number of components
			STBImage.stbi_set_flip_vertically_on_load(true); // Flip the image vertically
			imageBuffer = STBImage.stbi_load("src/main/resources/" + fileName + ".png", w, h, comp, 4); // Load the image
			if (imageBuffer == null) {
				throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + STBImage.stbi_failure_reason()); // Print problem if error occurs
			}
			textureWidth = w.get();
			textureHeight = h.get();
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureWidth, textureHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageBuffer); // Upload the texture data
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D); // Generate Mipmap
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL15.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL15.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		STBImage.stbi_image_free(imageBuffer);
	}

	public static void cleanup() {
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}
}
