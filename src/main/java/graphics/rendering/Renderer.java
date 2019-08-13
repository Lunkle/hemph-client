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

	private ProjectionTransformation projectionTransformation;

	private EntityRenderer renderer;
	private GUIRenderer guiRenderer;
//	private SkyboxRenderer skyboxRenderer;

	public Renderer() {
		projectionTransformation = new ProjectionTransformation();
		renderer = new EntityRenderer();
		renderer.loadProjectionMatrix(projectionTransformation);
		guiRenderer = new GUIRenderer();
//		skyboxRenderer = new SkyboxRenderer(projectionMatrix);
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void render(GameState gameState) {
		prepare();
//		entityShader.loadSkyColour(RED, GREEN, BLUE);
//		entityShader.loadLight(scene.getLights());
//		entityShader.loadViewMatrix(scene.getCamera().getMatrix());
		renderer.render(gameState);
//		entityShader.stop();
//		skyboxRenderer.render(camera);
		guiRenderer.render(gameState.getGuis());
	}

	public void cleanUp() {
//		entityShader.cleanUp();
		guiRenderer.cleanUp();
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

}
