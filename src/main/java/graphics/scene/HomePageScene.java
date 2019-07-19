package graphics.scene;

import graphics.gui.GUI;
import graphics.gui.GUIBuilder;

public class HomePageScene extends GameScene {

	public HomePageScene() {
		super();
		GUI testGUI = GUIBuilder.newInstance().setDimensions(0, 0, 1280, 300).create();
		GUI testGUI2 = GUIBuilder.newInstance().setDimensions(800, 600, 200, 300).create();
		addGUI(testGUI);
		addGUI(testGUI2);
	}

}
