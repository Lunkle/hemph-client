package graphics.model;

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

	public VAOBuilder addTextureCoordinates(float[] textureCoordinates) {
		vao.attachVBO(1, 2, textureCoordinates);
		return this;
	}

	public VAOBuilder addNormals(float[] normals) {
		vao.attachVBO(2, 3, normals);
		return this;
	}

	public VAOBuilder addIndices(int[] indices) {
		vao.attachEBO(indices);
		return this;
	}

	public VAO create() {
		vao.unbindVAO();
		return vao;
	}

}
