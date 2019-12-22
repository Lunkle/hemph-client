package loading.loader;

import java.util.ArrayList;
import java.util.List;

public class GraphicsDataConnecter {

	private List<UnconnectedData> unconnectedData;
	private int numberOfData = 0;

	public GraphicsDataConnecter() {
		unconnectedData = new ArrayList<>();
	}

	public void connectResources() {
		if (numberOfData > 0) {
			UnconnectedData nextData = unconnectedData.get(0);
			unconnectedData.remove(0);
			List<InterpretedData> interpretedData = nextData.getInterpretedDataList();
			List<RawData> rawData = nextData.getRawDataList();
			for (int i = 0; i < interpretedData.size(); i++) {
				rawData.get(i).accept(interpretedData.get(i));
				nextData.incrementNumberOfConnectedData();
			}
			nextData.setConnected();
			nextData.notifyObject();
			numberOfData--;
		}
	}

	public UnconnectedData generateNewTask() {
		return new UnconnectedData();
	}

	public void queueTask(UnconnectedData task) {
		unconnectedData.add(task);
		numberOfData++;
	}

}