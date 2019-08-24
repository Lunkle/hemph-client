package logics.state;

import org.lwjgl.glfw.GLFW;

import graphics.loader.GraphicsDataConnecter;
import graphics.loader.ResourceLoaderThread;
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
import input.mouse.Mouse;
import input.observer.KeyObserver;
import logics.globe.GlobeRawData;

public class LoadingScreenState extends GameState {

	private boolean finishedLoading = false;
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
	private ByteBufferImageRawData greenRawTextureData;
	private Texture greenTexture;
	private ByteBufferImageRawData globeSpecularRawTextureData;
	private Texture globeSpecularTexture;
	private GlobeRawData globeRawMeshData;
	private VAO globeMesh;

	public LoadingScreenState(Mouse mouse, ResourceLoaderThread loaderThread, GraphicsDataConnecter connecter) {
		super();

		setMouse(mouse);
		setLoadingThread(loaderThread);
		setConnecter(connecter);

		startTime = GLFW.glfwGetTime();

		setFlagToClearObservers();

		KeyObserver printHi = new KeyObserver();
		printHi.addCommand(Keys.KEY_E, new KeyCommand(new Command(() -> {}), new Command(() -> System.out.println("lmao"))));
		addKeyObserver(printHi);

		loadTask = loaderThread.generateNewTask();

		dukerawTextureData = new ByteBufferImageRawData();
		tableRawTextureData = new ByteBufferImageRawData();
		tableSpecularRawTextureData = new ByteBufferImageRawData();
		tableRawMeshData = new OBJMeshRawData();
		roomRawTextureData = new ByteBufferImageRawData();
		roomSpecularRawTextureData = new ByteBufferImageRawData();
		roomRawMeshData = new OBJMeshRawData();
		greenRawTextureData = new ByteBufferImageRawData();
		globeSpecularRawTextureData = new ByteBufferImageRawData();
		globeRawMeshData = new GlobeRawData();

		loadTask.addItem(dukerawTextureData, "dukemascot.png");
		loadTask.addItem(tableRawTextureData, "table.png");
		loadTask.addItem(tableSpecularRawTextureData, "tableSpecularMap.png");
		loadTask.addItem(tableRawMeshData, "table.obj");
		loadTask.addItem(roomRawTextureData, "room.png");
		loadTask.addItem(roomSpecularRawTextureData, "roomSpecularMap.png");
		loadTask.addItem(roomRawMeshData, "room.obj");
		loadTask.addItem(greenRawTextureData, "green.png");
		loadTask.addItem(globeSpecularRawTextureData, "globeSpecularMap.png");
		loadTask.addItem(globeRawMeshData, "");

		loaderThread.queueTask(loadTask);
	}

	private synchronized void connectFirstDataBatch() {
		UnconnectedData unconnectedData = connecter.generateNewTask();

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

		unconnectedData.addNotifier(this);
		connecter.queueTask(unconnectedData);

		while (!unconnectedData.isConnected()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void connectSceondDataBatch() {
		UnconnectedData unconnectedData = connecter.generateNewTask();

		greenTexture = new Texture();
		unconnectedData.addData(greenTexture, greenRawTextureData);
		globeSpecularTexture = new Texture();
		unconnectedData.addData(globeSpecularTexture, globeSpecularRawTextureData);
		globeMesh = new VAO();
		unconnectedData.addData(globeMesh, globeRawMeshData);

		unconnectedData.addNotifier(this);
		connecter.queueTask(unconnectedData);

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
		resourcePack.addTexture(greenTexture, "greenTexture");
		resourcePack.addTexture(globeSpecularTexture, "globeSpecularMap");
		resourcePack.addMesh(globeMesh, "globeMesh");
	}

	@Override
	public GameState update() {
		if (!finishedLoading) {
			double percentage = loadTask.getProgressPercentage();
			if (percentage < 100) {
				System.out.println(Math.round(percentage) + "%");
			} else {
				finishedLoading = true;
			}
		} else {
			connectFirstDataBatch();
			connectSceondDataBatch();
			generateResourcePack();
			double endTime = GLFW.glfwGetTime();
			double timeTaken = endTime - startTime;
			System.out.println("Finished loading in " + timeTaken + " seconds.");
			return new TableTopState(resourcePack);
		}
		return this;
	}

}
