package graphics.rendering;

import org.lwjgl.opengl.GL11;

import graphics.entity.EntityRenderer;
import graphics.framebuffer.FBO;
import graphics.gui.GUIRenderer;
import graphics.postProcessing.ScreenRenderer;
import graphics.transformation.ProjectionTransformation;
import logics.state.GameState;

public class MasterRenderer extends Renderer {

	private static final float RED = 123 / 255.0f;
	private static final float GREEN = 138 / 255.0f;
	private static final float BLUE = 149 / 255.0f;

	private FBO fbo;
	private EntityRenderer entityRenderer;
	private GUIRenderer guiRenderer;
	private ScreenRenderer screenRenderer;
	private ProjectionTransformation projectionTransformation;

	public MasterRenderer(int windowWidth, int windowHeight) {
		entityRenderer = new EntityRenderer();
		projectionTransformation = new ProjectionTransformation(windowWidth, windowHeight);
		entityRenderer.loadProjectionMatrix(projectionTransformation);
		guiRenderer = new GUIRenderer();
		screenRenderer = new ScreenRenderer();
		fbo = new FBO(windowWidth, windowHeight);
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void render(GameState gameState) {
		fbo.bindFBO();
		prepare();
		entityRenderer.render(gameState);
		guiRenderer.render(gameState);

		FBO.bindDefaultFBO();
		prepare();
		screenRenderer.setTexture(fbo.getColourTexture());
		screenRenderer.render(gameState);
	}

	public void cleanUp() {
		entityRenderer.cleanUp();
		guiRenderer.cleanUp();
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public ProjectionTransformation getProjectionTransformation() {
		return projectionTransformation;
	}

}
