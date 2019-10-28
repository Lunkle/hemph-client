package graphics.gui;

import graphics.texture.Texture;
import graphics.transformation.WorldTransformation;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;

public class GUI {

	private Texture texture;
	private WorldTransformation worldTransformation;

	protected GUI() {}

	/**
	 * Takes in dimesnions in normalized device coordinates.
	 * 
	 * @param posX   The x-position of the gui
	 * @param posY   The y-position of the gui
	 * @param width  The width of the gui
	 * @param height The height of the gui
	 */
	protected void setDimensions(float posX, float posY, float width, float height) {
		worldTransformation = new WorldTransformation(2.0f * posX - 1.0f, 1.0f - 2.0f * posY, 0, new Vector3f(0, 1, 0), 0, 2.0f * width, 2.0f * height, 1);
	}

	/**
	 * Returns the position of the gui in normalized device coordinates.
	 * 
	 * @return The position of the gui stored in a Vector2f
	 */
	public Vector2f getPosition() {
		Vector3f position = worldTransformation.getPosition();
		return new Vector2f((position.x + 1.0f) / 2.0f, (1.0f - position.y) / 2.0f);
	}

	/**
	 * Returns the size of the gui in normalized device coordinates.
	 * 
	 * @return The size of the gui stored in a Vector2f
	 */
	public Vector2f getSize() {
		Vector3f scale = worldTransformation.getScale();
		return new Vector2f(scale.x / 2.0f, scale.y / 2.0f);
	}

	/**
	 * Returns the position of the center of the gui in normalized device
	 * coordinates.
	 * 
	 * @return The position of the center of the gui stored in a Vector2f
	 */
	public Vector2f getCenterPosition() {
		Vector2f size = getSize();
		size.scale(0.5f);
		Vector2f position = getPosition();
		Vector2f center = new Vector2f(position.x + size.x, position.y + size.y);
		return center;
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
