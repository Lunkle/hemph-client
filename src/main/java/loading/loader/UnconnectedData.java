package loading.loader;

import java.util.ArrayList;
import java.util.List;

public class UnconnectedData {

	private Object notifierObject;
	private List<InterpretedData> interpretedDataList;
	private List<RawData> rawDataList;
	private boolean connected = false;

	private int totalData = 0;
	private int connectedData = 0;

	protected UnconnectedData() {
		interpretedDataList = new ArrayList<>();
		rawDataList = new ArrayList<>();
	}

	public void addData(InterpretedData interpretedData, RawData rawData) {
		totalData++;
		interpretedDataList.add(interpretedData);
		rawDataList.add(rawData);
	}

	protected List<InterpretedData> getInterpretedDataList() {
		return interpretedDataList;
	}

	protected List<RawData> getRawDataList() {
		return rawDataList;
	}

	public void setConnected() {
		connected = true;
	}

	public boolean isConnected() {
		return connected;
	}

	public void addNotifier(Object object) {
		notifierObject = object;
	}

	public void notifyObject() {
		if (notifierObject != null) {
			synchronized (notifierObject) {
				notifierObject.notify();
			}
		}
	}

	/**
	 * Just increments the variable so that the percentage can be calculated.
	 * (Typically it's the loading screen state that does the calculations)
	 */
	public void incrementNumberOfConnectedData() {
		connectedData++;
	}

	public double getProgressPercentage() {
		if (connectedData == 0) {
			return 0;
		} else {
			return 100 * (double) connectedData / totalData;
		}
	}

}
