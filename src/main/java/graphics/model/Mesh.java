package graphics.model;

public class Mesh {

	private final VAO vao;

	public Mesh(float[] positions, int[] indices) {
		vao = VAOBuilder.newInstance().addIndices(indices).addPositions(positions).create();
	}

	public int getVaoId() {
		return vao.getVaoID();
	}

	public int getVertexCount() {
		return vao.vertexCount;
	}

}
