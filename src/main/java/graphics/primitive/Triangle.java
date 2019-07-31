package graphics.primitive;

public class Triangle {

	private Point[] points;
	private int pointIndex = -1;
	private Edge[] edges;
	private int edgeIndex = -1;

	public Triangle(Point p1, Point p2, Point p3) {
		points = new Point[] { p1, p2, p3 };
		edges = new Edge[] { new Edge(p1, p2), new Edge(p1, p3), new Edge(p2, p3) };
	}

	public Edge getEdge1() {
		return edges[0];
	}

	public Edge getEdge2() {
		return edges[1];
	}

	public Edge getEdge3() {
		return edges[2];
	}

	public Edge getNextEdge() {
		edgeIndex++;
		if (edgeIndex == 3) {
			edgeIndex = 0;
		}
		return edges[edgeIndex];
	}

	public Edge[] getEdges() {
		return edges;
	}

	public Point getPoint1() {
		return points[0];
	}

	public Point getPoint2() {
		return points[1];
	}

	public Point getPoint3() {
		return points[2];
	}

	public Point getNextPoint() {
		pointIndex++;
		if (pointIndex == 3) {
			pointIndex = 0;
		}
		return points[pointIndex];
	}

	public Point[] getPoints() {
		return points;
	}

}
