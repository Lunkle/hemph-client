package graphics.entity;

import graphics.model.Model;
import graphics.transformation.ModelTransformation;
import math.Matrix4f;

public class Entity {

	private Model model;
	private float textureXOffset;
	private float textureYOffset;
	private ModelTransformation transformation;

	/**
	 * Initializes the entity with default transformation
	 * 
	 * @param model
	 * @param texture
	 */
	public Entity(Model model) {
		this(model, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	/**
	 * Initializes the entity with the specified transformations.
	 * 
	 * @param model
	 * @param texture
	 * @param posX
	 * @param posY
	 * @param posZ
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 */
	public Entity(Model model, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
		this.model = model;
		transformation = new ModelTransformation(posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
	}

	/**
	 * Used to set the texture index if applicable
	 * 
	 * @param textureIndex
	 */
	public void setTextureIndex(int textureIndex) {
//		int column = textureIndex % model.getTexture().getTextureGridSize();
//		textureXOffset = column / (float) model.getTexture().getTextureGridSize();
//		int row = textureIndex / model.getTexture().getTextureGridSize();
//		textureYOffset = row / (float) model.getTexture().getTextureGridSize();
	}

	public void increasePosition(float dx, float dy, float dz) {
		transformation.increasePosition(dx, dy, dz);
	}

	public void increaseRotation(float dx, float dy, float dz) {
		transformation.increaseRotation(dx, dy, dz);
	}

	public float getTextureXOffset() {
		return textureXOffset;
	}

	public float getTextureYOffset() {
		return textureYOffset;
	}

	public Model getModel() {
		return model;
	}

	public Matrix4f getModelMatrix() {
		return transformation.getMatrix();
	}

}
