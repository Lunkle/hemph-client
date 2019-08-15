package graphics.vao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import graphics.loader.InterpretedData;
import graphics.loader.RawData;
import math.Vector2f;
import math.Vector3f;

public class OBJMeshRawData extends MeshRawData implements RawData {

	private float[] positionsArray;
	private float[] normalsArray;
	private float[] textureArray;
	private int[] indicesArray;

	@Override
	public void load(String filePath) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("src/main/resources/" + filePath));
		} catch (FileNotFoundException e) {
			System.err.println("Failed to load obj file: " + filePath);
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<>();
		List<Vector2f> textures = new ArrayList<>();
		List<Vector3f> normals = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		try {
			while (true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				} else if (line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
				} else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				} else if (line.startsWith("f ")) {
					textureArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			}
			while (line != null) {
				if (!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");

				processVertex(vertex1, indices, textures, normals);
				processVertex(vertex2, indices, textures, normals);
				processVertex(vertex3, indices, textures, normals);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		positionsArray = new float[vertices.size() * 3];
		indicesArray = new int[indices.size()];
		int vertexPointer = 0;
		for (Vector3f vertex : vertices) {
			positionsArray[vertexPointer++] = vertex.x;
			positionsArray[vertexPointer++] = vertex.y;
			positionsArray[vertexPointer++] = vertex.z;
		}
		for (int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
	}

	private void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer * 2] = currentTex.x;
		textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer * 3] = currentNorm.x;
		normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
	}

	@Override
	public float[] getPositionsArray() {
		return positionsArray;
	}

	@Override
	public float[] getNormalsArray() {
		return normalsArray;
	}

	@Override
	public float[] getTextureArray() {
		return textureArray;
	}

	@Override
	public int[] getIndicesArray() {
		return indicesArray;
	}

	@Override
	public void accept(InterpretedData data) {
		data.interpret(this);
	}

}
