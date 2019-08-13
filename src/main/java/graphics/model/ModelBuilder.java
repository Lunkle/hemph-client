package graphics.model;

import graphics.texture.Texture;
import graphics.vao.VAO;

public class ModelBuilder {

	private Model model;

	private ModelBuilder() {
		model = new Model();
	}

	public static ModelBuilder newInstance() {
		return new ModelBuilder();
	}

	public ModelBuilder setMesh(VAO mesh) {
		model.setMesh(mesh);
		return this;
	}

	public ModelBuilder setDiffuseTexture(Texture diffuseTexture) {
		model.setDiffuseTexture(diffuseTexture);
		return this;
	}

	public ModelBuilder setSpecularTexture(Texture specularTexture) {
		model.setSpecularTexture(specularTexture);
		return this;
	}

	public Model create() {
		return model;
	}

}
