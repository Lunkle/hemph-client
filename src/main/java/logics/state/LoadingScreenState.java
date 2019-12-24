package logics.state;

import org.lwjgl.glfw.GLFW;

import graphics.texture.Texture;
import graphics.vao.VAO;
import input.command.Command;
import input.command.KeyCommand;
import input.information.Keys;
import input.observer.KeyObserver;
import loading.loader.ResourceLoadingTask;
import loading.loader.ResourcePack;
import loading.loader.UnconnectedData;
import loading.texture.ByteBufferImageRawData;
import loading.vao.OBJMeshRawData;
import logics.globe.GlobeRawData;

public class LoadingScreenState extends GameState {

	private boolean loadingItems = false;
	private boolean finishedLoading = false;
	private boolean connectingData = false;
	private boolean finishedConnectingData = false;
	private boolean finishedConnectingFirstBatch = false;
	private boolean finishedConnectingSecondBatch = false;
	private double startTime;

	private ResourceLoadingTask loadTask;
	private UnconnectedData connectTask;
	private ResourcePack resourcePack;
	private String loadFilePath;

	private ByteBufferImageRawData dukerawTextureData;
	private Texture dukeTexture;
	private ByteBufferImageRawData tableRawTextureData;
	private Texture tableTexture;
	private ByteBufferImageRawData tableSpecularRawTextureData;
	private Texture tableSpecularTexture;
	private OBJMeshRawData tableRawMeshData;
	private VAO tableMesh;
	private ByteBufferImageRawData roomRawTextureData;
	private Texture roomTexture;
	private ByteBufferImageRawData roomSpecularRawTextureData;
	private Texture roomSpecularTexture;
	private OBJMeshRawData roomRawMeshData;
	private VAO roomMesh;
	private ByteBufferImageRawData globeStandRawTextureData;
	private Texture globeStandTexture;
	private ByteBufferImageRawData globeStandSpecularRawTextureData;
	private Texture globeStandSpecularTexture;
	private OBJMeshRawData globeStandRawMeshData;
	private VAO globeStandMesh;
	private ByteBufferImageRawData candleRawTextureData;
	private Texture candleTexture;
	private ByteBufferImageRawData candleSpecularRawTextureData;
	private Texture candleSpecularTexture;
	private OBJMeshRawData candleRawMeshData;
	private VAO candleMesh;
	private ByteBufferImageRawData worldRawTextureData;
	private Texture worldTexture;
	private ByteBufferImageRawData globeSpecularRawTextureData;
	private Texture globeSpecularTexture;
	private VAO globeMesh;
	private ByteBufferImageRawData greenRawTextureData;
	private Texture greenTexture;
	private ByteBufferImageRawData redRawTextureData;
	private Texture redTexture;
	private ByteBufferImageRawData blueRawTextureData;
	private Texture blueTexture;
	private ByteBufferImageRawData arrowSpecularRawTextureData;
	private Texture arrowSpecularTexture;
	private OBJMeshRawData arrowRawMeshData;
	private VAO arrowMesh;

	private GlobeRawData globeRawMeshData;

	public LoadingScreenState(String filePath) {
		super();

		startTime = GLFW.glfwGetTime();

		setFlagToClearObservers();

		this.loadFilePath = filePath;

		KeyObserver printHi = new KeyObserver();
		printHi.addCommand(Keys.KEY_E, new KeyCommand(new Command(() -> {
		}), new Command(() -> System.out.println("lmao"))));
		addKeyObserver(printHi);
	}

	@Override
	protected void initialize() {

	}

	private void queueLoadRawDataTask() {
		loadTask = getLoaderThread().generateNewTask();

		resourcePack = new ResourcePack();
		resourcePack.load(loadFilePath);

		for (String name : resourcePack.getNameList()) {
			loadTask.addItem(resourcePack.getRawData(name), resourcePack.getFilepath(name));
		}
		globeRawMeshData = new GlobeRawData();
		loadTask.addItem(globeRawMeshData, "");

		getLoaderThread().queueTask(loadTask);
	}

