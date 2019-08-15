package graphics.loader;

import graphics.gui.GUIMeshData;
import graphics.texture.ByteBufferImageRawData;
import graphics.vao.MeshRawData;

/**
 * Represents any class with data that is ready to be used by opengl directly.
 * 
 * @author Donny
 *
 */
public interface InterpretedData {

	public default void interpret(ByteBufferImageRawData data) {}

	public default void interpret(GUIMeshData guiMeshData) {}

	public default void interpret(MeshRawData data) {}

}
