package graphics.entity;

import graphics.model.TexturedModel;
import graphics.transformation.ModelTransformation;
import math.Matrix4f;

public class Entity {

	private TexturedModel model;
	private float textureXOffset;
	private float textureYOffset;
	private ModelTransformation transformation;

	public Entity(TexturedModel model, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
		transformation = new ModelTransformation(posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
	}

	public Entity(TexturedModel model, int textureIndex, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
		calculateTextureOffsets(textureIndex);
		transformation = new ModelTransformation(posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
	}

	public void increasePosition(float dx, float dy, float dz) {
		transformation.increasePosition(dx, dy, dz);
	}

	public void increaseRotation(float dx, float dy, float dz) {
		transformation.increaseRotation(dx, dy, dz);
	}

	private void calculateTextureOffsets(int textureIndex) {
		int column = textureIndex % model.getTexture().getTextureGridSize();
		textureXOffset = (float) column / (float) model.getTexture().getTextureGridSize();
		int row = textureIndex / model.getTexture().getTextureGridSize();
		textureYOffset = (float) row / (float) model.getTexture().getTextureGridSize();
	}

	public float getTextureXOffset() {
		return textureXOffset;
	}

	public float getTextureYOffset() {
		return textureYOffset;
	}

	public TexturedModel getModel() {
		return model;
	}

	public Matrix4f getModelMatrix() {
		return transformation.getMatrix();
	}

}
