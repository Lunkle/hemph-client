package graphics.entity;

import java.util.List;

import graphics.light.DirectionalLight;
import graphics.light.DirectionalLightLocation;
import graphics.light.PointLight;
import graphics.light.PointLightLocation;
import graphics.light.SpotLight;
import graphics.light.SpotLightLocation;
import graphics.rendering.ShaderProgram;
import math.Matrix4f;
import math.Vector3f;

public class EntityShader extends ShaderProgram {

	private static final String VERTEX_FILE = "shaders/entity/entityVertexShader.txt";
	private static final String FRAGMENT_FILE = "shaders/entity/entityFragmentShader.txt";

	private static final int MAX_LIGHTS = 3;

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private DirectionalLightLocation[] directionalLightLocations = new DirectionalLightLocation[MAX_LIGHTS];
	private PointLightLocation[] pointLightLocations = new PointLightLocation[MAX_LIGHTS];
	private SpotLightLocation[] spotLightLocations = new SpotLightLocation[MAX_LIGHTS];
	private int location_materialDiffuse;
	private int location_materialSpecular;

	public EntityShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		getAllUniformLocations();
		start();
		connectTextureUnits();
		stop();
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelMatrix = super.getUniformLocation("modelMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_materialDiffuse = super.getUniformLocation("material.diffuse");
		location_materialSpecular = super.getUniformLocation("material.specular");

		for (int i = 0; i < MAX_LIGHTS; i++) {
			int directionalDirectionLocation = super.getUniformLocation("directionalLights[" + i + "].direction");
			int directionalAmbientLocation = super.getUniformLocation("directionalLights[" + i + "].ambient");
			int directionalDiffuseLocation = super.getUniformLocation("directionalLights[" + i + "].diffuse");
			int directionalSpecularLocation = super.getUniformLocation("directionalLights[" + i + "].specular");
			int directionalStrengthLocation = super.getUniformLocation("directionalLights[" + i + "].strength");
			directionalLightLocations[i] = new DirectionalLightLocation(directionalDirectionLocation, directionalAmbientLocation, directionalDiffuseLocation, directionalSpecularLocation, directionalStrengthLocation);
			int pointPositionLocation = super.getUniformLocation("pointLights[" + i + "].position");
			int pointConstantsLocation = super.getUniformLocation("pointLights[" + i + "].constants");
			int pointAmbientLocation = super.getUniformLocation("pointLights[" + i + "].ambient");
			int pointDiffuseLocation = super.getUniformLocation("pointLights[" + i + "].diffuse");
			int pointSpecularLocation = super.getUniformLocation("pointLights[" + i + "].specular");
			int pointStrengthLocation = super.getUniformLocation("pointLights[" + i + "].strength");
			pointLightLocations[i] = new PointLightLocation(pointPositionLocation, pointConstantsLocation, pointAmbientLocation, pointDiffuseLocation, pointSpecularLocation, pointStrengthLocation);
			int spotDirectionLocation = super.getUniformLocation("spotLights[" + i + "].direction");
			int spotPositionLocation = super.getUniformLocation("spotLights[" + i + "].position");
			int spotAngleLocation = super.getUniformLocation("spotLights[" + i + "].angle");
			int spotAmbientLocation = super.getUniformLocation("spotLights[" + i + "].ambient");
			int spotDiffuseLocation = super.getUniformLocation("spotLights[" + i + "].diffuse");
			int spotSpecularLocation = super.getUniformLocation("spotLights[" + i + "].specular");
			int spotStrengthLocation = super.getUniformLocation("spotLights[" + i + "].strength");
			spotLightLocations[i] = new SpotLightLocation(spotDirectionLocation, spotPositionLocation, spotAngleLocation, spotAmbientLocation, spotDiffuseLocation, spotSpecularLocation, spotStrengthLocation);
		}
	}

	private void connectTextureUnits() {
		super.loadInt(location_materialDiffuse, 0);
		super.loadInt(location_materialSpecular, 1);
	}

