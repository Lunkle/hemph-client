package graphics.loader;

import java.util.HashMap;
import java.util.Map;

import graphics.texture.Texture;
import graphics.vao.VAO;

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
