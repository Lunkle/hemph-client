package graphics.skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import graphics.texture.SkyboxTexture;

public class Skybox {

	private SkyboxTexture texture;
	private int intensity;

	public Skybox(SkyboxTexture texture, int intensity) {
		this.texture = texture;
		this.intensity = intensity;
	}

	public void activateTextures() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture.getID());
	}

	public SkyboxTexture getTexture() {
		return texture;
	}

	public void setTexture(SkyboxTexture texture) {
		this.texture = texture;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

}
