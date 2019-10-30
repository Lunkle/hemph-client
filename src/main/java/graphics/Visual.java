package graphics;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import graphics.gui.GUIBuilder;
import graphics.rendering.MasterRenderer;
import graphics.texture.Texture;
import graphics.transformation.ProjectionWrapper;
import graphics.vao.EBO;
import graphics.vao.VAO;
import graphics.vao.VBO;
import logics.state.GameStateWrapper;

public class Visual {

	private long windowID;

	private final int DEFAULT_WINDOW_WIDTH = 1280;
	private final int DEFAULT_WINDOW_HEIGHT = 720;

	private int currentWindowWidth = DEFAULT_WINDOW_WIDTH;
	private int currentWindowHeight = DEFAULT_WINDOW_HEIGHT;

	private MasterRenderer renderer;
	private GUIBuilder guiBuilder;

	public Visual(GameStateWrapper stateWrapper) {
		createDisplay();
		this.guiBuilder = stateWrapper.getGuiBuilder();
		guiBuilder.loadWindowDimensions(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		renderer = new MasterRenderer(stateWrapper, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	}

	public void updateWindowDimensions(int windowWidth, int windowHeight) {
		currentWindowWidth = windowWidth;
		currentWindowHeight = windowHeight;
		guiBuilder.loadWindowDimensions(windowWidth, windowHeight);
		renderer.loadWindowDimensions(windowWidth, windowHeight);
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

	public void refresh() {
		GL11.glViewport(0, 0, currentWindowWidth, currentWindowHeight);
		renderer.render();
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
		return DEFAULT_WINDOW_WIDTH;
	}

	public int getWindowHeight() {
		return DEFAULT_WINDOW_HEIGHT;
	}

	public ProjectionWrapper getProjectionWrapper() {
		return renderer.getProjectionWrapper();
	}

}
