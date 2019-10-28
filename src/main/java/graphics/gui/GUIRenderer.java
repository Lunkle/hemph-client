package graphics.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import graphics.vao.VAO;

public class GUIRenderer {

	private static final int QUAD_VERTEX_COUNT = 4;

	private GUIShader shader;
	private VAO guiVAO;

	public GUIRenderer() {
		this.shader = new GUIShader();
		guiVAO = new VAO();
		ScreenRawMeshData quadData = new ScreenRawMeshData();
		guiVAO.interpret(quadData); // Interpreting the raw quad data
	}

	public void render(List<GUI> guis) {
		shader.start();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		guiVAO.bindVAO();
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for (GUI gui : guis) {
			gui.activateTextures();
			shader.loadModelMatrix(gui.getTransformationMatrix());
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD_VERTEX_COUNT);
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		guiVAO.unbindVAO();
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}
