package graphics.primitive;

/**
 * Half edge is an edge with a direction.
 * 
 * @author Donny
 *
 */
public class HalfEdge {

	private Point fromPoint;
	private Point toPoint;

	public HalfEdge(Point p1, Point p2) {
		fromPoint = p1;
		toPoint = p2;
		fromPoint.addHalfEdge(this);
	}

	public Point getPoint1() {
		return fromPoint;
	}

	public Point getPoint2() {
		return toPoint;
	}

	public HalfEdge invert() {
		return new HalfEdge(toPoint, fromPoint);
	}

}
