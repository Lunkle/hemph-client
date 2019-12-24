package input.information;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

public enum Key {

	MOUSE_LEFT(GLFW.GLFW_MOUSE_BUTTON_1),
	MOUSE_RIGHT(GLFW.GLFW_MOUSE_BUTTON_2),
	MOUSE_MIDDLE(GLFW.GLFW_MOUSE_BUTTON_3),
	KEY_ESC(GLFW.GLFW_KEY_ESCAPE),
	KEY_W(GLFW.GLFW_KEY_W),
	KEY_A(GLFW.GLFW_KEY_A),
	KEY_S(GLFW.GLFW_KEY_S),
	KEY_D(GLFW.GLFW_KEY_D),
	KEY_Q(GLFW.GLFW_KEY_Q),
	KEY_E(GLFW.GLFW_KEY_E),
	UNKNOWN(-1);

	private static Map<Integer, Key> keysMap;

	private int code;

	static {
		keysMap = new HashMap<>();
		for (Key key : EnumSet.allOf(Key.class)) {
			keysMap.put(key.code, key);
		}
	}

	private Key(int code) {
		this.code = code;
	}

	public static Key getKey(int keyCode) {
		return keysMap.get(keyCode);
	}

}
