package input.observer;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

/**
 * The empty observer node is an observer that does absolutely nothing when
 * given data. Think of it as a "dead end" for an observer chain.
 * 
 * @author Donny
 *
 */
public class EmptyObserverNode extends InputObserverNode {

	protected EmptyObserverNode() {
		super((InputTypes type, Keys input, Actions action, float[] data) -> {});
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EmptyObserverNode) {
			return true;
		}
		return false;
	}

}
