package graphics.primitive;

import java.util.ArrayList;
import java.util.List;

import math.Vector3f;

public class Point implements Cloneable {

	protected Vector3f position;
	protected List<Point> adjacentPoints;

	public Point(float x, float y, float z) {
		position = new Vector3f(x, y, z);
		adjacentPoints = new ArrayList<>();
	}

	public Point(Vector3f vector) {
		this(vector.x, vector.y, vector.z);
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getZ() {
		return position.z;
	}

	public Vector3f vector() {
		return position;
	}

	public void addAdjacentPoint(Point point) {
		adjacentPoints.add(point);
	}

	public List<Point> getAdjacentPoints() {
		return adjacentPoints;
	}

	@Override
	public Point clone() {
		Point newPoint = new Point(position);
		return newPoint;
	}

}
