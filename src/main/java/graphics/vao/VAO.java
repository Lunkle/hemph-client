package graphics.vao;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import loading.loader.InterpretedData;
import loading.screen.ScreenRawMeshData;
import loading.vao.RawMeshData;

/**
 * An attribute object stores all the information for each vertex of a mesh. It
 * stores them in the form of buffer objects
 * 
 * @author Jay
 *
 */
public class VAO implements InterpretedData {

	private static List<Integer> vaos = new ArrayList<>();

	protected int vaoID;
	protected int vertexCount;

	/**
	 * Must bind and unbind VAO before attaching VBO/EBOs. The VAO is already
	 * deafult bound upon creation so do not create any other. VAOs until
	 * finished attaching VBO and EBOs for the most recently created vao.
	 * 
	 * @param data
	 */
	protected void attachEBO(int[] data) {
		EBO ebo = new EBO();
		ebo.bindEBO();
		ebo.loadData(data);
	}

	protected void attachVBO(int attributeIndex, int dimensions, float[] data) {
		VBO vbo = new VBO();
		vbo.bindVBO();
		vbo.loadData(data);
		GL20.glVertexAttribPointer(attributeIndex, dimensions, GL11.GL_FLOAT, false, 0, 0);
		vbo.unbindVBO();
	}

	/**
	 * Binding the attribute object so opengl knows to write to it.
	 */
	public void bindVAO() {
		GL30.glBindVertexArray(vaoID);
	}

	/**
	 * Remeber to unbind after finished attaching VBOS.
	 */
	public void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	public int getVaoId() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	/**
	 * Method for deleting all the attribute objects when program is closing
	 * Called in Visual class's cleanUp method.
	 */
	public static void cleanUp() {
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
	}

	@Override
	public void interpret(RawMeshData data) {
		vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		bindVAO();
		attachVBO(0, 3, data.getPositionsArray());
		attachVBO(1, 2, data.getTextureArray());
		attachVBO(2, 3, data.getNormalsArray());
		int[] indices = data.getIndicesArray();
		attachEBO(indices);
		vertexCount = indices.length;
		unbindVAO();
	}

	@Override
	public void interpret(ScreenRawMeshData data) {
		vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		bindVAO();
		attachVBO(0, 2, data.getQuadVertices());
		vertexCount = 6;
		unbindVAO();
	}

}
