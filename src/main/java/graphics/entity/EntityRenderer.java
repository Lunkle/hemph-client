package graphics.entity;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import graphics.transformation.ProjectionWrapper;
import graphics.vao.VAO;
import logics.octree.GameEntity;
import logics.state.GameState;

public class EntityRenderer {

	private EntityShader shader;
	private ProjectionWrapper projectionWrapper;

	public EntityRenderer(ProjectionWrapper projectionWrapper) {
		shader = new EntityShader();
		this.projectionWrapper = projectionWrapper;
	}

	/**
	 * This should be called every frame so that the entity renderer knows what
	 * lights are currently on the scene, and the perspective it should take.
	 * 
	 * @param gameState
	 */
	public void loadUniforms(GameState gameState) {
		shader.start();
		shader.loadDirectionalLights(gameState.getDirectionalLights());
		shader.loadPointLights(gameState.getPointLights());
//		shader.loadSpotLights(gameState.getSpotLights());
		shader.loadViewMatrix(gameState.getCamera().getViewTransformation().getMatrix());
		shader.stop();
	}

	public void render(GameState gameState) {
		loadUniforms(gameState);
		Map<VAO, List<GameEntity>> meshEntityMap = gameState.getMeshLists();
		shader.start();
		shader.loadProjectionMatrix(projectionWrapper.getTransformation().getMatrix());
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		enableCulling();
		for (VAO mesh : meshEntityMap.keySet()) {
			mesh.bindVAO();
			for (GameEntity entity : meshEntityMap.get(mesh)) {
				entity.getModel().activateTextures();
				shader.loadModelMatrix(entity.getAbsoluteWorldTransformation().getMatrix());
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			mesh.unbindVAO();
		}
		shader.stop();
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public void cleanUp() {
		shader.cleanUp();
	}

}
