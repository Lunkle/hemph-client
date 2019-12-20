package loading.screen;

public class ScreenQuadRawMeshData extends ScreenRawMeshData {

	private static final float[] QUAD_VERTICES = { -1, 1, -1, -1, 1, 1, 1, -1 };

	@Override
	public float[] getQuadVertices() {
		return QUAD_VERTICES;
	}

}
