package graphics.gui;

import graphics.texture.Texture;
import graphics.transformation.WorldTransformation;
import math.Matrix4f;
import math.Vector3f;

public class GUI {

	private static int windowWidth = 1280;
	private static int windowHeight = 760;
	private Texture texture;
	private WorldTransformation worldTransformation;

	protected GUI() {}

	public static void loadWindowDimensions(int windowWidth, int windowHeight) {
		GUI.windowWidth = windowWidth;
		GUI.windowHeight = windowHeight;
	}

	protected void setDimensions(float posX, float posY, float width, float height) {
		worldTransformation = new WorldTransformation(2.0f * posX / windowWidth - 1.0f, 1.0f - 2.0f * posY / windowHeight, 0, new Vector3f(0, 1, 0), 0, 2.0f * width / windowWidth, 2.0f * height / windowHeight, 1);
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
