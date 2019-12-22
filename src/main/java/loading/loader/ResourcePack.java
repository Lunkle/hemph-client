package loading.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.texture.Texture;
import graphics.vao.VAO;
import loading.texture.ByteBufferImageRawData;
import loading.vao.OBJMeshRawData;

/**
 * Just a clean way to "transport" interpreted data across states. Without this
 * class you would have massive constructors (which look uguh-lee!)
 * 
 * However, there is a downside: if the receiving side erroneously believes a
 * certain piece of data is in this resource pack and tries to fetch it and use
 * it, the error will only show up at run time.
 * 
 * @author Donny
 *
 */
public class ResourcePack {

	private List<String> nameList;
	private Map<String, String> filepathMap;
	private Map<String, RawData> rawDataMap;

	private Map<String, InterpretedData> interpretedMap;

	public ResourcePack() {
		nameList = new ArrayList<>();
		filepathMap = new HashMap<>();
		rawDataMap = new HashMap<>();

		interpretedMap = new HashMap<>();
	}

	public void load(String filePath) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("src/main/resources/loading/" + filePath));
		} catch (FileNotFoundException e) {
			System.err.println("Failed to load startup-load file: " + filePath);
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		String line;
		int lineNumber = 0;
		try {
			while (true) {
				line = reader.readLine();
				lineNumber++;
				if (line == null) {
					break;
				}
				String[] currentLine = line.split(" ");
				RawData data = null;
				if (!currentLine[0].startsWith("//") && !currentLine[0].equals("")) {
					if (currentLine[0].equals("bbrd")) {
						data = new ByteBufferImageRawData();
					} else if (currentLine[0].equals("objmrd")) {
						data = new OBJMeshRawData();
					} else {
						System.out.println("Resource loading file syntax error at '" + filePath + "', line " + lineNumber);
					}
					nameList.add(currentLine[2]);
					filepathMap.put(currentLine[2], currentLine[1]);
					rawDataMap.put(currentLine[2], data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getNameList() {
		return nameList;
	}

	public String getFilepath(String name) {
		return filepathMap.get(name);
	}

	public RawData getRawData(String name) {
		return rawDataMap.get(name);
	}

	/**
	 * Adds a fully interpreted data into this resource pack.
	 * 
	 * @param texture
	 * @param name
	 */
	public void addData(InterpretedData texture, String name) {
		interpretedMap.put(name, texture);
	}

	/**
	 * Attempts to fetch an interpreted data type by name from this resource
	 * pack.
	 * 
	 * @return an interpreted data if it exists or null if it doesn't
	 */
	public InterpretedData getData(String name) {
		return interpretedMap.get(name);
	}

	public Texture getTexture(String name) {
		InterpretedData data = interpretedMap.get(name);
		if (data instanceof Texture) {
			return (Texture) data;
		}
		return null;
	}

	public VAO getMesh(String name) {
		InterpretedData data = interpretedMap.get(name);
		if (data instanceof VAO) {
			return (VAO) data;
		}
		return null;
	}

	/**
	 * 
	 * @param data
	 */
	public void generateUnconnectedData() {
		for (String name : getNameList()) {
			RawData rawData = rawDataMap.get(name);
			interpretedMap.put(name, rawData.newInterpretedData());
		}
	}

}
