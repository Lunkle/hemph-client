package graphics.rendering;

import graphics.transformation.ViewTransformation;
import math.Vector3f;

public class Camera {

	private ViewTransformation transformation;
	private Vector3f cameraMoveDirection = new Vector3f(0, 0, 0);
	private float cameraSpeed = 0.06f;

	public enum Directions {
		FRONT,
		BACK,
		LEFT,
		RIGHT,
		UP,
		DOWN,
	}

	public Camera() {
		transformation = new ViewTransformation(0, 0, 0, 0, 0, 0);
	}

	public ViewTransformation getViewTransformation() {
		return transformation;
	}

	public void setPosition(float posX, float posY, float posZ) {
		transformation.setPosition(posX, posY, posZ);
	}

	public Vector3f getPosition() {
		return transformation.getPosition();
	}

	public void setRotation(float rotX, float rotY, float rotZ) {
		transformation.setRotation(rotX, rotY, rotZ);
	}

	public void update() {
		if (cameraMoveDirection.lengthSquared() != 0) {
			Vector3f movementVector = new Vector3f(cameraMoveDirection);
			movementVector.normalise().scale(cameraSpeed);
			transformation.increasePosition(movementVector.x, movementVector.y, movementVector.z);
		}
	}

	public void addDirection(Directions addDirection) {
		switch (addDirection) {
			case FRONT:
				Vector3f.add(cameraMoveDirection, new Vector3f(0, 0, -1), cameraMoveDirection);
				break;
			case BACK:
				Vector3f.add(cameraMoveDirection, new Vector3f(0, 0, 1), cameraMoveDirection);
				break;
			case LEFT:
				Vector3f.add(cameraMoveDirection, new Vector3f(-1, 0, 0), cameraMoveDirection);
				break;
			case RIGHT:
				Vector3f.add(cameraMoveDirection, new Vector3f(1, 0, 0), cameraMoveDirection);
				break;
			case UP:
				Vector3f.add(cameraMoveDirection, new Vector3f(0, 1, 0), cameraMoveDirection);
				break;
			case DOWN:
				Vector3f.add(cameraMoveDirection, new Vector3f(0, -1, 0), cameraMoveDirection);
				break;
		}
	}

}
