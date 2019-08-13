package input.information;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

public enum Actions {

	PRESS(GLFW.GLFW_PRESS),
	RELEASE(GLFW.GLFW_RELEASE),
	REPEAT(GLFW.GLFW_REPEAT),
	UNKNOWN(-1);

	private static Map<Integer, Actions> actionMap;

	private int code;

	static {
		actionMap = new HashMap<>();
		for (Actions action : EnumSet.allOf(Actions.class)) {
			actionMap.put(action.code, action);
		}
	}

	private Actions(int code) {
		this.code = code;
	}

	public static Actions getAction(int actionCode) {
		return actionMap.get(actionCode);
	}

}