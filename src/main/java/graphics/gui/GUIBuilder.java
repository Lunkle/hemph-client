package graphics.gui;

import graphics.texture.Texture;

public class GUIBuilder {

	private int windowWidth;
	private int windowHeight;
	private GUI gui;

	public GUIBuilder() {
		this(1280, 760); // These are the default screen dimensions
	}

	public GUIBuilder(int windowWidth, int windowHeight) {
		loadWindowDimensions(windowWidth, windowHeight);
	}

	public void loadWindowDimensions(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}

	public GUIBuilder newInstance() {
		gui = new GUI();
		return this;
	}

	public GUIBuilder setDimensions(float posX, float posY, float width, float height) {
		gui.setDimensions(posX / windowWidth, posY / windowHeight, width / windowWidth, height / windowHeight);
		return this;
	}

	public GUIBuilder setTexture(Texture texture) {
		texture.setAsDiffuseTexture();
		gui.setTexture(texture);
		return this;
	}

	public GUI create() {
		return gui;
	}

}
