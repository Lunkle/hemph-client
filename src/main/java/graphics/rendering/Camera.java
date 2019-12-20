package graphics.rendering;

import graphics.transformation.ViewTransformation;
import math.Vector3f;

public class Camera {

	private boolean manualControl = true;
	private ViewTransformation transformation;
	private Vector3f cameraMoveDirection = new Vector3f(0, 0, 0);
	private Vector3f cameraPositionTarget = new Vector3f(0, 0, 0);
	private float cameraSpeed = 0.12f;

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
		if (manualControl) {
			if (cameraMoveDirection.lengthSquared() > 0.000001) {
				Vector3f movementVector = new Vector3f(cameraMoveDirection);
				movementVector.normalise().scale(cameraSpeed);
				transformation.increasePosition(movementVector.x, movementVector.y, movementVector.z);
			}
		} else {
			Vector3f targetVector = Vector3f.sub(cameraPositionTarget, getPosition()).scale(0.5f);
			transformation.increasePosition(targetVector.x, targetVector.y, targetVector.z);
			if (targetVector.lengthSquared() < 0.000001) {
				manualControl = true;
			}
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

	@Override
	public String toString() {
		return "Camera:\n\t" + "x: " + transformation.getPosition().getX() + "\n\ty: " + transformation.getPosition().getY() + "\n\tz: " + transformation.getPosition().getZ();
	}

	public void setTargetPosition(float targetX, float targetY, float targetZ) {
		manualControl = false;
		cameraPositionTarget.set(targetX, targetY, targetZ);
	}

}
