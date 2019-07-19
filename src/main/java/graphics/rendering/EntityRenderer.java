package graphics.rendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import graphics.entity.Entity;
import graphics.entity.EntityShader;
import graphics.model.Mesh;
import graphics.transformation.ProjectionTransformation;

public class EntityRenderer {

	private EntityShader shader;

	public EntityRenderer(EntityShader shader, ProjectionTransformation projectionTransformation) {
		this.shader = shader;
		loadProjectionMatrix(projectionTransformation);
	}

	public void render(Map<Mesh, List<Entity>> entities) {
		for (Mesh mesh : entities.keySet()) {
			bindMesh(mesh);
			List<Entity> batch = entities.get(mesh);
			for (Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindMesh();
		}
	}

	private void bindMesh(Mesh mesh) {
		GL30.glBindVertexArray(mesh.getVaoId());
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
//		GL13.glActiveTexture(GL13.GL_TEXTURE0);
//		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}

	private void unbindMesh() {
		Renderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(Entity entity) {
		shader.loadModelMatrix(entity.getModelMatrix());
		shader.loadTextureOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
	}

	private void loadProjectionMatrix(ProjectionTransformation projectionTransformation) {
		shader.start();
		shader.loadProjectionMatrix(projectionTransformation.getMatrix());
		shader.stop();
	}

}
