package input.mouse;

import graphics.rendering.Camera;
import graphics.transformation.ProjectionTransformation;
import graphics.transformation.ViewTransformation;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

public class MousePicker {

	private static Mouse mouse;
	private static ViewTransformation viewTransformation;
	private static ProjectionTransformation projectionTransformation;

	public static void loadInformation(Mouse mouse, ViewTransformation viewTransformation, ProjectionTransformation projectionTransformation) {
		MousePicker.mouse = mouse;
		MousePicker.viewTransformation = viewTransformation;
		MousePicker.projectionTransformation = projectionTransformation;
	}

	public static void loadInformation(Mouse mouse, Camera camera, ProjectionTransformation projectionTransformation) {
		loadInformation(mouse, camera.getViewTransformation(), projectionTransformation);
	}

	public static Vector3f calculateMouseRay() {
		Vector2f mousePosition = mouse.getPosition();
		Vector2f normalizedDeviceCoordinates = getNormalizedDeviceCoordinates(mousePosition);
		Vector4f clipSpaceCoordinates = new Vector4f(normalizedDeviceCoordinates.x, normalizedDeviceCoordinates.y, -1, 1);
		Vector4f eyeSpaceCooordinates = getEyeSpaceCoordinates(clipSpaceCoordinates);
		Vector3f worldRay = getWorldSpaceCoordinates(eyeSpaceCooordinates);
		return worldRay;
	}

	private static Vector2f getNormalizedDeviceCoordinates(Vector2f mousePosition) {
		float x = 2 * mousePosition.x / projectionTransformation.getWindowWidth() - 1;
		float y = 1 - 2 * mousePosition.y / projectionTransformation.getWindowHeight();
		return new Vector2f(x, y);
	}

	private static Vector4f getEyeSpaceCoordinates(Vector4f clipSpaceCoordinates) {
		Matrix4f invertedProjectionMatrix = Matrix4f.invert(projectionTransformation.getMatrix());
		Vector4f eyeSpaceCoordinates = Matrix4f.transform(invertedProjectionMatrix, clipSpaceCoordinates);
		return new Vector4f(eyeSpaceCoordinates.x, eyeSpaceCoordinates.y, -1, 0);
	}

	private static Vector3f getWorldSpaceCoordinates(Vector4f eyeSpaceCooordinates) {
		Matrix4f invertedProjectionMatrix = Matrix4f.invert(viewTransformation.getMatrix());
		Vector4f worldSpaceCoordinates = Matrix4f.transform(invertedProjectionMatrix, eyeSpaceCooordinates);
		Vector3f ray = new Vector3f(worldSpaceCoordinates.x, worldSpaceCoordinates.y, worldSpaceCoordinates.z);
		ray.normalise();
		return ray;
	}

}
