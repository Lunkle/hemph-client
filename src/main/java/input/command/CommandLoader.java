package input.command;

public class CommandLoader extends Thread {

	public CommandLoader() {
		urgentCommandLoading();
	}

	public void urgentCommandLoading() {
		Command.addCommand(new CloseWindowCommand());
		Command.addCommand(new MoveCameraForwardCommand());
	}

	@Override
	public void run() {
	}
}
