package input.command;

public class KeyCommand {

	public Command onPress;
	public Command onRelease;

	public KeyCommand(Command onPress, Command onRelease) {
		this.onPress = onPress;
		this.onRelease = onRelease;
	}

}
