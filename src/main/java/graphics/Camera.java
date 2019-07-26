package graphics;

import graphics.transformation.ViewTransformation;
import math.Matrix4f;
import math.Vector3f;

public class Camera {

	private ViewTransformation transformation;
	private Vector3f cameraMoveDirection = new Vector3f(0, 0, 0);
	private float cameraSpeed = 0.1f;

	public enum Directions {
		FRONT, LEFT, RIGHT, BACK, UP, DOWN,
	}

	public Camera() {
		transformation = new ViewTransformation(0, 0, 0, 0, 0, 0);
	}

	public Matrix4f getViewMatrix() {
		return transformation.getMatrix();
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
				Vector3f.add(cameraMoveDirection, new Vector3f(1, 0, 0), cameraMoveDirection);
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			case BACK:
				Vector3f.add(cameraMoveDirection, new Vector3f(-1, 0, 0), cameraMoveDirection);
				break;
			case UP:
				break;
			case DOWN:
				break;
		}
	}

}
