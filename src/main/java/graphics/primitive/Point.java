package graphics.primitive;

import java.util.ArrayList;
import java.util.List;

public class Point {

	private float x;
	private float y;
	private float z;

	private List<HalfEdge> halfEdges;

	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		halfEdges = new ArrayList<>();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public void addHalfEdge(HalfEdge halfEdge) {
		halfEdges.add(halfEdge);
	}

	public List<HalfEdge> getHalfEdges() {
		return halfEdges;
	}

}
