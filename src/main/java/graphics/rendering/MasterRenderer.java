package graphics.rendering;

import org.lwjgl.opengl.GL11;

import graphics.entity.EntityRenderer;
import graphics.framebuffer.DoubleBuffer;
import graphics.framebuffer.FBO;
import graphics.gui.GUIRenderer;
import graphics.postProcessing.ScreenRenderer;
import graphics.skybox.SkyboxRenderer;
import graphics.transformation.ProjectionTransformation;
import graphics.transformation.ProjectionWrapper;
import logics.state.GameStateWrapper;

public class MasterRenderer {

	private GameStateWrapper stateWrapper;

	private DoubleBuffer doubleBuffer;
	private EntityRenderer entityRenderer;
//	private BlurRenderer blurRenderer;
	private GUIRenderer guiRenderer;
	private SkyboxRenderer skyboxRenderer;
	private ScreenRenderer screenRenderer;
	private ProjectionWrapper projectionWrapper;

	public MasterRenderer(GameStateWrapper stateWrapper, int windowWidth, int windowHeight) {
		this.stateWrapper = stateWrapper;

		this.projectionWrapper = new ProjectionWrapper();
		loadWindowDimensions(windowWidth, windowHeight);

		entityRenderer = new EntityRenderer(projectionWrapper);
		skyboxRenderer = new SkyboxRenderer(projectionWrapper);
		guiRenderer = new GUIRenderer();
		screenRenderer = new ScreenRenderer();
	}

	public void render() {
		doubleBuffer.bindNextBuffer();
		FBO.clearCurrentFBOData();
		skyboxRenderer.render(stateWrapper.getState());
		entityRenderer.render(stateWrapper.getState());
		guiRenderer.render(stateWrapper.getState());

		FBO.bindDefaultFBO();
		FBO.clearCurrentFBOData();
		screenRenderer.render(doubleBuffer.getColourTexture());
	}

	public void cleanUp() {
		doubleBuffer.cleanUp();
		guiRenderer.cleanUp();
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public ProjectionWrapper getProjectionWrapper() {
		return projectionWrapper;
	}

	public void loadWindowDimensions(int windowWidth, int windowHeight) {
		doubleBuffer = new DoubleBuffer(windowWidth, windowHeight);
		ProjectionTransformation projectionTransformation = new ProjectionTransformation(windowWidth, windowHeight);
		projectionWrapper.setTransformation(projectionTransformation);
	}

}
