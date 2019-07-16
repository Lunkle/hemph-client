package game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Visual {

	private static long windowID;

	private static int windowWidth = 1280;
	private static int windowHeight = 720;

//	private GamePage page;
//	private GameScene scene;
//	private MasterRenderer renderer;

	public Visual() {
		createDisplay();
		init();
	}

	public void init() {
//		page = new GamePage();
//		scene = new GameScene();
//		renderer = new MasterRenderer();
		GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	}

	public void refresh() {
//		renderer.render(page, scene);
		updateDisplay();
	}

	public void cleanUp() {
		Callbacks.glfwFreeCallbacks(windowID); // Release window callbacks
		GLFW.glfwDestroyWindow(windowID); // Release window
		GLFW.glfwTerminate(); // Terminate GLFW
		GLFW.glfwSetErrorCallback(null).free(); // Release the GLFWerrorfun
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
		windowID = GLFW.glfwCreateWindow(getWindowWidth(), getWindowHeight(), "Hello World!", MemoryUtil.NULL, MemoryUtil.NULL); // Create the window
		if (windowID == MemoryUtil.NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()); // Get the resolution of the primary monitor
		GLFW.glfwSetWindowPos(windowID, (vidmode.width() - getWindowWidth()) / 2, (vidmode.height() - getWindowHeight()) / 2); // Center our window
		GLFW.glfwMakeContextCurrent(windowID); // Make the OpenGL context current
		GLFW.glfwSwapInterval(1); // Enable v-sync
		GLFW.glfwShowWindow(windowID); // Make the window visible
		GL.createCapabilities();
	}

	private void updateDisplay() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		GLFW.glfwSwapBuffers(windowID);
	}

	public boolean shouldCloseWindow() {
		return GLFW.glfwWindowShouldClose(windowID);
	}

	public static long getWindowID() {
		return windowID;
	}

	public static int getWindowWidth() {
		return windowWidth;
	}

	public static void setWindowWidth(int windowWidth) {
		Visual.windowWidth = windowWidth;
	}

	public static int getWindowHeight() {
		return windowHeight;
	}

	public static void setWindowHeight(int windowHeight) {
		Visual.windowHeight = windowHeight;
	}

}
