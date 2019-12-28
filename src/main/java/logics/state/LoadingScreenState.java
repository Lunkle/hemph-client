package logics.state;

import org.lwjgl.glfw.GLFW;

import input.command.Command;
import input.command.KeyCommand;
import input.information.Key;
import input.observer.KeyObserver;
import loading.loader.ResourceLoadingTask;
import loading.loader.ResourcePack;
import loading.loader.UnconnectedData;

public class LoadingScreenState extends GameState {

	private boolean loadingItems = false;
	private boolean finishedLoading = false;
	private boolean connectingData = false;
	private boolean finishedConnectingData = false;
	private double startTime;

	private ResourceLoadingTask loadTask;
	private UnconnectedData connectTask;
	private ResourcePack resourcePack;
	private String loadFilePath;

	public LoadingScreenState(String filePath) {
		super();

		startTime = GLFW.glfwGetTime();

		this.loadFilePath = filePath;
	}

	@Override
	public void initialize() {
		clearObservers();

		KeyObserver printHi = new KeyObserver();
		printHi.addCommand(Key.KEY_E, new KeyCommand(new Command(() -> {}), new Command(() -> System.out.println("lmao"))));
		addKeyObserver(printHi);
	}

	private void queueLoadRawDataTask() {
		loadTask = getLoaderThread().generateNewTask();

		resourcePack = new ResourcePack();
		resourcePack.load(loadFilePath);

		for (String name : resourcePack.getNameList()) {
			loadTask.addItem(resourcePack.getRawData(name), resourcePack.getFilepath(name));
		}

		getLoaderThread().queueTask(loadTask);
	}

	private void queueConnectTask() {
		resourcePack.generateUnconnectedData();
		connectTask = getConnecter().generateNewTask();
		for (String name : resourcePack.getNameList()) {
			connectTask.addData(resourcePack.getData(name), resourcePack.getRawData(name));
		}
		getConnecter().queueTask(connectTask);
	}

	@Override
	public void updateWindowDimensions(int windowWidth, int windowHeight) {}

	@Override
	public void update() {
		if (!loadingItems) {
			queueLoadRawDataTask();
			loadingItems = true;
		} else if (!finishedLoading) {
			double percentage = loadTask.getProgressPercentage();
			if (percentage < 100) {
				System.out.println("Reading Files: " + Math.round(percentage) + "%");
			} else {
				System.out.println("Reading Files: 100%");
				finishedLoading = true;
			}
		} else if (!connectingData) {
			queueConnectTask();
			connectingData = true;
		} else if (!finishedConnectingData) {
			double percentage = connectTask.getProgressPercentage();
			if (percentage < 100) {
				System.out.println("Loading: " + Math.round(percentage) + "%");
			} else {
				System.out.println("Loading: 100%");
				finishedConnectingData = true;
			}
		} else {
			double endTime = GLFW.glfwGetTime();
			double timeTaken = endTime - startTime;
			System.out.println("Finished loading in " + timeTaken + " seconds.");
			transition(new PlayGameState(resourcePack));
		}
	}

}
