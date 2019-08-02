package graphics.model;

import java.util.List;

public class VAOBuilder {

	private VAO vao;

	private VAOBuilder() {
		vao = new VAO();
	}

	public static VAOBuilder newInstance() {
		VAOBuilder vaobuilder = new VAOBuilder();
		return vaobuilder;
	}

	public VAOBuilder addPositions(int dimensions, float[] positions) {
		vao.attachVBO(0, dimensions, positions);
		return this;
	}

	public VAOBuilder addPositions(float[] positions) {
		vao.attachVBO(0, 3, positions);
		return this;
	}

	public VAOBuilder addPositions(List<Float> positions) {
		return addPositions(toFloatArray(positions));
	}

	public VAOBuilder addTextureCoordinates(float[] textureCoordinates) {
		vao.attachVBO(1, 2, textureCoordinates);
		return this;
	}

	public VAOBuilder addTextureCoordinates(List<Float> textureCoordinates) {
		return addTextureCoordinates(toFloatArray(textureCoordinates));
	}

	public VAOBuilder addNormals(float[] normals) {
		vao.attachVBO(2, 3, normals);
		return this;
	}

	public VAOBuilder addNormals(List<Float> normals) {
		return addNormals(toFloatArray(normals));
	}

	public VAOBuilder addIndices(int[] indices) {
		vao.attachEBO(indices);
		vao.vertexCount = indices.length;
		return this;
	}

	public VAOBuilder addIndices(List<Integer> indices) {
		return addIndices(toIntArray(indices));
	}

	public VAO create() {
		vao.unbindVAO();
		return vao;
	}

	public static VAO createVAO(List<Float> positions, List<Float> textureCoordinates, List<Float> normals, List<Integer> indices) {
		return createVAO(toFloatArray(positions), toFloatArray(textureCoordinates), toFloatArray(normals), toIntArray(indices));
	}

	public static VAO createVAO(float[] positions, float[] textureCoordinates, float[] normals, int[] indices) {
		return VAOBuilder.newInstance().addIndices(indices).addPositions(positions).addTextureCoordinates(textureCoordinates).addNormals(normals).create();
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
