package graphics.skybox;

import graphics.rendering.ShaderProgram;
import math.Matrix4f;

public class SkyboxShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/java/graphics/skybox/skyboxVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/main/java/graphics/skybox/skyboxFragmentShader.txt";

	private int location_projectionMatrix;
	private int location_viewMatrix;

	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		getAllUniformLocations();
		start();
		connectTextureUnits();
		stop();
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Matrix4f matrix) {
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		super.loadMatrix(location_viewMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}

	private void connectTextureUnits() {
//		super.loadInt(location_materialDiffuse, 0);
	}

}
