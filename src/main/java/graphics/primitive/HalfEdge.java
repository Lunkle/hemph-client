package graphics.primitive;

public class HalfEdge {

	private Triangle triangle;
	private Vertex to;
	private HalfEdge pair;
	private HalfEdge next;

	// Utilitary data, not needed in final mesh
	private Vertex midpoint;

	public HalfEdge(Vertex to, Triangle triangle) {
		this.to = to;
		this.triangle = triangle;
	}

	public HalfEdge(Vertex to) {
		this.to = to;
	}

	public void setVertex(Vertex vertex) {
		to = vertex;
	}

	public Vertex getVertex() {
		return to;
	}

	public void setTriangle(Triangle triangle) {
		this.triangle = triangle;
	}

	public Triangle getTriangle() {
		return triangle;
	}

	public void setNext(HalfEdge edge) {
		next = edge;
	}

	public HalfEdge getNext() {
		return next;
	}

	public void setPair(HalfEdge pair) {
		this.pair = pair;
	}

	public HalfEdge getPair() {
		return pair;
	}

	public Vertex getMidpoint() {
		return midpoint;
	}

	public void setMidpoint(Vertex midpoint) {
		this.midpoint = midpoint;
	}

}
