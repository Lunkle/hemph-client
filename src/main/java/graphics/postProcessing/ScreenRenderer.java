package graphics.postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import graphics.rendering.Renderer;
import graphics.texture.Texture;
import graphics.vao.VAO;
import logics.state.GameState;

public class ScreenRenderer extends Renderer {

	private static final int QUAD_VERTEX_COUNT = 4;

	private ScreenShader shader;
	private VAO screenQuad;
	private Texture texture;

	public ScreenRenderer() {
		shader = new ScreenShader();
		screenQuad = new VAO();
		ScreenQuadRawMeshData screenQuadData = new ScreenQuadRawMeshData();
		screenQuad.interpret(screenQuadData); // Interpreting the raw screen quad data
	}

	@Override
	public void render(GameState gameState) {
		shader.start();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		screenQuad.bindVAO();
		GL20.glEnableVertexAttribArray(0);
		texture.activateTexture();
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD_VERTEX_COUNT);
		GL20.glDisableVertexAttribArray(0);
		screenQuad.unbindVAO();
		shader.stop();
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
