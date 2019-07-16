package graphics.entity;

import java.util.List;

import graphics.ShaderProgram;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;

public class EntityShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/java/graphics/entity/entityVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/main/java/graphics/entity/entityFragmentShader.txt";

	private static final int MAX_LIGHTS = 16;

	private int location_modelMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_inverseViewMatrix;
	private int[] location_lightPosition;
	private int[] location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColour;
	private int location_textureGridSize;
	private int location_textureOffset;

	public EntityShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelMatrix = super.getUniformLocation("modelMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_inverseViewMatrix = super.getUniformLocation("inverseViewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColour = super.getUniformLocation("skyColour");
		location_textureGridSize = super.getUniformLocation("textureGridSize");
		location_textureOffset = super.getUniformLocation("textureOffset");

		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
		}
	}

	public void loadTextureOffset(float x, float y) {
		super.loadVector(location_textureOffset, new Vector2f(x, y));
	}

	public void loadTextureGridSize(int size) {
		super.loadInt(location_textureGridSize, size);
	}

	public void loadSkyColour(float r, float g, float b) {
		super.loadVector(location_skyColour, new Vector3f(r, g, b));
	}

	public void loadFakeLightingVariable(boolean useFake) {
		super.loadBoolean(location_useFakeLighting, useFake);
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}

	public void loadModelMatrix(Matrix4f matrix) {
		super.loadMatrix(location_modelMatrix, matrix);
	}

	public void loadLight(List<Light> lights) {
		for (int i = 0; i < Math.min(MAX_LIGHTS, lights.size()); i++) {
			super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
			super.loadVector(location_lightColour[i], lights.get(i).getColour());
		}
		for (int i = 0; i < Math.max(0, MAX_LIGHTS - lights.size()); i++) {
			super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
			super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
		}
	}

	public void loadViewMatrix(Matrix4f matrix) {
		super.loadMatrix(location_viewMatrix, matrix);
		super.loadMatrix(location_inverseViewMatrix, Matrix4f.invert(matrix, matrix));
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

}
