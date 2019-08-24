package input.command;

public class NullCommand extends Command {

	public NullCommand() {
		super(() -> {});
	}

}
