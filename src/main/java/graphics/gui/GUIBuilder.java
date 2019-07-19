package graphics.gui;

public class GUIBuilder {

	private GUI gui;

	private GUIBuilder() {
		gui = new GUI();
	}

	public static GUIBuilder newInstance() {
		GUIBuilder guibuilder = new GUIBuilder();
		return guibuilder;
	}

	public GUIBuilder setDimensions(float posX, float posY, float width, float height) {
		gui.setDimensions(posX, posY, width, height);
		return this;
	}

	public GUI create() {
		return gui;
	}

}
