package graphics.entity;

import java.util.List;

import graphics.light.DirectionalLight;
import graphics.rendering.ShaderProgram;
import math.Matrix4f;
import math.Vector3f;

public class EntityShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/java/graphics/entity/entityVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/main/java/graphics/entity/entityFragmentShader.txt";

	private static final int MAX_LIGHTS = 1;

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private DirectionalLightLocation[] directionalLightLocations = new DirectionalLightLocation[MAX_LIGHTS];
//	private PointLightLocation[] pointLightLocations = new PointLightLocation[MAX_LIGHTS];
//	private SpotLightLocation[] spotLightLocations = new SpotLightLocation[MAX_LIGHTS];
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
			directionalLightLocations[i] = new DirectionalLightLocation(directionalDirectionLocation, directionalAmbientLocation, directionalDiffuseLocation, directionalSpecularLocation);
//			int pointPositionLocation = super.getUniformLocation("pointLights[" + i + "].position");
//			int pointAmbientLocation = super.getUniformLocation("pointLights[" + i + "].ambient");
//			int pointDiffuseLocation = super.getUniformLocation("pointLights[" + i + "].diffuse");
//			int pointSpecularLocation = super.getUniformLocation("pointLights[" + i + "].specular");
//			pointLightLocations[i] = new PointLightLocation(pointPositionLocation, pointAmbientLocation, pointDiffuseLocation, pointSpecularLocation);
//			int spotDirectionLocation = super.getUniformLocation("spotLights[" + i + "].direction");
//			int spotPositionLocation = super.getUniformLocation("spotLights[" + i + "].position");
//			int spotAngleLocation = super.getUniformLocation("spotLights[" + i + "].angle");
//			int spotAmbientLocation = super.getUniformLocation("spotLights[" + i + "].ambient");
//			int spotDiffuseLocation = super.getUniformLocation("spotLights[" + i + "].diffuse");
//			int spotSpecularLocation = super.getUniformLocation("spotLights[" + i + "].specular");
//			spotLightLocations[i] = new SpotLightLocation(spotDirectionLocation, spotPositionLocation, spotAngleLocation, spotAmbientLocation, spotDiffuseLocation, spotSpecularLocation);
		}
	}

	private void connectTextureUnits() {
		super.loadInt(location_materialDiffuse, 0);
		super.loadInt(location_materialSpecular, 1);
	}

	public void loadDirectionalLights(List<DirectionalLight> directionalLights) {
		for (int i = 0; i < Math.min(directionalLights.size(), MAX_LIGHTS); i++) {
			super.loadVector(directionalLightLocations[i].getLocation_direction(), directionalLights.get(i).getDirection());
			super.loadVector(directionalLightLocations[i].getLocation_ambient(), directionalLights.get(i).getAmbient());
			super.loadVector(directionalLightLocations[i].getLocation_diffuse(), directionalLights.get(i).getDiffuse());
			super.loadVector(directionalLightLocations[i].getLocation_specular(), directionalLights.get(i).getSpecular());
		}
		for (int i = 0; i < MAX_LIGHTS - directionalLights.size(); i++) {
			super.loadVector(directionalLightLocations[i].getLocation_direction(), new Vector3f(0, 0, 0));
			super.loadVector(directionalLightLocations[i].getLocation_ambient(), new Vector3f(0, 0, 0));
			super.loadVector(directionalLightLocations[i].getLocation_diffuse(), new Vector3f(0, 0, 0));
			super.loadVector(directionalLightLocations[i].getLocation_specular(), new Vector3f(0, 0, 0));
		}
	}

//	public void loadPointLights(List<PointLight> pointLights) {
//		for (int i = 0; i < Math.max(pointLights.size(), MAX_LIGHTS); i++) {
//			super.loadVector(pointLightLocations[i].getLocation_position(), pointLights.get(i).getPosition());
//			super.loadVector(pointLightLocations[i].getLocation_ambient(), pointLights.get(i).getAmbient());
//			super.loadVector(pointLightLocations[i].getLocation_diffuse(), pointLights.get(i).getDiffuse());
//			super.loadVector(pointLightLocations[i].getLocation_specular(), pointLights.get(i).getSpecular());
//		}
//		for (int i = 0; i < MAX_LIGHTS - pointLights.size(); i++) {
//			super.loadVector(pointLightLocations[i].getLocation_position(), new Vector3f(0, 0, 0));
//			super.loadVector(pointLightLocations[i].getLocation_ambient(), new Vector3f(0, 0, 0));
//			super.loadVector(pointLightLocations[i].getLocation_diffuse(), new Vector3f(0, 0, 0));
//			super.loadVector(pointLightLocations[i].getLocation_specular(), new Vector3f(0, 0, 0));
//		}
//	}
//
//	public void loadSpotLights(List<SpotLight> spotLights) {
//		for (int i = 0; i < Math.max(spotLights.size(), MAX_LIGHTS); i++) {
//			super.loadVector(spotLightLocations[i].getLocation_direction(), spotLights.get(i).getDirection());
//			super.loadVector(spotLightLocations[i].getLocation_position(), spotLights.get(i).getPosition());
//			super.loadFloat(spotLightLocations[i].getLocation_angle(), spotLights.get(i).getAngle());
//			super.loadVector(spotLightLocations[i].getLocation_ambient(), spotLights.get(i).getAmbient());
//			super.loadVector(spotLightLocations[i].getLocation_diffuse(), spotLights.get(i).getDiffuse());
//			super.loadVector(spotLightLocations[i].getLocation_specular(), spotLights.get(i).getSpecular());
//		}
//		for (int i = 0; i < MAX_LIGHTS - spotLights.size(); i++) {
//			super.loadVector(spotLightLocations[i].getLocation_direction(), new Vector3f(0, 0, 0));
//			super.loadVector(spotLightLocations[i].getLocation_position(), new Vector3f(0, 0, 0));
//			super.loadFloat(spotLightLocations[i].getLocation_angle(), 0);
//			super.loadVector(spotLightLocations[i].getLocation_ambient(), new Vector3f(0, 0, 0));
//			super.loadVector(spotLightLocations[i].getLocation_diffuse(), new Vector3f(0, 0, 0));
//			super.loadVector(spotLightLocations[i].getLocation_specular(), new Vector3f(0, 0, 0));
//		}
//	}

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
