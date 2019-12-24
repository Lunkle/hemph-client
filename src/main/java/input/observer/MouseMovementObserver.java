package input.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input.command.Command;
import input.event.InputEvent;
import input.event.MouseMovementEvent;

public class MouseMovementObserver extends InputObserver {

	private Map<MouseCheck, Command> commandMap;
	private List<MouseCheck> ordering;

	public MouseMovementObserver() {
		super();
		commandMap = new HashMap<>();
		ordering = new ArrayList<>();
	}

	@Override
	public boolean handleEvent(InputEvent event) {
		if (event instanceof MouseMovementEvent) {
			for (MouseCheck check : ordering) {
				if (check.checkMouse()) {
					commandMap.get(check).execute();
					return true;
				}
			}
		}
		return false;
	}

	public void addCheck(MouseCheck mouseCheck, Command command) {
		commandMap.put(mouseCheck, command);
		ordering.add(mouseCheck);
	}

}
