package graphics.gui;

import graphics.loader.InterpretedData;
import graphics.loader.RawData;

public class GUIMeshData implements RawData {

	// The quad is going to be the same for every GUI
	private final float[] QUAD_VERTICES = { 0, 0, 0, -1, 1, 0, 1, -1 };

	@Override
	public void load(String filePath) {
		System.out.println("NO, please man");
	}

	@Override
	public void accept(InterpretedData data) {
		data.interpret(this);
	}

	public float[] getQuadVertices() {
		return QUAD_VERTICES;
	}

}
