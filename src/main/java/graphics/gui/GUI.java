package graphics.gui;

import graphics.Visual;
import graphics.texture.Texture;
import graphics.transformation.WorldTransformation;
import graphics.vao.VAO;
import math.Matrix4f;

public class GUI {

	private static int guiVaoID;
	private Texture texture;

	private WorldTransformation worldTransformation;

	static {
		VAO guiVAO = new VAO();
		guiVAO.interpret(new GUIMeshData());
		guiVaoID = guiVAO.getVaoId();
	}

	protected GUI() {}

	protected void setDimensions(float posX, float posY, float width, float height) {
		worldTransformation = new WorldTransformation(2.0f * posX / Visual.getWindowWidth() - 1.0f, 1.0f - 2.0f * posY / Visual.getWindowHeight(), 0, 0, 0, 0, 2.0f * width / Visual.getWindowWidth(), 2.0f * height / Visual.getWindowHeight(), 1);
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

	public static int getGuiVaoID() {
		return guiVaoID;
	}

}
