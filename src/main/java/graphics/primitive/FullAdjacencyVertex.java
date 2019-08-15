package graphics.primitive;

import java.util.ArrayList;
import java.util.List;

import math.Vector3f;

public class FullAdjacencyVertex extends Vertex {

	private List<HalfEdge> emanatingEdges;

	public FullAdjacencyVertex(float x, float y, float z) {
		super(x, y, z);
		emanatingEdges = new ArrayList<>();
	}

	@Override
	public void setOutgoingHalfEdge(HalfEdge halfEdge) {
		emanatingEdges.add(halfEdge);
	}

	@Override
	public Vector3f calculateNormal() {
		Vector3f totalNormal = new Vector3f(0, 0, 0);
		for (HalfEdge edge : emanatingEdges) {
			totalNormal = Vector3f.add(totalNormal, Primitive.getTriangleNormal(edge));
		}
		totalNormal.normalise();
		return totalNormal;
	}

	public List<HalfEdge> getEmanatingEdges() {
		return emanatingEdges;
	}

}