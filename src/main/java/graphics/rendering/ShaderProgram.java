package graphics.rendering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;

public abstract class ShaderProgram {

	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;

	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	public ShaderProgram(String vertexFile, String fragmentFile) {
		programId = GL20.glCreateProgram();
		vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		GL20.glAttachShader(programId, vertexShaderId);
		GL20.glAttachShader(programId, fragmentShaderId);
		GL20.glLinkProgram(programId);
		GL20.glDetachShader(programId, vertexShaderId);
		GL20.glDetachShader(programId, fragmentShaderId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		GL20.glValidateProgram(programId);
	}

	public void start() {
		GL20.glUseProgram(programId);
	}

	public void stop() {
		GL20.glUseProgram(0);
	}

	public void cleanUp() {
		stop();
		GL20.glDeleteProgram(programId);
	}

	/**
	 * Finds all of the uniform locations in the shaders. Should be called in every
	 * shader program constructor.
	 */
	protected abstract void getAllUniformLocations();

	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programId, uniformName);
	}

	protected void loadInt(int location, int value) {
		GL20.glUniform1i(location, value);
	}

	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}

	protected void loadVector(int location, Vector2f vector) {
		GL20.glUniform2f(location, vector.x, vector.y);
	}

	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}

	protected void loadBoolean(int location, boolean value) {
		GL20.glUniform1f(location, value ? 1 : 0);
	}

	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
	}

	private static int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}

}
