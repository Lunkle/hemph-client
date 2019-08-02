package graphics.primitive;

import math.Vector3f;

public class Triangle {

	private Point[] points;
	private int pointIndex = -1;
	private Vector3f triangleNormal;

	public Triangle(Point p1, Point p2, Point p3) {
		points = new Point[] { p1, p2, p3 };

		triangleNormal = calculateNormal();
	}

	private Vector3f calculateNormal() {
		Vector3f planeVector1 = Vector3f.sub(getPoint2().vector(), getPoint1().vector());
		Vector3f planeVector2 = Vector3f.sub(getPoint3().vector(), getPoint1().vector());
		Vector3f cross = new Vector3f(Vector3f.cross(planeVector1, planeVector2));
		cross.normalise();
		return cross;
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

	public Vector3f getTriangleNormal() {
		return triangleNormal;
	}

}
