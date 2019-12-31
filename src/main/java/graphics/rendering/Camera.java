package graphics.rendering;

import org.lwjgl.glfw.GLFW;

import graphics.transformation.UnitQuaternion;
import graphics.transformation.ViewTransformation;
import logics.octree.GameEntity;
import math.Vector3f;

public class Camera {

	private ViewTransformation transformation;
	private Vector3f cameraMoveDirection = new Vector3f(0, 0, 0);
	private float cameraSpeed = 0.12f;

	private boolean manualControl = true;
	private double initialTime;
	private double transitionTime;
	private Vector3f initialPosition;
	private Vector3f toTargetVector;

	private boolean rotationSlerping = false;
	private double initialRotationTime;
	private double rotationTransitionTime;
	private UnitQuaternion initialRotation;
	private Vector3f targetRotationAxis;
	private float totalRotationAngle;

	private boolean tracking = false;
	private boolean trackingEntity = false;
	private Vector3f lookingLocation;
	private GameEntity lookingEntity;

	/**
	 * How smooth the transition to target position is. Positive values mean smooth
	 * transition. Negative values mean not smooth transition.
	 */
	private static final double SMOOTH_FACTOR = 5;
	private static final double TRANSITION_CONSTANT = 1.0 / (1.0 / Math.exp(SMOOTH_FACTOR) - 1);

	public enum Directions {
		FRONT,
		BACK,
		LEFT,
		RIGHT,
		UP,
		DOWN,
	}

	public Camera() {
		transformation = new ViewTransformation(0, 0, 0, new Vector3f(0, 1, 0), 0);
	}

	public ViewTransformation getViewTransformation() {
		return transformation;
	}

	private void setPosition(float posX, float posY, float posZ) {
		transformation.setPosition(posX, posY, posZ);
	}

	private void setPosition(Vector3f pos) {
		setPosition(pos.x, pos.y, pos.z);
	}

	public Vector3f getPosition() {
		return transformation.getPosition();
	}

	public void setRotation(Vector3f axis, float theta) {
		transformation.setRotation(axis, theta);
	}

	public void update() {
		if (manualControl) {
			manualControlUpdate();
		} else {
			calculateTransitionPosition();
		}
		if (rotationSlerping) {
			calculateTransitionRotation();
		} else if (tracking) {
			trackUpdate();
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

	public void manualControlUpdate() {
		if (cameraMoveDirection.lengthSquared() > 0.000001) {
			Vector3f movementVector = new Vector3f(cameraMoveDirection);
			movementVector.normalise().scale(cameraSpeed);
			transformation.increasePosition(movementVector.x, movementVector.y, movementVector.z);
		}
	}

	public void setPosition(float targetX, float targetY, float targetZ, float time) {
		if (time <= 0) {
			setPosition(targetX, targetY, targetZ);
		} else {
			manualControl = false;
			initialTime = GLFW.glfwGetTime();
			transitionTime = time;
			initialPosition = getPosition();
			toTargetVector = Vector3f.sub(new Vector3f(targetX, targetY, targetZ), initialPosition);
		}
	}

	public void calculateTransitionPosition() {
		double factor = getInterpolationFactor(GLFW.glfwGetTime(), initialTime, transitionTime);
		if (factor == 1) {
			manualControl = true;
			setPosition(Vector3f.add(initialPosition, toTargetVector));
		} else {
			Vector3f movementVector = new Vector3f(toTargetVector).scale((float) factor);
			setPosition(Vector3f.add(initialPosition, movementVector));
		}
	}

	public void setRotation(Vector3f axis, float angle, float time) {
		UnitQuaternion targetRotation = new UnitQuaternion(axis, angle / 2);
		UnitQuaternion currentRotationInverse = transformation.getRotation().getInverse();
		UnitQuaternion appliedRotation = currentRotationInverse.multiply(targetRotation);
		applyRotation(appliedRotation.getAxis(), appliedRotation.getAngle() * 2, time);
	}

	public void applyRotation(Vector3f axis, float angle, float time) {
		stopTracking();
		if (time <= 0) {
			UnitQuaternion addRotationQuaternion = new UnitQuaternion(axis, angle / 2);
			UnitQuaternion finalRotationQuaternion = initialRotation.multiply(addRotationQuaternion);
			transformation.setRotation(finalRotationQuaternion);
		} else {
			rotationSlerping = true;
			initialRotationTime = GLFW.glfwGetTime();
			rotationTransitionTime = time;
			initialRotation = transformation.getRotation();
			targetRotationAxis = new Vector3f(axis);
			totalRotationAngle = angle;
		}
	}

	private void calculateTransitionRotation() {
		double factor = getInterpolationFactor(GLFW.glfwGetTime(), initialRotationTime, rotationTransitionTime);
		if (factor == 1) {
			rotationSlerping = false;
		}
		UnitQuaternion addedRotationQuaternion = new UnitQuaternion(targetRotationAxis, (float) (factor * totalRotationAngle / 2));
		UnitQuaternion finalRotationQuaternion = initialRotation.multiply(addedRotationQuaternion);
		transformation.setRotation(finalRotationQuaternion);
	}

	public double getInterpolationFactor(double current, double start, double duration) {
		double linearFactor = (current - start) / duration;
		double targetFactor = 1;
		if (linearFactor < 1) {
			targetFactor = TRANSITION_CONSTANT * (Math.exp(-SMOOTH_FACTOR * linearFactor) - 1);
		}
		return targetFactor;
	}

	public void stopTracking() {
		tracking = false;
	}

	public void track(float xPos, float yPos, float zPos) {
		tracking = true;
		rotationSlerping = false;
		trackingEntity = false;
		lookingLocation = new Vector3f(xPos, yPos, zPos);
	}

	public void track(GameEntity entity) {
		tracking = true;
		rotationSlerping = false;
		trackingEntity = true;
		lookingEntity = entity;
	}

	public void trackUpdate() {
		Vector3f objectLocation = trackingEntity ? lookingEntity.getAbsoluteWorldTransformation().getPosition() : lookingLocation;
		Vector3f toLookVector = Vector3f.sub(objectLocation, getPosition());
		Vector3f defaultVector = new Vector3f(0, 0, -1);
		float angle = Vector3f.angle(defaultVector, toLookVector);
		Vector3f cross = Vector3f.cross(defaultVector, toLookVector);
		setRotation(cross, angle);
	}

	@Override
	public String toString() {
		return "Camera:\n\t" + "x: " + transformation.getPosition().getX() + "\n\ty: " + transformation.getPosition().getY() + "\n\tz: " + transformation.getPosition().getZ();
	}

}
