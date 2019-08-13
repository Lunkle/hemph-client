package input.observer;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public interface InputObserver {

	public void handleEvent(InputTypes type, Keys input, Actions action, float[] data);

}