package input.command;

public class ScrollCommand {

	private ParameterRunnable command;

	public ScrollCommand(ParameterRunnable command) {
		this.command = command;
	}

	public void execute(float offset) {
		command.run(offset);
	}

}
