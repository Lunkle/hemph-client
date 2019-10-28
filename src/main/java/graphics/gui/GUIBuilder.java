package graphics.gui;

import java.util.ArrayList;
import java.util.List;

import graphics.texture.Texture;
import math.Vector2f;

public class GUIBuilder {

	private List<GUI> builtGuis = new ArrayList<>();

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
		int previousWindowWidth = this.windowWidth;
		int previousWindowHeight = this.windowHeight;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		for (GUI gui : builtGuis) {
			Vector2f size = gui.getSize();
			float previousWidth = previousWindowWidth * size.x;
			float previousHeight = previousWindowHeight * size.y;
			float scale = (float) Math.sqrt((float) (windowWidth * windowHeight) / (float) (previousWindowWidth * previousWindowHeight));
			Vector2f newSize = new Vector2f(scale * previousWidth / windowWidth, scale * previousHeight / windowHeight);

			Vector2f centerPosition = gui.getCenterPosition();
			Vector2f newPosition = new Vector2f(centerPosition.x - newSize.x / 2, centerPosition.y - newSize.y / 2);

			gui.setDimensions(newPosition.x, newPosition.y, newSize.x, newSize.y);
		}
	}

	public GUIBuilder newInstance() {
		gui = new GUI();
		return this;
	}

	/**
	 * Sets the dimensions of the gui in pixels.
	 * 
	 * @param posX   The x-position of the gui
	 * @param posY   The y-position of the gui
	 * @param width  The width of the gui
	 * @param height The height of the gui
	 * @return The original GUIBuilder
	 */
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
		builtGuis.add(gui);
		return gui;
	}

}
