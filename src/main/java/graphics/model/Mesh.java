package graphics.model;

import java.util.List;

public class Mesh {

	private VAO vao;
	private int vertexCount;

	private int[] indices;
	private float[] positions;
	private float[] textureCoordinates;
	private float[] normals;

	public Mesh(int[] indices, float[] positions, float[] textureCoordinates, float[] normals) {
		this.indices = indices;
		this.positions = positions;
		this.textureCoordinates = textureCoordinates;
		this.normals = normals;
		vertexCount = indices.length;
		setupMesh();
	}

	public Mesh(List<Integer> indices, List<Float> positions, List<Float> textureCoordinates, List<Float> normals) {
		this(toIntArray(indices), toFloatArray(positions), toFloatArray(textureCoordinates), toFloatArray(normals));
	}

	private void setupMesh() {
		vao = VAOBuilder.newInstance().addIndices(indices).addPositions(positions).addTextureCoordinates(textureCoordinates).addNormals(normals).create();
	}

	public int getVAOID() {
		return vao.getVaoID();
	}

	public int getVertexCount() {
		return vertexCount;
	}

	private static int[] toIntArray(List<Integer> data) {
		int numberOfElements = data.size();
		int[] dataArray = new int[numberOfElements];
		for (int i = 0; i < numberOfElements; i++) {
			dataArray[i] = data.get(i);
		}
		return dataArray;
	}

	private static float[] toFloatArray(List<Float> data) {
		int numberOfElements = data.size();
		float[] dataArray = new float[numberOfElements];
		for (int i = 0; i < numberOfElements; i++) {
			dataArray[i] = data.get(i);
		}
		return dataArray;
	}

}
