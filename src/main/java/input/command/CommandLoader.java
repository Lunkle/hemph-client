package input.command;

public class CommandLoader extends Thread {

	public CommandLoader() {
		urgentCommandLoading();
	}

	public void urgentCommandLoading() {
		Command.addCommand(new CloseWindowCommand());
	}

	@Override
	public void run() {

	}
}
