package graphics.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import graphics.texture.Texture;
import graphics.vao.VAO;
import logics.state.GameState;

public class GUIRenderer {

	private static final int QUAD_VERTEX_COUNT = 4;

	private GUIShader shader;
	private int guiVaoID;

	private static GUI finalGUI = GUIBuilder.newInstance().create();

	public GUIRenderer() {
		this.shader = new GUIShader();
		VAO guiVAO = new VAO();
		GUIMeshData quadData = new GUIMeshData();
		quadData.accept(guiVAO); // Interpreting the raw quad data
		guiVaoID = guiVAO.getVaoId();
	}

	public void render(GameState gameState) {
		List<GUI> guis = gameState.getGuis();
		shader.start();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(guiVaoID);
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
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void loadScreenDimensions(float width, float height) {
		finalGUI.setDimensions(0, 0, width, height);
	}

	public void renderFinalImage(Texture texture) {
		finalGUI.setTexture(texture);
		shader.start();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(guiVaoID);
		GL20.glEnableVertexAttribArray(0);
		finalGUI.activateTextures();
		shader.loadModelMatrix(finalGUI.getTransformationMatrix());
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD_VERTEX_COUNT);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}
