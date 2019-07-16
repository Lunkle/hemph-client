package input.key;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import input.command.Command;

public abstract class KeyHandler extends GLFWKeyCallback {

	private Map<Integer, Command> keyMap = new HashMap<>();

	public KeyHandler() {
		setKeyCommands();
	}

	@Override
	public final void invoke(long window, int key, int scancode, int action, int mods) {
		Command command = keyMap.get(key);
		if (command != null) {
			if (action == GLFW.GLFW_PRESS)
				command.onPress();
			else
				command.onRelease();
		}
	}

	public abstract void setKeyCommands();

	public final void addKeyCommand(int key, Command command) {
		keyMap.put(key, command);
	}

	public final void addKeyCommand(int key, String commandID) {
		Command putCommand = Command.getCommand(commandID);
		if (putCommand != null) {
			keyMap.put(key, putCommand);
		} else {
			System.out.println("Invalid command");
		}
	}

}
