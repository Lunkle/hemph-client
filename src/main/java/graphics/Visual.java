package graphics;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import graphics.gui.GUI;
import graphics.rendering.Renderer;
import graphics.texture.Texture;
import graphics.transformation.ProjectionTransformation;
import graphics.vao.EBO;
import graphics.vao.VAO;
import graphics.vao.VBO;
import logics.state.GameState;

public class Visual {

	private long windowID;

	private int windowWidth = 1280;
	private int windowHeight = 720;
	private ProjectionTransformation projectionTransformation;

	private Renderer renderer;

	public Visual() {
		createDisplay();
		GUI.loadWindowDimensions(windowWidth, windowHeight);
		projectionTransformation = new ProjectionTransformation(windowWidth, windowHeight);
		renderer = new Renderer(projectionTransformation);
	}

	private void createDisplay() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!GLFW.glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE); // the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE); // the window will be resizable
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		windowID = GLFW.glfwCreateWindow(getWindowWidth(), getWindowHeight(), "Hemph", MemoryUtil.NULL, MemoryUtil.NULL); // Create the window
		if (windowID == MemoryUtil.NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()); // Get the resolution of the primary monitor
		GLFW.glfwSetWindowPos(windowID, (vidmode.width() - getWindowWidth()) / 2, (vidmode.height() - getWindowHeight()) / 2); // Center our window
		GLFW.glfwMakeContextCurrent(windowID); // Make the OpenGL context current
		GL.createCapabilities();
		GLFW.glfwSwapInterval(1); // Enable v-sync
		GLFW.glfwShowWindow(windowID); // Make the window visible
	}

	public void refresh(GameState state) {
		GL11.glViewport(0, 0, windowWidth, windowHeight);
		renderer.render(state);
		GLFW.glfwSwapBuffers(windowID);
	}

	public void cleanUp() {
		VAO.cleanUp();
		EBO.cleanUp();
		VBO.cleanUp();
		Texture.cleanup();
		Callbacks.glfwFreeCallbacks(windowID); // Release window callbacks
		GLFW.glfwDestroyWindow(windowID); // Release window
		GLFW.glfwTerminate(); // Terminate GLFW
		GLFW.glfwSetErrorCallback(null).free(); // Release the GLFWerrorfun
	}

	public boolean shouldCloseWindow() {
		return GLFW.glfwWindowShouldClose(windowID);
	}

	public long getWindowID() {
		return windowID;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public ProjectionTransformation getProjectionTransformation() {
		return projectionTransformation;
	}

}
