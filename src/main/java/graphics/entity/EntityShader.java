package graphics.entity;

import java.util.List;

import graphics.light.Light;
import graphics.rendering.ShaderProgram;
import math.Matrix4f;

public class EntityShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/java/graphics/entity/entityVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/main/java/graphics/entity/entityFragmentShader.txt";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;

	public EntityShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelMatrix = super.getUniformLocation("modelMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
	}

	public void loadLights(List<Light> lights) {
		// TODO Auto-generated method stub

	}

	public void loadModelMatrix(Matrix4f matrix) {
		super.loadMatrix(location_modelMatrix, matrix);
	}

	public void loadViewMatrix(Matrix4f matrix) {
		super.loadMatrix(location_viewMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

}
