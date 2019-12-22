package loading.screen;

import graphics.vao.VAO;
import loading.loader.InterpretedData;
import loading.loader.RawData;

public class ScreenRawMeshData implements RawData {

	// The quad is going to be the same for every GUI
	private static final float[] QUAD_VERTICES = { 0, 0, 0, -1, 1, 0, 1, -1 };

	@Override
	public void load(String filePath) {
		System.out.println("GUI quad data should not need to be loaded");
	}

	@Override
	public void accept(InterpretedData data) {
		data.interpret(this);
	}

	public float[] getQuadVertices() {
		return QUAD_VERTICES;
	}

	@Override
	public InterpretedData newInterpretedData() {
		return new VAO();
	}

}
