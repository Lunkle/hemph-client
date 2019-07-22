package graphics;

import graphics.transformation.ViewTransformation;
import math.Matrix4f;
import math.Vector3f;

public class Camera {

	private ViewTransformation transformation;
	private Vector3f cameraMoveDirection;
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
		cameraMoveDirection.normalise().scale(cameraSpeed);
		transformation.increasePosition(cameraMoveDirection.x, cameraMoveDirection.y, cameraMoveDirection.z);
	}

	public void addDirection(Directions addDirection) {
		switch (addDirection) {
			case FRONT:
				Vector3f.add(cameraMoveDirection, new Vector3f(0, 0, 0), cameraMoveDirection);
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			case BACK:
				break;
			case UP:
				break;
			case DOWN:
				break;
		}
	}

}
