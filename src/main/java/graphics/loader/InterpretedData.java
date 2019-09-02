package graphics.loader;

import graphics.gui.ScreenRawMeshData;
import graphics.postProcessing.EmptyTextureRawData;
import graphics.texture.ByteBufferImageRawData;
import graphics.vao.RawMeshData;

/**
 * Represents any class with data that is ready to be used by opengl directly.
 * 
 * @author Donny
 *
 */
public interface InterpretedData {

	public default void interpret(ByteBufferImageRawData data) {}

	public default void interpret(RawMeshData data) {}

	public default void interpret(ScreenRawMeshData data) {}

	public default void interpret(EmptyTextureRawData emptyTextureRawData) {}

}
