package graphics.primitive;

import math.Vector3f;

public class Vertex implements Cloneable {

	private Vector3f position;
	private HalfEdge edge;
	private int index = -1;

	public Vertex(float x, float y, float z) {
		position = new Vector3f(x, y, z);
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setOutgoingHalfEdge(HalfEdge halfEdge) {
		if (edge == null) {
			this.edge = halfEdge;
		}
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

	public Vector3f calculateNormal() {
		if (edge == null) {
			return new Vector3f(0, 1, 0);
		}
		HalfEdge outgoingEdge = edge;
		Vector3f totalNormal = new Vector3f(0, 0, 0);
		do {
			totalNormal = Vector3f.add(totalNormal, Primitive.getTriangleNormal(outgoingEdge));
			outgoingEdge = outgoingEdge.getPair().getNext();
		} while (outgoingEdge != edge);
		totalNormal.normalise();
		return totalNormal;
	}

}
