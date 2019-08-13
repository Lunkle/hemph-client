package graphics.loader;

import java.util.HashMap;
import java.util.Map;

public class ResourceLoadingTask extends Thread {

	private Map<RawData, String> pathMap;
	private Map<RawData, Long> byteMap;
	private long loadedBytes = 0;
	private long totalBytes = 0;

	protected ResourceLoadingTask() {
		pathMap = new HashMap<>();
		byteMap = new HashMap<>();
	}

	protected void calculateTotalBytes() {
		for (RawData loadItem : pathMap.keySet()) {
			long fileByteSize = getBytes(loadItem);
			totalBytes += fileByteSize;
		}
	}

	private long getBytes(RawData loadItem) {
		Long bytes = byteMap.get(loadItem);
		if (bytes != null) {
			return bytes.longValue();
		} else {
			long fileByteSize = loadItem.getSize(pathMap.get(loadItem));
			byteMap.put(loadItem, fileByteSize);
			return fileByteSize;
		}

	}

	public void addItem(RawData data, String path) {
		pathMap.put(data, path);
	}

	protected void loadResources() {
		for (RawData loadItem : pathMap.keySet()) {
			loadItem.load(pathMap.get(loadItem));
			loadedBytes += byteMap.get(loadItem);
		}
	}

	public int getProgressPercentage() {
		if (totalBytes == 0) {
			return 100;
		}
		return (int) Math.round(100.0 * loadedBytes / totalBytes);
	}

}
