package graphics.postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import graphics.texture.Texture;
import graphics.vao.VAO;
import loading.screen.ScreenQuadRawMeshData;

public class ScreenRenderer {

	private static final int QUAD_VERTEX_COUNT = 4;

	private ScreenShader shader;
	private VAO screenQuad;

	public ScreenRenderer() {
		shader = new ScreenShader();
		screenQuad = new VAO();
		ScreenQuadRawMeshData screenQuadData = new ScreenQuadRawMeshData();
		screenQuad.interpret(screenQuadData); // Interpreting the raw screen quad data
	}

	public void render(Texture texture) {
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

}
