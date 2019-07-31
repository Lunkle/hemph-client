package graphics.gui;

import graphics.rendering.ShaderProgram;
import math.Matrix4f;

public class GUIShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/java/graphics/gui/guiVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/main/java/graphics/gui/guiFragmentShader.txt";

	private int location_modelMatrix;

	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		getAllUniformLocations();
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelMatrix = super.getUniformLocation("modelMatrix");
	}

	public void loadModelMatrix(Matrix4f matrix) {
		super.loadMatrix(location_modelMatrix, matrix);
	}
}
