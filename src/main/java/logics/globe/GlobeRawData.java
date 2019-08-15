package logics.globe;

import java.util.ArrayList;
import java.util.List;

import graphics.loader.InterpretedData;
import graphics.loader.RawData;
import graphics.primitive.FullAdjacencyVertex;
import graphics.primitive.HalfEdge;
import graphics.primitive.Triangle;
import graphics.primitive.Vertex;
import graphics.vao.MeshRawData;
import math.Vector2f;
import math.Vector3f;

public class GlobeRawData extends MeshRawData implements RawData {

	private List<Triangle> triangles;
	private List<FullAdjacencyVertex> vertices;

	public GlobeRawData() {
		super();
		triangles = new ArrayList<>();
		vertices = new ArrayList<>();
	}

	@Override
	public void load(String filePath) {
		generateIcosaherdron();
		subdivide();
		translateToRawData();
	}

	public void generateIcosaherdron() {
		Vector2f vector = new Vector2f(1, (float) (2 * Math.sin(Math.toRadians(54))));
		vector.normalise();
		float a = vector.x;
		float b = vector.y;
		// XY plane rectangle
		FullAdjacencyVertex p1 = new FullAdjacencyVertex(-a, b, 0);
		FullAdjacencyVertex p2 = new FullAdjacencyVertex(a, b, 0);
		FullAdjacencyVertex p3 = new FullAdjacencyVertex(-a, -b, 0);
		FullAdjacencyVertex p4 = new FullAdjacencyVertex(a, -b, 0);
		// YZ plane rectangle
		FullAdjacencyVertex p5 = new FullAdjacencyVertex(0, -a, b);
		FullAdjacencyVertex p6 = new FullAdjacencyVertex(0, a, b);
		FullAdjacencyVertex p7 = new FullAdjacencyVertex(0, -a, -b);
		FullAdjacencyVertex p8 = new FullAdjacencyVertex(0, a, -b);
		// XZ plane rectangle
		FullAdjacencyVertex p9 = new FullAdjacencyVertex(b, 0, -a);
		FullAdjacencyVertex p10 = new FullAdjacencyVertex(b, 0, a);
		FullAdjacencyVertex p11 = new FullAdjacencyVertex(-b, 0, -a);
		FullAdjacencyVertex p12 = new FullAdjacencyVertex(-b, 0, a);

		// Add them into the ArrayList;
		p1.setIndex(0);
		vertices.add(p1);
		p2.setIndex(1);
		vertices.add(p2);
		p3.setIndex(2);
		vertices.add(p3);
		p4.setIndex(3);
		vertices.add(p4);
		p5.setIndex(4);
		vertices.add(p5);
		p6.setIndex(5);
		vertices.add(p6);
		p7.setIndex(6);
		vertices.add(p7);
		p8.setIndex(7);
		vertices.add(p8);
		p9.setIndex(8);
		vertices.add(p9);
		p10.setIndex(9);
		vertices.add(p10);
		p11.setIndex(10);
		vertices.add(p11);
		p12.setIndex(11);
		vertices.add(p12);

		// Top five triangles around point 1
		addTriangle(new Triangle(p1, p11, p12));
		addTriangle(new Triangle(p1, p12, p6));
		addTriangle(new Triangle(p1, p6, p2));
		addTriangle(new Triangle(p1, p2, p8));
		addTriangle(new Triangle(p1, p8, p11));
		// Middle ten triangles forming a strip
		addTriangle(new Triangle(p12, p11, p3));
		addTriangle(new Triangle(p12, p3, p5));
		addTriangle(new Triangle(p6, p12, p5));
		addTriangle(new Triangle(p6, p5, p10));
		addTriangle(new Triangle(p2, p6, p10));
		addTriangle(new Triangle(p2, p10, p9));
		addTriangle(new Triangle(p8, p2, p9));
		addTriangle(new Triangle(p8, p9, p7));
		addTriangle(new Triangle(p11, p8, p7));
		addTriangle(new Triangle(p11, p7, p3));
		// Bottom five triangles around point 4
		addTriangle(new Triangle(p4, p9, p10));
		addTriangle(new Triangle(p4, p10, p5));
		addTriangle(new Triangle(p4, p5, p3));
		addTriangle(new Triangle(p4, p3, p7));
		addTriangle(new Triangle(p4, p7, p9));
	}

	private void subdivide() {

	}

	private void translateToRawData() {
		for (Vertex vertex : vertices) {
			Vector3f pointPosition = vertex.getPosition();
			positions.add(pointPosition.x);
			positions.add(pointPosition.y);
			positions.add(pointPosition.z);
			Vector3f pointNormal = vertex.calculateNormal();
			normals.add(pointNormal.x);
			normals.add(pointNormal.y);
			normals.add(pointNormal.z);
			textureCoordinates.add(0.5f);
			textureCoordinates.add(1.0f);
			textureCoordinates.add(0.0f);
			textureCoordinates.add(0.0f);
			textureCoordinates.add(1.0f);
			textureCoordinates.add(0.0f);
		}
		for (Triangle triangle : triangles) {
			HalfEdge edge = triangle.getEdge();
			for (int i = 0; i < 3; i++) {
				indices.add(edge.getVertex().getIndex());
				edge = edge.getNext();
			}
		}
	}

	public void addTriangle(Triangle triangle) {
		triangles.add(triangle);
	}

	@Override
	public void accept(InterpretedData data) {
		data.interpret(this);
	}

	@Override
	public long getSize(String filePath) {
		return triangles.size() * 3 * 3 * 4;
	}

}
