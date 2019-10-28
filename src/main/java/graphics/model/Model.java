package graphics.model;

import graphics.texture.Texture;
import graphics.vao.VAO;

public class Model {

	private VAO mesh;
	private Texture diffuseTexture;
	private Texture specularTexture;

	public VAO getMesh() {
		return mesh;
	}

	protected void setMesh(VAO mesh) {
		this.mesh = mesh;
	}

	protected void setDiffuseTexture(Texture diffuseTexture) {
		diffuseTexture.setAsDiffuseTexture();
		this.diffuseTexture = diffuseTexture;
	}

	protected void setSpecularTexture(Texture specularTexture) {
		specularTexture.setAsSpecularTexture();
		this.specularTexture = specularTexture;
	}

	/**
	 * Activates all the textures.
	 */
	public void activateTextures() {
		diffuseTexture.activateTexture();
		specularTexture.activateTexture();
	}

}
