package logics.globe;

import graphics.model.Model;
import graphics.rendering.Camera;
import graphics.transformation.ProjectionWrapper;
import graphics.transformation.WorldTransformation;
import input.command.Command;
import input.information.Action;
import input.information.Key;
import input.mouse.Mouse;
import input.mouse.MousePicker;
import input.observer.MouseButtonObserver;
import input.observer.MouseCheck;
import input.observer.MouseMovementObserver;
import logics.octree.HeaderEntity;
import math.Vector3f;

public class GlobeEntity extends HeaderEntity {

	private boolean selected = false;
	private boolean focused = false;
	private Vector3f currentIntersection = null;
	private Vector3f previousIntersection = null;
	private Vector3f globePreviousPosition = null;

	public GlobeEntity(Model model, WorldTransformation worldTransformation) {
		super(model, worldTransformation);
	}

	public boolean isSelected() {
		return selected;
	}

	public void releaseGlobe(Camera camera) {
		if (!focused) {
			camera.setTargetPosition(-0.035147168f, 8.040001f, -3.9139676f);
		}
		focused = true;
	}

	public void defocusGlobe() {
		focused = false;
	}

	/**
	 * Gets intersection of a ray and a sphere.
	 * 
	 * @param origin
	 * @param direction
	 * @return the intersection point
	 */
	public Vector3f getIntersection(Vector3f origin, Vector3f direction) {
		globePreviousPosition = getAbsoluteWorldTransformation().getPosition();
		float globeSize = getAbsoluteWorldTransformation().getScale().getLargestComponent();
		Vector3f globeToCamera = Vector3f.sub(origin, globePreviousPosition);
		float a = Vector3f.dot(direction, direction);
		float b = 2 * Vector3f.dot(direction, globeToCamera);
		float c = Vector3f.dot(globeToCamera, globeToCamera) - globeSize * globeSize;
		float discriminant = b * b - 4 * a * c;
		if (discriminant >= 0) {
			float sqrtDiscriminant = (float) Math.sqrt(discriminant);
			float t1 = (-b + sqrtDiscriminant) / (2 * a);
			float t2 = (-b - sqrtDiscriminant) / (2 * a);
			if (t1 >= 0 || t2 >= 0) {
				float target = t1 < 0 ? t2 : (t2 < 0 ? t1 : Math.min(t1, t2));
				previousIntersection = currentIntersection;
				currentIntersection = Vector3f.add(origin, direction.scale(target));
				return currentIntersection;
			}
		}
		currentIntersection = null;
		return null;
	}

	public MouseButtonObserver getGlobeSelectionObserver(Mouse mouse, Camera camera, ProjectionWrapper projectionWrapper) {
		MouseButtonObserver globeSelectionObserver = new MouseButtonObserver();
		globeSelectionObserver.setName("Globe Press Observer");
		Command selectGlobe = new Command(() -> selected = true);
		MouseCheck globeIntersectionCheck = () -> {
			MousePicker.loadInformation(mouse, camera, projectionWrapper.getTransformation());
			Vector3f intersection = getIntersection(camera.getPosition(), MousePicker.calculateMouseRay());
			return intersection != null;
		};
		globeSelectionObserver.addCheck(Key.MOUSE_LEFT, Action.PRESS, globeIntersectionCheck, selectGlobe);
		return globeSelectionObserver;
	}

	public MouseButtonObserver getGlobeReleaseObserver(Mouse mouse, Camera camera, ProjectionWrapper projectionWrapper) {
		MouseButtonObserver globeReleaseObserver = new MouseButtonObserver();
		globeReleaseObserver.setName("Globe Release Observer");
		Command deselectGlobe = new Command(() -> releaseGlobe(camera));
		MouseCheck releaseCheck = () -> {
			selected = false;
			MousePicker.loadInformation(mouse, camera, projectionWrapper.getTransformation());
			Vector3f intersection = getIntersection(camera.getPosition(), MousePicker.calculateMouseRay());
			return intersection != null;
		};
		globeReleaseObserver.addCheck(Key.MOUSE_LEFT, Action.RELEASE, releaseCheck, deselectGlobe);
		return globeReleaseObserver;
	}

	public MouseMovementObserver getGlobeRotationObserver(Mouse mouse, Camera camera, ProjectionWrapper projectionWrapper) {
		MouseMovementObserver globeRotationObserver = new MouseMovementObserver();
		Command rotateGlobe = new Command(() -> {
			MousePicker.loadInformation(mouse, camera, projectionWrapper.getTransformation());
			rotateGlobe(camera.getPosition(), MousePicker.calculateMouseRay());
		});
		MouseCheck selectedCheck = () -> {
			return (selected && focused);
		};
		globeRotationObserver.addCheck(selectedCheck, rotateGlobe);
		return globeRotationObserver;
	}

	private void rotateGlobe(Vector3f origin, Vector3f direction) {
		getIntersection(origin, direction);
		if (isSelected() && previousIntersection != null && currentIntersection != null) {
			Vector3f prevInt = Vector3f.sub(previousIntersection, globePreviousPosition).normalise(null);
			Vector3f currInt = Vector3f.sub(currentIntersection, getWorldTransformation().getPosition()).normalise(null);
			Vector3f rotationAxis = Vector3f.cross(prevInt, currInt).normalise(null);
			float angleBetween = -Vector3f.angle(prevInt, currInt);
			getWorldTransformation().increaseRotation(rotationAxis, angleBetween);
		}
	}

	@Override
	public void update() {}

}
