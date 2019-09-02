package graphics.entity;

import graphics.model.Model;
import graphics.transformation.UnitQuaternion;
import graphics.transformation.WorldTransformation;
import math.Matrix4f;
import math.Vector3f;

public class Entity {

	private Model model;
	private WorldTransformation worldTransformation;

	/**
	 * Initializes the entity with default transformation
	 * 
	 * @param model
	 * @param texture
	 */
	public Entity(Model model) {
		this(model, 0, 0, 0, new Vector3f(0, 1, 0), 0, 0, 0, 0);
	}

	/**
	 * Initializes the entity with the specified transformations.
	 * 
	 * @param model
	 * @param posX
	 * @param posY
	 * @param posZ
	 * @param axis  of rotation
	 * @param angle about the axis of rotation
	 * @param scale in the x direction
	 * @param scale in the y direction
	 * @param scale in the z direction
	 */
	public Entity(Model model, float posX, float posY, float posZ, Vector3f axis, float angle, float scaleX, float scaleY, float scaleZ) {
		this.model = model;
		worldTransformation = new WorldTransformation(posX, posY, posZ, axis, angle, scaleX, scaleY, scaleZ);
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

	public WorldTransformation getWorldTransformation() {
		return worldTransformation;
	}

	public void increasePosition(float dx, float dy, float dz) {
		worldTransformation.increasePosition(dx, dy, dz);
	}

	public void setRotation(Vector3f axis, float angle) {
		worldTransformation.setRotation(axis, angle);
	}

	public void increaseRotation(Vector3f axis, float angle) {
		worldTransformation.increaseRotation(axis, angle);
	}

	public void increaseRotation(UnitQuaternion rotation) {
		worldTransformation.increaseRotation(rotation);
	}

	public float getScaleX() {
		return worldTransformation.getScaleX();
	}

	public float getScaleY() {
		return worldTransformation.getScaleY();
	}

	public float getScaleZ() {
		return worldTransformation.getScaleZ();
	}

	public void setScaleX(float scaleX) {
		worldTransformation.setScaleX(scaleX);
	}

	public void setScaleY(float scaleY) {
		worldTransformation.setScaleY(scaleY);
	}

	public void setScaleZ(float scaleZ) {
		worldTransformation.setScaleZ(scaleZ);
	}

	public Model getModel() {
		return model;
	}

	/**
	 * Activates all the textures.
	 */
	public void activateTextures() {
		model.getDiffuseTexture().activateTexture();
		model.getSpecularTexture().activateTexture();
	}

	public Matrix4f getModelMatrix() {
		return worldTransformation.getMatrix();
	}

}
