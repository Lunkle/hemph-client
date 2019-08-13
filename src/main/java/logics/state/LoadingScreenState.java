package logics.state;

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
import input.observer.KeyObserver;

public class LoadingScreenState extends GameState {

	private boolean finishedLoading = false;

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

	public LoadingScreenState(ResourceLoaderThread loaderThread, GraphicsDataConnecter connecter) {
		super();
		setLoadingThread(loaderThread);
		setConnecter(connecter);

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

		loadTask.addItem(dukerawTextureData, "dukemascot.png");
		loadTask.addItem(tableRawTextureData, "table.png");
		loadTask.addItem(tableSpecularRawTextureData, "tableSpecularMap.png");
		loadTask.addItem(tableRawMeshData, "table.obj");
		loadTask.addItem(roomRawTextureData, "room.png");
		loadTask.addItem(roomSpecularRawTextureData, "roomSpecularMap.png");
		loadTask.addItem(roomRawMeshData, "room.obj");

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

	private void generateResourcePack() {
		resourcePack = new ResourcePack();
		resourcePack.addTexture(dukeTexture, "dukeTexture");
		resourcePack.addTexture(tableTexture, "tableTexture");
		resourcePack.addTexture(tableSpecularTexture, "tableSpecularMap");
		resourcePack.addMesh(tableMesh, "tableMesh");
		resourcePack.addTexture(roomTexture, "roomTexture");
		resourcePack.addTexture(roomSpecularTexture, "roomSpecularMap");
		resourcePack.addMesh(roomMesh, "roomMesh");
	}

	@Override
	public GameState update() {
		if (!finishedLoading) {
			double percentage = loadTask.getProgressPercentage();
			if (percentage < 100) {

			} else {
				finishedLoading = true;
			}
		} else {
			connectFirstDataBatch();
			generateResourcePack();
			return new TableTopState(resourcePack);
		}
		return this;
	}

}
