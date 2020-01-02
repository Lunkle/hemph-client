package logics.collision;

import logics.octree.GameEntity;
import math.Vector3f;

public class BoundingSphere extends BoundingRegion {

	private boolean bound = false;
	private Vector3f position;
	private GameEntity entity;
	private float radius;

	public BoundingSphere(GameEntity entity, float radius) {
		bound = true;
		this.entity = entity;
		this.radius = Math.abs(radius);
	}

	public BoundingSphere(Vector3f position, float radius) {
		bound = false;
		this.position = new Vector3f(position);
		this.radius = Math.abs(radius);
	}

	public Vector3f getPosition() {
		if (bound) {
			return entity.getAbsoluteWorldTransformation().getPosition();
		} else {
			return position;
		}
	}

	public float getRadius() {
		return radius;
	}

	@Override
	public boolean containsPoint(Vector3f point) {
		float radiusSquared = radius * radius;
		float distToPoint = Vector3f.sub(point, getPosition()).lengthSquared();
		return distToPoint <= radiusSquared;
	}

	@Override
	public IntersectionType intersects(BoundingRegion region) {
		return region.intersects(this).invert();
	}

	@Override
	public IntersectionType intersects(BoundingBox box) {
		return box.intersects(this).invert();
	}

	@Override
	public IntersectionType intersects(BoundingSphere sphere) {
		float dSquared = Vector3f.sub(getPosition(), sphere.getPosition()).lengthSquared();
		if (Math.pow(Math.abs(radius - sphere.radius), 2) > dSquared) {
			return (radius > sphere.radius) ? IntersectionType.CONTAINS : IntersectionType.CONTAINED;
		} else if (Math.pow(radius + sphere.radius, 2) < dSquared) {
			return IntersectionType.NO_INTERSECTION;
		} else {
			return IntersectionType.INTERSECT;
		}
	}

}
