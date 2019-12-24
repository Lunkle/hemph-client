package loading.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import graphics.texture.Texture;
import loading.loader.InterpretedData;
import loading.loader.RawData;

public class ByteBufferImageRawData implements RawData {

	private ByteBuffer imageBuffer;
	private int imageWidth;
	private int imageHeight;

	@Override
	public void load(String filePath) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1); // Buffer to store width
			IntBuffer h = stack.mallocInt(1); // Buffer to store height
			IntBuffer comp = stack.mallocInt(1); // Buffer to store number of components
			imageBuffer = STBImage.stbi_load("src/main/resources/" + filePath, w, h, comp, 4); // Load the image
			if (imageBuffer == null) {
				System.out.println("Failed to load image at " + filePath);
				throw new RuntimeException("Failed to load image." + System.lineSeparator() + STBImage.stbi_failure_reason()); // Print problem if error occurs
			}
			imageWidth = w.get();
			imageHeight = h.get();
		}
	}

	@Override
	public InterpretedData newInterpretedData() {
		return new Texture();
	}

	public ByteBuffer getImageBuffer() {
		return imageBuffer;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void freeBuffer() {
		STBImage.stbi_image_free(imageBuffer);
	}

	@Override
	public void accept(InterpretedData data) {
		data.interpret(this);
	}

}