	// private synchronized void connectFirstDataBatch() {
	// UnconnectedData unconnectedData = getConnecter().generateNewTask();
	// dukeTexture = new Texture();
	// unconnectedData.addData(dukeTexture, dukerawTextureData);
	// tableTexture = new Texture();
	// unconnectedData.addData(tableTexture, tableRawTextureData);
	// tableSpecularTexture = new Texture();
	// unconnectedData.addData(tableSpecularTexture,
	// tableSpecularRawTextureData);
	// tableMesh = new VAO();
	// unconnectedData.addData(tableMesh, tableRawMeshData);
	// roomTexture = new Texture();
	// unconnectedData.addData(roomTexture, roomRawTextureData);
	// roomSpecularTexture = new Texture();
	// unconnectedData.addData(roomSpecularTexture, roomSpecularRawTextureData);
	// roomMesh = new VAO();
	// unconnectedData.addData(roomMesh, roomRawMeshData);
	// globeStandTexture = new Texture();
	// unconnectedData.addData(globeStandTexture, globeStandRawTextureData);
	// globeStandSpecularTexture = new Texture();
	// unconnectedData.addData(globeStandSpecularTexture,
	// globeStandSpecularRawTextureData);
	// globeStandMesh = new VAO();
	// unconnectedData.addData(globeStandMesh, globeStandRawMeshData);
	// candleTexture = new Texture();
	// unconnectedData.addData(candleTexture, candleRawTextureData);
	// candleSpecularTexture = new Texture();
	// unconnectedData.addData(candleSpecularTexture,
	// candleSpecularRawTextureData);
	// candleMesh = new VAO();
	// unconnectedData.addData(candleMesh, candleRawMeshData);
	//
	// unconnectedData.addNotifier(this);
	// getConnecter().queueTask(unconnectedData);
	// while (!unconnectedData.isConnected()) {
	// try {
	// wait();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// private synchronized void connectSceondDataBatch() {
	// UnconnectedData unconnectedData = getConnecter().generateNewTask();
	//
	// greenTexture = new Texture();
	// unconnectedData.addData(greenTexture, greenRawTextureData);
	// redTexture = new Texture();
	// unconnectedData.addData(redTexture, redRawTextureData);
	// blueTexture = new Texture();
	// unconnectedData.addData(blueTexture, blueRawTextureData);
	// arrowSpecularTexture = new Texture();
	// unconnectedData.addData(arrowSpecularTexture,
	// arrowSpecularRawTextureData);
	// arrowMesh = new VAO();
	// unconnectedData.addData(arrowMesh, arrowRawMeshData);
	//
	// worldTexture = new Texture();
	// unconnectedData.addData(worldTexture, worldRawTextureData);
	// globeSpecularTexture = new Texture();
	// unconnectedData.addData(globeSpecularTexture,
	// globeSpecularRawTextureData);
	//
	// unconnectedData.addNotifier(this);
	// getConnecter().queueTask(unconnectedData);
	//
	// while (!unconnectedData.isConnected()) {
	// try {
	// wait();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	private void queueConnectTask() {
		resourcePack.generateUnconnectedData();
		connectTask = getConnecter().generateNewTask();
		for (String name : resourcePack.getNameList()) {
			connectTask.addData(resourcePack.getData(name), resourcePack.getRawData(name));
		}
		globeMesh = new VAO();
		connectTask.addData(globeMesh, globeRawMeshData);
		getConnecter().queueTask(connectTask);
	}

	private void finalizeResourcePack() {
		resourcePack.addData(globeMesh, "globeMesh");
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
			finalizeResourcePack();
			double endTime = GLFW.glfwGetTime();
			double timeTaken = endTime - startTime;
			System.out.println("Finished loading in " + timeTaken + " seconds.");
			transition(new PlayGameState(resourcePack));
		}
	}

}
