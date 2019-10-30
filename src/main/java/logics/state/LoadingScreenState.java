package logics.state;

import org.lwjgl.glfw.GLFW;

import graphics.loader.ResourceLoadingTask;
import graphics.loader.ResourcePack;
import graphics.loader.UnconnectedData;
import graphics.texture.ByteBufferImageRawData;
import graphics.texture.Texture;
import graphics.vao.OBJMeshRawData;
import graphics.vao.VAO;
import input.command.Command;
import input.command.KeyCommand;
import input.information.Keys;
import input.observer.KeyObserver;
import logics.globe.GlobeRawData;

public class LoadingScreenState extends GameState {

	private boolean loadedItems = false;
	private boolean finishedLoading = false;
	private boolean finishedConnectingFirstBatch = false;
	private boolean finishedConnectingSecondBatch = false;
	private double startTime;

	private ResourceLoadingTask loadTask;
	private ResourcePack resourcePack;

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
	private GlobeRawData globeRawMeshData;
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

	public LoadingScreenState() {
		super();

		startTime = GLFW.glfwGetTime();

		setFlagToClearObservers();

		KeyObserver printHi = new KeyObserver();
		printHi.addCommand(Keys.KEY_E, new KeyCommand(new Command(() -> {}), new Command(() -> System.out.println("lmao"))));
		addKeyObserver(printHi);

	}

	private void addLoadRawDataTask() {
		loadTask = getLoaderThread().generateNewTask();

		dukerawTextureData = new ByteBufferImageRawData();
		tableRawTextureData = new ByteBufferImageRawData();
		tableSpecularRawTextureData = new ByteBufferImageRawData();
		tableRawMeshData = new OBJMeshRawData();
		roomRawTextureData = new ByteBufferImageRawData();
		roomSpecularRawTextureData = new ByteBufferImageRawData();
		roomRawMeshData = new OBJMeshRawData();
		globeStandRawTextureData = new ByteBufferImageRawData();
		globeStandSpecularRawTextureData = new ByteBufferImageRawData();
		globeStandRawMeshData = new OBJMeshRawData();
		candleRawTextureData = new ByteBufferImageRawData();
		candleSpecularRawTextureData = new ByteBufferImageRawData();
		candleRawMeshData = new OBJMeshRawData();
		worldRawTextureData = new ByteBufferImageRawData();
		globeSpecularRawTextureData = new ByteBufferImageRawData();
		globeRawMeshData = new GlobeRawData();
		greenRawTextureData = new ByteBufferImageRawData();
		redRawTextureData = new ByteBufferImageRawData();
		blueRawTextureData = new ByteBufferImageRawData();
		arrowSpecularRawTextureData = new ByteBufferImageRawData();
		arrowRawMeshData = new OBJMeshRawData();

		loadTask.addItem(dukerawTextureData, "dukemascot.png");
		loadTask.addItem(tableRawTextureData, "table.png");
		loadTask.addItem(tableSpecularRawTextureData, "tableSpecularMap.png");
		loadTask.addItem(tableRawMeshData, "table.obj");
		loadTask.addItem(roomRawTextureData, "room.png");
		loadTask.addItem(roomSpecularRawTextureData, "roomSpecularMap.png");
		loadTask.addItem(roomRawMeshData, "room.obj");
		loadTask.addItem(globeStandRawTextureData, "globeStand.png");
		loadTask.addItem(globeStandSpecularRawTextureData, "globeStandSpecularMap.png");
		loadTask.addItem(globeStandRawMeshData, "globeStand.obj");
		loadTask.addItem(candleRawTextureData, "candle.png");
		loadTask.addItem(candleSpecularRawTextureData, "candleSpecularMap.png");
		loadTask.addItem(candleRawMeshData, "candle.obj");
		loadTask.addItem(worldRawTextureData, "worldTexture.png");
		loadTask.addItem(globeSpecularRawTextureData, "globeSpecularMap.png");
		loadTask.addItem(globeRawMeshData, "");
		loadTask.addItem(greenRawTextureData, "green.png");
		loadTask.addItem(redRawTextureData, "red.png");
		loadTask.addItem(blueRawTextureData, "blue.png");
		loadTask.addItem(arrowSpecularRawTextureData, "arrowSpecularMap.png");
		loadTask.addItem(arrowRawMeshData, "arrow.obj");

		getLoaderThread().queueTask(loadTask);
	}

