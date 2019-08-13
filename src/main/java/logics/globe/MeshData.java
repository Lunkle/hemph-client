package logics.globe;

import java.util.ArrayList;
import java.util.List;

import graphics.primitive.Point;
import graphics.primitive.Triangle;
import graphics.vao.VAO;
import math.Vector3f;

public class MeshData {

	private List<Triangle> triangles;

	public MeshData() {
		triangles = new ArrayList<>();
	}

	public List<Triangle> getTriangles() {
		return triangles;
	}

	/**
	 * Adds the triangle into the triangles array;
	 * 
	 * @param triangle
	 */
	public void addTriangle(Triangle triangle) {
		triangles.add(triangle);
	}

	public VAO toMesh() {
		List<Float> positions = new ArrayList<>();
		List<Float> normals = new ArrayList<>();
		List<Float> textureCoordinates = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		int index = 0;
		for (Triangle triangle : triangles) {
			Vector3f normal = triangle.getTriangleNormal();
			for (int i = 0; i < 3; i++) {
				Point point = triangle.getNextPoint();
				positions.add(point.getX());
				positions.add(point.getY());
				positions.add(point.getZ());
				normals.add(normal.x);
				normals.add(normal.y);
				normals.add(normal.z);
				indices.add(index);
				index++;
			}
			textureCoordinates.add(0.5f);
			textureCoordinates.add(1.0f);
			textureCoordinates.add(0.0f);
			textureCoordinates.add(0.0f);
			textureCoordinates.add(1.0f);
			textureCoordinates.add(0.0f);
		}
		return new VAO();
//		return VAOBuilder.newInstance().addPositions(positions).addTextureCoordinates(textureCoordinates).addNormals(normals).addIndices(indices).create();
	}

}
