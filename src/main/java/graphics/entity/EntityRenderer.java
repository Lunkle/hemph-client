package graphics.entity;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import graphics.transformation.ProjectionTransformation;
import graphics.vao.VAO;
import logics.state.GameState;

public class EntityRenderer {

	private EntityShader shader;

	public EntityRenderer() {
		shader = new EntityShader();
	}

	public void render(GameState gameState) {
		enableCulling();
//		Map<VAO, List<Entity>> backgroundMeshEntityMap = gameState.getBackgroundMeshes();
		Map<VAO, List<Entity>> foregroundMeshEntityMap = gameState.getForegroundMeshes();
		shader.start();
		shader.loadDirectionalLights(gameState.getDirectionalLights());
		shader.loadPointLights(gameState.getPointLights());
//		shader.loadSpotLights(gameState.getSpotLights());
		shader.loadViewMatrix(gameState.getCamera().getViewTransformation().getMatrix());
		for (VAO mesh : foregroundMeshEntityMap.keySet()) {
			GL30.glBindVertexArray(mesh.getVaoId());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			List<Entity> entityBatch = foregroundMeshEntityMap.get(mesh);
			for (Entity entity : entityBatch) {
				entity.activateTextures();
				shader.loadModelMatrix(entity.getModelMatrix());
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
		}
		shader.stop();
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public void loadProjectionMatrix(ProjectionTransformation projectionTransformation) {
		shader.start();
		shader.loadProjectionMatrix(projectionTransformation.getMatrix());
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}
