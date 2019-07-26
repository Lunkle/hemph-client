package graphics.model;

public class Model {

	private VAO mesh;
	private Texture texture;

	public Model(VAO mesh, Texture texture) {
		this.mesh = mesh;
		this.texture = texture;
	}

	public VAO getMesh() {
		return mesh;
	}

	public int getTextureID() {
		return texture.getID();
	}

}
