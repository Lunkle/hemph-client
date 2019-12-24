package input.observer;

import org.lwjgl.glfw.GLFW;

import graphics.Visual;
import input.command.Command;
import input.command.KeyCommand;
import input.command.NullCommand;
import input.information.Key;

public class KeyEscapeExitObserver extends KeyObserver {

	public KeyEscapeExitObserver(Visual visuals) {
		super();
		Command closeWindow = new Command(() -> GLFW.glfwSetWindowShouldClose(visuals.getWindowID(), true));
		addCommand(Key.KEY_ESC, new KeyCommand(new NullCommand(), closeWindow));
	}

}
