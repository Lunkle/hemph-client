package logics.collision;

import math.Vector3f;

/**
 * Assuming the boxes are axis aligned!!
 * 
 * @author Donny
 *
 */
public class BoundingBox extends BoundingRegion {

	private Vector3f center;
	private float halfXLength;
	private float halfYLength;
	private float halfZLength;

	public BoundingBox(Vector3f center, float xLength, float yLength, float zLength) {
		this.center = new Vector3f(center);
		this.halfXLength = xLength / 2;
		this.halfYLength = yLength / 2;
		this.halfZLength = zLength / 2;
	}

	public BoundingBox(Vector3f center, float sideLength) {
		this(center, sideLength, sideLength, sideLength);
	}

	public Vector3f[] getCorners() {
		Vector3f[] corners = new Vector3f[8];
		corners[0] = Vector3f.add(center, new Vector3f(halfXLength, halfYLength, halfZLength));
		corners[1] = Vector3f.add(center, new Vector3f(halfXLength, halfYLength, -halfZLength));
		corners[2] = Vector3f.add(center, new Vector3f(halfXLength, -halfYLength, halfZLength));
		corners[3] = Vector3f.add(center, new Vector3f(halfXLength, -halfYLength, -halfZLength));
		corners[4] = Vector3f.add(center, new Vector3f(-halfXLength, halfYLength, halfZLength));
		corners[5] = Vector3f.add(center, new Vector3f(-halfXLength, halfYLength, -halfZLength));
		corners[6] = Vector3f.add(center, new Vector3f(-halfXLength, -halfYLength, halfZLength));
		corners[7] = Vector3f.add(center, new Vector3f(-halfXLength, -halfYLength, -halfZLength));
		return corners;
	}

	/**
	 * Determines if a box contains a specified point.
	 * 
	 * @param boxHalfXLength half of x length of the box
	 * @param boxHalfYLength half of y length of the box
	 * @param boxHalfZLength half of z length of the box
	 * @param boxCenter center of the box
	 * @param point specified point
	 * @return True if point is in the box, false otherwise.
	 * @author Donny
	 */
	private static boolean pointInsideBox(float boxHalfXLength, float boxHalfYLength, float boxHalfZLength, Vector3f boxCenter, Vector3f point) {
		boolean xIn = point.x >= boxCenter.x - boxHalfXLength && point.x <= boxCenter.x + boxHalfXLength;
		boolean yIn = point.y >= boxCenter.y - boxHalfYLength && point.y <= boxCenter.y + boxHalfYLength;
		boolean zIn = point.z >= boxCenter.z - boxHalfZLength && point.z <= boxCenter.z + boxHalfZLength;
		return xIn && yIn && zIn;
	}

	@Override
	public boolean containsPoint(Vector3f point) {
		return pointInsideBox(halfXLength, halfYLength, halfZLength, center, point);
	}

	@Override
	public IntersectionType intersects(BoundingRegion region) {
		return region.intersects(this).invert();
	}

	/**
	 * Determines how the box intersects with a given box.
	 * 
	 * @param box given box
	 * @return INTERSECTS, CONTAINS, CONTAINED, and NO_INTERSECTION. All are
	 * from the perspective of the original box.
	 * @author Jay
	 */
	@Override
	public IntersectionType intersects(BoundingBox box) {
		boolean xFail = Math.abs(box.center.x - this.center.x) > box.halfXLength + this.halfXLength;
		boolean yFail = Math.abs(box.center.y - this.center.y) > box.halfYLength + this.halfYLength;
		boolean zFail = Math.abs(box.center.z - this.center.z) > box.halfZLength + this.halfZLength;

		if (xFail || yFail || zFail) {
			return IntersectionType.NO_INTERSECTION;
		}

		Vector3f boxCorner1 = Vector3f.add(box.center, new Vector3f(box.halfXLength, box.halfYLength, box.halfZLength));
		Vector3f boxCorner2 = Vector3f.add(box.center, new Vector3f(-box.halfXLength, -box.halfYLength, -box.halfZLength));
		Vector3f thisCorner1 = Vector3f.add(center, new Vector3f(halfXLength, halfYLength, halfZLength));
		Vector3f thisCorner2 = Vector3f.add(center, new Vector3f(-halfXLength, -halfYLength, -halfZLength));

		if (pointInsideBox(box.halfXLength, box.halfYLength, box.halfZLength, box.center, thisCorner1) && pointInsideBox(box.halfXLength, box.halfYLength, box.halfZLength, box.center, thisCorner2)) {
			return IntersectionType.CONTAINED;
		} else if (pointInsideBox(halfXLength, halfYLength, halfZLength, center, boxCorner1) && pointInsideBox(halfXLength, halfYLength, halfZLength, center, boxCorner2)) {
			return IntersectionType.CONTAINS;
		}
		return IntersectionType.INTERSECT;
	}

	@Override
	public IntersectionType intersects(BoundingSphere sphere) {
		float r = sphere.getRadius();
		Vector3f c = sphere.getPosition();
		int numPointsInside = 0;
		for (Vector3f corner : getCorners()) {
			if (sphere.containsPoint(corner)) {
				numPointsInside++;
			}
		}
		if (numPointsInside == 8) {
			return IntersectionType.CONTAINED;
		} else if (numPointsInside == 0) {
			if (pointInsideBox(halfXLength - r, halfYLength - r, halfZLength - r, center, c)) {
				return IntersectionType.CONTAINS;
			}
			Vector3f closestPoint = getClosestPointTo(sphere.getPosition());
			float sphereCenterDistToPointSquared = Vector3f.sub(closestPoint, c).lengthSquared();
			if (r * r > sphereCenterDistToPointSquared) {
				return IntersectionType.INTERSECT;
			}
			System.out.println(closestPoint);
			System.out.println("outside");
			return IntersectionType.NO_INTERSECTION;
		} else {
			return IntersectionType.INTERSECT;
		}
	}

	private Vector3f getClosestPointTo(Vector3f point) {
		float x = Math.max(center.x - halfXLength, Math.min(center.x + halfXLength, point.x));
		float y = Math.max(center.y - halfYLength, Math.min(center.y + halfYLength, point.y));
		float z = Math.max(center.z - halfZLength, Math.min(center.z + halfZLength, point.z));
		return new Vector3f(x, y, z);
	}

}