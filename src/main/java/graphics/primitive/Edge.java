package graphics.primitive;

/**
 * An edge consists of two opposite half edges.
 * 
 * @author Donny
 *
 */
public class Edge {

	private HalfEdge halfEdge1;
	private HalfEdge halfEdge2;

	public Edge(Point p1, Point p2) {
		halfEdge1 = new HalfEdge(p1, p2);
		halfEdge2 = new HalfEdge(p1, p2);
	}

	public HalfEdge getHalfEdge1() {
		return halfEdge1;
	}

	public HalfEdge getHalfEdge2() {
		return halfEdge2;
	}

}
