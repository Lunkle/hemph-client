package graphics.primitive;

public class HalfEdge {

	private FullAdjacencyVertex to;
	private HalfEdge pair;
	private HalfEdge next;

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

}
