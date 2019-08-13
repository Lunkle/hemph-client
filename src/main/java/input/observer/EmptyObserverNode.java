package input.observer;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public class EmptyObserverNode extends InputObserverNode {

	protected EmptyObserverNode() {
		super((InputTypes type, Keys input, Actions action, float[] data) -> {});
	}

}
