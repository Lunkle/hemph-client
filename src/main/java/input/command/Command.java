package input.command;

import java.util.HashMap;
import java.util.Map;

import logics.GameState;

public abstract class Command {

	private static Map<String, Command> commandMap = new HashMap<>();

	protected static GameState state;

	protected String commandID = "";

	public abstract void onPress();

	public abstract void onRelease();

	public final static void addCommand(Command command) {
		if (!commandMap.containsKey(command.commandID)) {
			commandMap.put(command.commandID, command);
		}
	}

	public final static Command getCommand(String commandID) {
		return commandMap.get(commandID);
	}

}
