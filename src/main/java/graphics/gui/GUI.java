package graphics.gui;

import graphics.texture.Texture;
import graphics.transformation.WorldTransformation;
import math.Matrix4f;
import math.Vector3f;

public class GUI {

	private Texture texture;
	private WorldTransformation worldTransformation;

	protected GUI() {}

	protected void setDimensions(float posX, float posY, float width, float height) {
		worldTransformation = new WorldTransformation(2.0f * posX - 1.0f, 1.0f - 2.0f * posY, 0, new Vector3f(0, 1, 0), 0, 2.0f * width, 2.0f * height, 1);
	}

	public Matrix4f getTransformationMatrix() {
		return worldTransformation.getMatrix();
	}

	protected void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getTextureID() {
		if (texture != null) {
			return texture.getID();
		}
		return -1;
	}

	public void activateTextures() {
		texture.activateTexture();
	}

}
