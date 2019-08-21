package graphics.rendering;

import org.lwjgl.opengl.GL11;

import graphics.entity.EntityRenderer;
import graphics.gui.GUIRenderer;
import graphics.transformation.ProjectionTransformation;
import logics.state.GameState;

public class Renderer {

	private static final float RED = 123 / 255.0f;
	private static final float GREEN = 138 / 255.0f;
	private static final float BLUE = 149 / 255.0f;

	private EntityRenderer renderer;
	private GUIRenderer guiRenderer;

	public Renderer(ProjectionTransformation projectionTransformation) {
		renderer = new EntityRenderer();
		renderer.loadProjectionMatrix(projectionTransformation);
		guiRenderer = new GUIRenderer();
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void render(GameState gameState) {
		prepare();
		renderer.render(gameState);
		guiRenderer.render(gameState.getGuis());
	}

	public void cleanUp() {
		renderer.cleanUp();
		guiRenderer.cleanUp();
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

}
