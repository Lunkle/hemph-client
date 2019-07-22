package graphics.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GUIRenderer {

	private static final int QUAD_VERTEX_COUNT = 6;

	private GUIShader shader;

	public GUIRenderer() {
		this.shader = new GUIShader();
	}

	public void render(List<GUI> guis) {
		shader.start();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(GUI.getGuiVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for (GUI gui : guis) {
			int textureID = gui.getTextureID();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
			shader.loadModelMatrix(gui.getModelTransformation().getMatrix());
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD_VERTEX_COUNT);
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}

}
