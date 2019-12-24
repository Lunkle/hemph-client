package input.information;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

public enum Action {

	PRESS(GLFW.GLFW_PRESS),
	RELEASE(GLFW.GLFW_RELEASE),
	REPEAT(GLFW.GLFW_REPEAT),
	UNKNOWN(-1);

	private static Map<Integer, Action> actionMap;

	private int code;

	static {
		actionMap = new HashMap<>();
		for (Action action : EnumSet.allOf(Action.class)) {
			actionMap.put(action.code, action);
		}
	}

	private Action(int code) {
		this.code = code;
	}

	public static Action getAction(int actionCode) {
		return actionMap.get(actionCode);
	}

}