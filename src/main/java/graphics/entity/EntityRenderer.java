package graphics.entity;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import graphics.model.Mesh;
import graphics.transformation.ProjectionTransformation;
import logics.GameState;

public class EntityRenderer {

	private EntityShader shader;

	public EntityRenderer() {
		shader = new EntityShader();
	}

	public void render(GameState gameState) {
		Map<Mesh, List<Entity>> meshEntityMap = gameState.getMeshes();
		shader.start();
		shader.loadViewMatrix(gameState.getViewMatrix());
		for (Mesh mesh : meshEntityMap.keySet()) {
			GL30.glBindVertexArray(mesh.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			List<Entity> entityBatch = meshEntityMap.get(mesh);
			for (Entity entity : entityBatch) {
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getTextureID());
				shader.loadModelMatrix(entity.getModelMatrix());
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
		shader.stop();
	}

	@SuppressWarnings("unused")
	private void bindMeshVAO(Mesh mesh) {
		GL30.glBindVertexArray(mesh.getVAOID());
		GL20.glEnableVertexAttribArray(0);
//		GL20.glEnableVertexAttribArray(1);
//		GL20.glEnableVertexAttribArray(2);
//		ModelTexture texture = model.getTexture();
//		if (texture.isHasTransparency()) {
//			Renderer.disableCulling();
//		}
//		shader.loadTextureGridSize(texture.getTextureGridSize());
//		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
//		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());

	}

	public void loadProjectionMatrix(ProjectionTransformation projectionTransformation) {
		shader.start();
		shader.loadProjectionMatrix(projectionTransformation.getMatrix());
		shader.stop();
	}
}