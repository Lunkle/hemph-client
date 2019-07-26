package graphics.gui;

import game.Visual;
import graphics.model.Texture;
import graphics.model.VAOBuilder;
import graphics.transformation.WorldTransformation;
import graphics.transformation.Transformation;

public class GUI {

	// The quad is static because it is going to be the same for every GUI
	private static final float[] QUAD_VERTICES = { 0, 0, 0, -1, 1, 0, 1, -1 };
	private static int guiVaoID;
	private Texture texture;

	private WorldTransformation worldTransformation;

	static {
		guiVaoID = VAOBuilder.newInstance().addPositions(2, QUAD_VERTICES).create().getVaoId();
	}

	protected GUI() {
	}

	protected void setDimensions(float posX, float posY, float width, float height) {
		worldTransformation = new WorldTransformation(2.0f * posX / Visual.getWindowWidth() - 1.0f, 1.0f - 2.0f * posY / Visual.getWindowHeight(), 0, 0, 0, 0, 2.0f * width / Visual.getWindowWidth(), 2.0f * height / Visual.getWindowHeight(), 1);
	}

	public Transformation getWorldTransformation() {
		return worldTransformation;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getTextureID() {
		if (texture != null) {
			return texture.getID();
		}
		return -1;
	}

	public static int getGuiVaoID() {
		return guiVaoID;
	}

}
