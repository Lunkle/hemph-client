package graphics.postProcessing;

import graphics.rendering.ShaderProgram;

public class ScreenShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/java/graphics/postProcessing/screenVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/main/java/graphics/postProcessing/screenFragmentShader.txt";

	public ScreenShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		getAllUniformLocations();
	}

	@Override
	protected void getAllUniformLocations() {

	}

}
