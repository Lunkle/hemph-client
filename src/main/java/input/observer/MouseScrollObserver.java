package input.observer;

import java.util.HashMap;
import java.util.Map;

import input.command.ScrollCommand;
import input.event.InputEvent;
import input.event.MouseScrollEvent;

public class MouseScrollObserver extends InputObserver {

	private Map<MouseCheck, ScrollCommand> commandMap;

	public MouseScrollObserver() {
		super();
		commandMap = new HashMap<>();
	}

	@Override
	public boolean handleEvent(InputEvent event) {
		if (event instanceof MouseScrollEvent) {
			MouseScrollEvent mouseScrollEvent = (MouseScrollEvent) event;
			for (MouseCheck check : commandMap.keySet()) {
				if (check.checkMouse()) {
					commandMap.get(check).execute(mouseScrollEvent.getOffset());
					return true;
				}
			}
		}
		return false;
	}

}
