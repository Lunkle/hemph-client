package graphics.primitive;

import java.util.ArrayList;
import java.util.List;

import math.Vector3f;

public class Vertex {

	private Vector3f position;
	private HalfEdge edge;
	private int index = -1;

	public Vertex(float x, float y, float z) {
		position = new Vector3f(x, y, z);
		emanatingEdges = new ArrayList<>();
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	public HalfEdge getOutgoingHalfEdge() {
		return edge;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	private List<HalfEdge> emanatingEdges;

	public void setEmanatingEdges(List<HalfEdge> emanatingEdges) {
		this.emanatingEdges = emanatingEdges;
	}

	public List<HalfEdge> getEmanatingEdges() {
		return emanatingEdges;
	}

	public Vertex normalizeLength() {
		Vector3f normalizedPosition = getPosition();
		normalizedPosition.normalise();
		setPosition(normalizedPosition);
		return this;
	}

	public void setOutgoingHalfEdge(HalfEdge halfEdge) {
		emanatingEdges.add(halfEdge);
	}

	public Vector3f calculateNormal() {
		Vector3f totalNormal = new Vector3f(0, 0, 0);
		for (HalfEdge edge : emanatingEdges) {
			totalNormal = Vector3f.add(totalNormal, Primitive.getTriangleNormal(edge));
		}
		totalNormal.normalise();
		return totalNormal;
	}

}