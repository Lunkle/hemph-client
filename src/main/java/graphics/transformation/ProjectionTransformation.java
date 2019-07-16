package graphics.transformation;

import game.Visual;
import math.Matrix4f;

public class ProjectionTransformation extends Transformation {

	private float fov = 110;
	private float nearPlane = 0.1f;
	private float farPlane = 1000;

	@Override
	protected void calculateMatrix() {
		float aspectRatio = Visual.getWindowWidth() / (float) Visual.getWindowHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = farPlane - nearPlane;

		matrix = new Matrix4f();
		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((farPlane + nearPlane) / frustum_length);
		matrix.m23 = -1;
		matrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
		matrix.m33 = 0;

	}

}
