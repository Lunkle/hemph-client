package graphics.model;

import java.util.ArrayList;
import java.util.List;

public class Model {

	private List<Mesh> meshes;
	private Texture texture;

	public Model(Texture texture) {
		meshes = new ArrayList<>();
		this.texture = texture;
	}

	public void addMesh(Mesh mesh) {
		meshes.add(mesh);
	}

	public List<Mesh> getMeshes() {
		return meshes;
	}

	public int getTextureID() {
		return texture.getID();
	}

}
