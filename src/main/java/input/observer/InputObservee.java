package input.observer;

import input.information.Actions;
import input.information.InputTypes;
import input.information.Keys;

public interface InputObservee {

	public void notifyObservers(InputTypes type, Keys input, Actions action, float[] data);

	public void addObserver(InputObserver newObserver);

	public void clearObservers();

	public default InputObserverNode getEmptyObserverNode() {
		return new EmptyObserverNode();
	}

}
