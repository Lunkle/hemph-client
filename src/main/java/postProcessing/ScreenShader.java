package postProcessing;

import graphics.rendering.ShaderProgram;

public class ScreenShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/java/graphics/screen/screenVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/main/java/graphics/screen/screenFragmentShader.txt";

	public ScreenShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		getAllUniformLocations();
	}

	@Override
	protected void getAllUniformLocations() {

	}

}