	private synchronized void connectFirstDataBatch() {
		UnconnectedData unconnectedData = getConnecter().generateNewTask();
		dukeTexture = new Texture();
		unconnectedData.addData(dukeTexture, dukerawTextureData);
		tableTexture = new Texture();
		unconnectedData.addData(tableTexture, tableRawTextureData);
		tableSpecularTexture = new Texture();
		unconnectedData.addData(tableSpecularTexture, tableSpecularRawTextureData);
		tableMesh = new VAO();
		unconnectedData.addData(tableMesh, tableRawMeshData);
		roomTexture = new Texture();
		unconnectedData.addData(roomTexture, roomRawTextureData);
		roomSpecularTexture = new Texture();
		unconnectedData.addData(roomSpecularTexture, roomSpecularRawTextureData);
		roomMesh = new VAO();
		unconnectedData.addData(roomMesh, roomRawMeshData);
		globeStandTexture = new Texture();
		unconnectedData.addData(globeStandTexture, globeStandRawTextureData);
		globeStandSpecularTexture = new Texture();
		unconnectedData.addData(globeStandSpecularTexture, globeStandSpecularRawTextureData);
		globeStandMesh = new VAO();
		unconnectedData.addData(globeStandMesh, globeStandRawMeshData);
		candleTexture = new Texture();
		unconnectedData.addData(candleTexture, candleRawTextureData);
		candleSpecularTexture = new Texture();
		unconnectedData.addData(candleSpecularTexture, candleSpecularRawTextureData);
		candleMesh = new VAO();
		unconnectedData.addData(candleMesh, candleRawMeshData);

		unconnectedData.addNotifier(this);
		getConnecter().queueTask(unconnectedData);
		while (!unconnectedData.isConnected()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void connectSceondDataBatch() {
		UnconnectedData unconnectedData = getConnecter().generateNewTask();

		greenTexture = new Texture();
		unconnectedData.addData(greenTexture, greenRawTextureData);
		redTexture = new Texture();
		unconnectedData.addData(redTexture, redRawTextureData);
		blueTexture = new Texture();
		unconnectedData.addData(blueTexture, blueRawTextureData);
		arrowSpecularTexture = new Texture();
		unconnectedData.addData(arrowSpecularTexture, arrowSpecularRawTextureData);
		arrowMesh = new VAO();
		unconnectedData.addData(arrowMesh, arrowRawMeshData);

		worldTexture = new Texture();
		unconnectedData.addData(worldTexture, worldRawTextureData);
		globeSpecularTexture = new Texture();
		unconnectedData.addData(globeSpecularTexture, globeSpecularRawTextureData);
		globeMesh = new VAO();
		unconnectedData.addData(globeMesh, globeRawMeshData);

		unconnectedData.addNotifier(this);
		getConnecter().queueTask(unconnectedData);

		while (!unconnectedData.isConnected()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void generateResourcePack() {
		resourcePack = new ResourcePack();
		resourcePack.addTexture(dukeTexture, "dukeTexture");
		resourcePack.addTexture(tableTexture, "tableTexture");
		resourcePack.addTexture(tableSpecularTexture, "tableSpecularMap");
		resourcePack.addMesh(tableMesh, "tableMesh");
		resourcePack.addTexture(roomTexture, "roomTexture");
		resourcePack.addTexture(roomSpecularTexture, "roomSpecularMap");
		resourcePack.addMesh(roomMesh, "roomMesh");
		resourcePack.addTexture(globeStandTexture, "globeStandTexture");
		resourcePack.addTexture(globeStandSpecularTexture, "globeStandSpecularMap");
		resourcePack.addMesh(globeStandMesh, "globeStandMesh");
		resourcePack.addTexture(candleTexture, "candleTexture");
		resourcePack.addTexture(candleSpecularTexture, "candleSpecularMap");
		resourcePack.addMesh(candleMesh, "candleMesh");
		resourcePack.addTexture(worldTexture, "worldTexture");
		resourcePack.addTexture(globeSpecularTexture, "globeSpecularMap");
		resourcePack.addMesh(globeMesh, "globeMesh");
		resourcePack.addTexture(greenTexture, "greenTexture");
		resourcePack.addTexture(redTexture, "redTexture");
		resourcePack.addTexture(blueTexture, "blueTexture");
		resourcePack.addTexture(arrowSpecularTexture, "arrowSpecularMap");
		resourcePack.addMesh(arrowMesh, "arrowMesh");
	}

	@Override
	public void updateWindowDimensions(int windowWidth, int windowHeight) {

	}

	@Override
	public void update() {
		if (!loadedItems) {
			addLoadRawDataTask();
			loadedItems = true;
		} else if (!finishedLoading) {
			double percentage = loadTask.getProgressPercentage();
			if (percentage < 100) {
				System.out.println(Math.round(percentage) + "%");
			} else {
				finishedLoading = true;
			}
		} else if (!finishedConnectingFirstBatch) {
			connectFirstDataBatch();
			finishedConnectingFirstBatch = true;
		} else if (!finishedConnectingSecondBatch) {
			connectSceondDataBatch();
			finishedConnectingSecondBatch = true;
		} else {
			generateResourcePack();
			double endTime = GLFW.glfwGetTime();
			double timeTaken = endTime - startTime;
			System.out.println("Finished loading in " + timeTaken + " seconds.");
			transition(new PlayGameState(resourcePack));
		}
	}

}
