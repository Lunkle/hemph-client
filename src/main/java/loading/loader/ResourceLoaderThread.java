package loading.loader;

import java.util.ArrayList;
import java.util.List;

public class ResourceLoaderThread extends Thread {

	private boolean isDone = false;
	private List<ResourceLoadingTask> queuedTasks;
	private int numberOfTasks = 0;

	public ResourceLoaderThread() {
		queuedTasks = new ArrayList<>();
	}

	@Override
	public void run() {
		while (!isDone) {
			if (numberOfTasks > 0) {
				ResourceLoadingTask currentTask = queuedTasks.get(0);
				queuedTasks.remove(0);
				currentTask.loadResources();
				numberOfTasks--;
			}
			synchronized (this) {
				while (!isDone && numberOfTasks == 0) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public ResourceLoadingTask generateNewTask() {
		return new ResourceLoadingTask();
	}

	public void queueTask(ResourceLoadingTask task) {
		task.calculateTotalBytes();
		queuedTasks.add(task);
		numberOfTasks++;
		synchronized (this) {
			notify();
		}
	}

	public void end() {
		isDone = true;
		synchronized (this) {
			notify();
		}
	}

}
