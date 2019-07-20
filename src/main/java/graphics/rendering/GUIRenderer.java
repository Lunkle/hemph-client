package graphics.rendering;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import graphics.gui.GUI;
import graphics.gui.GUIShader;
import graphics.transformation.Transformation;
import math.Matrix4f;

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
		for (GUI gui : guis) {
			Transformation t = gui.getModelTransformation();
			Matrix4f matrix = t.getMatrix();
			shader.loadModelMatrix(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD_VERTEX_COUNT);
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}

}
