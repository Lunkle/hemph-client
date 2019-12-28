package graphics.skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import graphics.transformation.ProjectionWrapper;
import graphics.vao.VAO;
import loading.skybox.SkyboxMeshRawData;
import logics.state.GameState;

public class SkyboxRenderer {

	private static final int CUBE_VERTEX_COUNT = 36;

	private VAO cube;
	private SkyboxShader shader;

	private ProjectionWrapper projectionWrapper;

	public SkyboxRenderer(ProjectionWrapper projectionWrapper) {
		shader = new SkyboxShader();
		cube = new VAO();
		SkyboxMeshRawData boxData = new SkyboxMeshRawData();
		cube.interpret(boxData);
		this.projectionWrapper = projectionWrapper;
	}

	public void render(GameState gameState) {
		GL11.glDepthMask(false);
		shader.start();
		shader.loadProjectionMatrix(projectionWrapper.getTransformation().getMatrix());
		shader.loadViewMatrix(gameState.getCamera().getViewTransformation().getMatrix());
		GL30.glBindVertexArray(cube.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		for (Skybox skybox : gameState.getSkyboxes()) {
			skybox.activateTextures();
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, CUBE_VERTEX_COUNT);
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
		GL11.glDepthMask(true);
	}
}
