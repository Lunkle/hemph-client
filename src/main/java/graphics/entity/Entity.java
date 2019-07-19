package graphics.entity;

import graphics.model.Mesh;
import graphics.transformation.ModelTransformation;
import math.Matrix4f;

public class Entity {

	private Mesh mesh;
	private float textureXOffset;
	private float textureYOffset;
	private ModelTransformation transformation;

	public Entity(Mesh mesh, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
		init(mesh, posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
	}

	public Entity(Mesh mesh, int textureIndex, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
		calculateTextureOffsets(textureIndex);
		init(mesh, posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
	}

	private void init(Mesh mesh, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
		this.mesh = mesh;
		transformation = new ModelTransformation(posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
	}

	public void increasePosition(float dx, float dy, float dz) {
		transformation.increasePosition(dx, dy, dz);
	}

	public void increaseRotation(float dx, float dy, float dz) {
		transformation.increaseRotation(dx, dy, dz);
	}

	private void calculateTextureOffsets(int textureIndex) {
//		int column = textureIndex % model.getTexture().getTextureGridSize();
//		textureXOffset = column / (float) model.getTexture().getTextureGridSize();
//		int row = textureIndex / model.getTexture().getTextureGridSize();
//		textureYOffset = row / (float) model.getTexture().getTextureGridSize();
	}

	public float getTextureXOffset() {
		return textureXOffset;
	}

	public float getTextureYOffset() {
		return textureYOffset;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public Matrix4f getModelMatrix() {
		return transformation.getMatrix();
	}

}
