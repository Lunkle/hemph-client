package loading.loader;

import java.util.HashMap;
import java.util.Map;

import graphics.texture.Texture;
import graphics.vao.VAO;

/**
 * Just a clean way to "transport" interpreted data across states. Without this
 * class you would have massive constructors (which look uguh-lee!)
 * 
 * However, there is a downside: if the receiving side erroneously believes a
 * certain piece of data is in this resource pack and tries to fetch it and use
 * it, the error will only show up at run time.
 * 
 * @author Donny
 *
 */
public class ResourcePack {

	private Map<String, Texture> textureMap;
	private Map<String, VAO> meshMap;

	public ResourcePack() {
		textureMap = new HashMap<>();
		meshMap = new HashMap<>();
	}

	public void addTexture(Texture texture, String name) {
		textureMap.put(name, texture);
	}

	public void addMesh(VAO mesh, String name) {
		meshMap.put(name, mesh);
	}

	public Texture getTexture(String name) {
		return textureMap.get(name);
	}

	public VAO getMesh(String name) {
		return meshMap.get(name);
	}

}
