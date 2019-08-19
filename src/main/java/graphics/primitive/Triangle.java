package graphics.primitive;

import math.Vector3f;

public class Triangle {

	protected HalfEdge edge;
	protected Vector3f triangleNormal;

	public Triangle(Vertex p1, Vertex p2, Vertex p3) {
		edge = Primitive.findHalfEdge(p1, p2);
		edge.setTriangle(this);
		p1.setOutgoingHalfEdge(edge);
		Primitive.pairWithPotentialOtherEdge(edge, p1);

		HalfEdge next = Primitive.findHalfEdge(p2, p3);
		next.setTriangle(this);
		edge.setNext(next);
		p2.setOutgoingHalfEdge(next);
		Primitive.pairWithPotentialOtherEdge(next, p2);

		HalfEdge previous = Primitive.findHalfEdge(p3, p1);
		previous.setTriangle(this);
		next.setNext(previous);
		p3.setOutgoingHalfEdge(previous);
		Primitive.pairWithPotentialOtherEdge(previous, p3);

		previous.setNext(edge);

		triangleNormal = calculateNormal();
	}

	private Vector3f calculateNormal() {
		HalfEdge nextEdge = edge.getNext();
		Vector3f planeVector1 = Vector3f.sub(edge.getVertex().getPosition(), nextEdge.getVertex().getPosition());
		Vector3f planeVector2 = Vector3f.sub(nextEdge.getNext().getVertex().getPosition(), nextEdge.getVertex().getPosition());
		Vector3f cross = new Vector3f(Vector3f.cross(planeVector1, planeVector2));
		return cross;
	}

	public Vector3f getTriangleNormal() {
		return triangleNormal;
	}

	public HalfEdge getEdge() {
		return edge;
	}

	public Vector3f getCentroid() {
		Vector3f p1 = edge.getVertex().getPosition();
		Vector3f p2 = edge.getNext().getVertex().getPosition();
		Vector3f p3 = edge.getNext().getNext().getVertex().getPosition();
		Vector3f centroid = Vector3f.add(Vector3f.add(p1, p2), p3);
		centroid.scale(1.0f / 3.0f);
		return centroid;
	}

}
