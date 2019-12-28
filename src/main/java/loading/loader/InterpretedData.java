package loading.loader;

import loading.framebuffer.EmptyTextureRawData;
import loading.screen.ScreenRawMeshData;
import loading.skybox.SkyboxMeshRawData;
import loading.skybox.SkyboxTextureRawData;
import loading.texture.ByteBufferImageRawData;
import loading.vao.RawMeshData;

/**
 * Represents any class with data that is ready to be used by opengl directly or
 * is ready to be used in our own code.
 * 
 * @author Donny
 *
 */
public interface InterpretedData {

	public default void interpret(SkyboxMeshRawData data) {}

	public default void interpret(ByteBufferImageRawData data) {}

	public default void interpret(RawMeshData data) {}

	public default void interpret(ScreenRawMeshData data) {}

	public default void interpret(EmptyTextureRawData data) {}

	public default void interpret(SkyboxTextureRawData data) {}

}
