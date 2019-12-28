package loading.skybox;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import graphics.texture.SkyboxTexture;
import loading.loader.InterpretedData;
import loading.loader.RawData;

public class SkyboxTextureRawData implements RawData {

	private static final String[] FACE_ORDER = new String[] { "Right", "Left", "Top", "Bottom", "Back", "Front" };;

	private int[] widths = new int[6];
	private int[] heights = new int[6];
	private ByteBuffer[] byteBuffers = new ByteBuffer[6];

	@Override
	public void load(String filePath) {
		for (int i = 0; i < 6; i++) {
			try (MemoryStack stack = MemoryStack.stackPush()) {
				IntBuffer w = stack.mallocInt(1); // Buffer to store width
				IntBuffer h = stack.mallocInt(1); // Buffer to store height
				IntBuffer comp = stack.mallocInt(1); // Buffer to store number of components
				byteBuffers[i] = STBImage.stbi_load("src/main/resources/skybox/" + filePath + FACE_ORDER[i] + ".png", w, h, comp, 4); // Load the image
				if (byteBuffers[i] == null) {
					throw new RuntimeException("Failed to load image " + filePath + FACE_ORDER[i] + ".png " + System.lineSeparator() + STBImage.stbi_failure_reason()); // Print problem if error occurs
				}
				widths[i] = w.get();
				heights[i] = h.get();
			}
		}
	}

	public int[] getWidths() {
		return widths;
	}

	public int[] getHeights() {
		return heights;
	}

	public ByteBuffer[] getByteBuffers() {
		return byteBuffers;
	}

	@Override
	public void accept(InterpretedData data) {
		data.interpret(this);
	}

	@Override
	public InterpretedData newInterpretedData() {
		return new SkyboxTexture();
	}

	@Override
	public long getSize(String filePath) {
		return 6000000;
	}

}
