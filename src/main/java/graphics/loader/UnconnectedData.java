package graphics.loader;

import java.util.ArrayList;
import java.util.List;

public class UnconnectedData {

	private Object notifierObject;
	private List<InterpretedData> interpretedDataList;
	private List<RawData> rawDataList;
	private boolean connected = false;

	protected UnconnectedData() {
		interpretedDataList = new ArrayList<>();
		rawDataList = new ArrayList<>();
	}

	public void addData(InterpretedData interpretedData, RawData rawData) {
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

}
