package input.command;

public class Command {

	Runnable command;

	public Command(Runnable command) {
		this.command = command;
	}

	public void execute() {
		command.run();
	}

}
