package graphics.model;

public class Edge {

	private Point[] points;
	private int pointIndex = 1;

	public Edge(Point p1, Point p2) {
		points = new Point[] { p1, p2 };
	}

	public Point getPoint1() {
		return points[0];
	}

	public Point getPoint2() {
		return points[1];
	}

	public Point getNextPoint() {
		pointIndex = 1 - pointIndex;
		return points[pointIndex];
	}

	public Point[] getPoints() {
		return points;
	}

}
