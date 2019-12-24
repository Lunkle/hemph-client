package input.command;

public class Command {

	Runnable command;

	public Command(Runnable command) {
		this.command = command;
	}

	public void execute() {
		command.run();
	}

	public static Command combine(Command c1, Command c2) {
		Command c = new Command(() -> {
			c1.execute();
			c2.execute();
		});
		return c;
	}

}
