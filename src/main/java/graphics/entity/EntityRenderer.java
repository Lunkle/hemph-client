package graphics.entity;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import graphics.transformation.ProjectionTransformation;
import graphics.vao.VAO;
import logics.octree.GameEntity;
import logics.state.GameState;

public class EntityRenderer {

	private EntityShader shader;

	public EntityRenderer() {
		shader = new EntityShader();
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

	public void render(Map<VAO, List<GameEntity>> meshEntityMap) {
		shader.start();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		enableCulling();
		for (VAO mesh : meshEntityMap.keySet()) {
			mesh.bindVAO();
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			for (GameEntity entity : meshEntityMap.get(mesh)) {
				entity.getModel().activateTextures();
				shader.loadModelMatrix(entity.getAbsoluteWorldTransformation().getMatrix());
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			mesh.unbindVAO();
		}
		shader.stop();
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	/**
	 * Should only be called at the beginning of the game and whenever the window is
	 * resized (in which case the projection transformation needs to be updated).
	 * 
	 * @param projectionTransformation
	 */
	public void loadProjectionMatrix(ProjectionTransformation projectionTransformation) {
		shader.start();
		shader.loadProjectionMatrix(projectionTransformation.getMatrix());
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}

}
