package graphics.rendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import graphics.entity.EntityRenderer;
import graphics.framebuffer.DoubleBuffer;
import graphics.framebuffer.FBO;
import graphics.gui.GUIRenderer;
import graphics.postProcessing.ScreenRenderer;
import graphics.transformation.ProjectionTransformation;
import graphics.vao.VAO;
import logics.octree.GameEntity;
import logics.state.GameState;

public class MasterRenderer {

	private DoubleBuffer doubleBuffer;
	private EntityRenderer entityRenderer;
//	private BlurRenderer blurRenderer;
	private GUIRenderer guiRenderer;
	private ScreenRenderer screenRenderer;
	private ProjectionTransformation projectionTransformation;

	public MasterRenderer(int windowWidth, int windowHeight) {
		entityRenderer = new EntityRenderer();
		loadWindowDimensions(windowWidth, windowHeight);
		guiRenderer = new GUIRenderer();
		screenRenderer = new ScreenRenderer();
	}

	public void loadWindowDimensions(int windowWidth, int windowHeight) {
		doubleBuffer = new DoubleBuffer(windowWidth, windowHeight);
		projectionTransformation = new ProjectionTransformation(windowWidth, windowHeight);
		entityRenderer.loadProjectionMatrix(projectionTransformation);
	}

	public void render(GameState gameState) {
		doubleBuffer.bindNextBuffer();
		FBO.clearCurrentFBOData();
		entityRenderer.loadUniforms(gameState);
		for (Map<VAO, List<GameEntity>> meshes : gameState.getMeshLists()) {
////			Texture toBlurTexture = doubleBuffer.getColourTexture();
////			doubleBuffer.bindNextBuffer();
////			FBO.clearCurrentFBOData();
////			blurRenderer.render(toBlurTexture);
			entityRenderer.render(meshes);
		}
		guiRenderer.render(gameState.getGuis());
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

	public ProjectionTransformation getProjectionTransformation() {
		return projectionTransformation;
	}

}