	public void loadDirectionalLights(List<DirectionalLight> directionalLights) {
		int minimum = Math.min(directionalLights.size(), MAX_LIGHTS);
		for (int i = 0; i < minimum; i++) {
			super.loadVector(directionalLightLocations[i].getLocation_direction(), directionalLights.get(i).getDirection());
			super.loadVector(directionalLightLocations[i].getLocation_ambient(), directionalLights.get(i).getAmbient());
			super.loadVector(directionalLightLocations[i].getLocation_diffuse(), directionalLights.get(i).getDiffuse());
			super.loadVector(directionalLightLocations[i].getLocation_specular(), directionalLights.get(i).getSpecular());
			super.loadFloat(directionalLightLocations[i].getLocation_strength(), directionalLights.get(i).getStrength());
		}
		for (int i = minimum; i < MAX_LIGHTS; i++) {
			super.loadVector(directionalLightLocations[i].getLocation_direction(), new Vector3f(0, 0, 0));
			super.loadVector(directionalLightLocations[i].getLocation_ambient(), new Vector3f(0, 0, 0));
			super.loadVector(directionalLightLocations[i].getLocation_diffuse(), new Vector3f(0, 0, 0));
			super.loadVector(directionalLightLocations[i].getLocation_specular(), new Vector3f(0, 0, 0));
			super.loadFloat(directionalLightLocations[i].getLocation_strength(), 0);
		}
	}

	public void loadPointLights(List<PointLight> pointLights) {
		int minimum = Math.min(pointLights.size(), MAX_LIGHTS);
		for (int i = 0; i < minimum; i++) {
			super.loadVector(pointLightLocations[i].getLocation_position(), pointLights.get(i).getPosition());
			super.loadVector(pointLightLocations[i].getLocation_constants(), pointLights.get(i).getConstants());
			super.loadVector(pointLightLocations[i].getLocation_ambient(), pointLights.get(i).getAmbient());
			super.loadVector(pointLightLocations[i].getLocation_diffuse(), pointLights.get(i).getDiffuse());
			super.loadVector(pointLightLocations[i].getLocation_specular(), pointLights.get(i).getSpecular());
			super.loadFloat(pointLightLocations[i].getLocation_strength(), pointLights.get(i).getStrength());
		}
		for (int i = minimum; i < MAX_LIGHTS; i++) {
			super.loadVector(pointLightLocations[i].getLocation_position(), new Vector3f(0, 0, 0));
			super.loadVector(pointLightLocations[i].getLocation_constants(), new Vector3f(0, 0, 1));
			super.loadVector(pointLightLocations[i].getLocation_ambient(), new Vector3f(0, 0, 0));
			super.loadVector(pointLightLocations[i].getLocation_diffuse(), new Vector3f(0, 0, 0));
			super.loadVector(pointLightLocations[i].getLocation_specular(), new Vector3f(0, 0, 0));
			super.loadFloat(pointLightLocations[i].getLocation_strength(), 0);
		}
	}

	public void loadSpotLights(List<SpotLight> spotLights) {
		int minimum = Math.min(spotLights.size(), MAX_LIGHTS);
		for (int i = 0; i < minimum; i++) {
			super.loadVector(spotLightLocations[i].getLocation_direction(), spotLights.get(i).getDirection());
			super.loadVector(spotLightLocations[i].getLocation_position(), spotLights.get(i).getPosition());
			super.loadFloat(spotLightLocations[i].getLocation_angle(), spotLights.get(i).getAngle());
			super.loadVector(spotLightLocations[i].getLocation_ambient(), spotLights.get(i).getAmbient());
			super.loadVector(spotLightLocations[i].getLocation_diffuse(), spotLights.get(i).getDiffuse());
			super.loadVector(spotLightLocations[i].getLocation_specular(), spotLights.get(i).getSpecular());
			super.loadFloat(spotLightLocations[i].getLocation_strength(), spotLights.get(i).getStrength());
		}
		for (int i = minimum; i < MAX_LIGHTS; i++) {
			super.loadVector(spotLightLocations[i].getLocation_direction(), new Vector3f(0, 0, 0));
			super.loadVector(spotLightLocations[i].getLocation_position(), new Vector3f(0, 0, 0));
			super.loadFloat(spotLightLocations[i].getLocation_angle(), 0);
			super.loadVector(spotLightLocations[i].getLocation_ambient(), new Vector3f(0, 0, 0));
			super.loadVector(spotLightLocations[i].getLocation_diffuse(), new Vector3f(0, 0, 0));
			super.loadVector(spotLightLocations[i].getLocation_specular(), new Vector3f(0, 0, 0));
			super.loadFloat(spotLightLocations[i].getLocation_strength(), 0);
		}
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
