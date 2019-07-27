package input.command;

public class CommandLoader extends Thread {

	public CommandLoader() {
		urgentCommandLoading();
	}

	public void urgentCommandLoading() {
		Command.addCommand(new CloseWindowCommand());
		Command.addCommand(new MoveCameraForwardCommand());
		Command.addCommand(new MoveCameraBackwardCommand());
		Command.addCommand(new MoveCameraLeftCommand());
		Command.addCommand(new MoveCameraRightCommand());
		Command.addCommand(new MoveCameraUpCommand());
		Command.addCommand(new MoveCameraDownCommand());
	}

	@Override
	public void run() {
	}
}
