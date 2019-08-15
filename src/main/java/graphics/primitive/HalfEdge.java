package graphics.primitive;

public class HalfEdge {

	private FullAdjacencyVertex to;
	private HalfEdge pair;
	private HalfEdge next;

	// Utilitary data, not needed in final mesh
	private FullAdjacencyVertex midpoint;

	public HalfEdge(FullAdjacencyVertex to) {
		this.to = to;
	}

	public FullAdjacencyVertex getVertex() {
		return to;
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

	public FullAdjacencyVertex getMidpoint() {
		return midpoint;
	}

	public void setMidpoint(FullAdjacencyVertex midpoint) {
		this.midpoint = midpoint;
	}

}
