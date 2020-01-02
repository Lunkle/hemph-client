package logics.collision;

import math.Vector3f;

public abstract class BoundingRegion {

	public abstract boolean containsPoint(Vector3f point);

	/**
	 * This must be implemented in each class with just a single function call of
	 * "return region.intersects(this).invert();". Make sure that for any subclass,
	 * there must be a corresponding abstract intersects method in this class.
	 * 
	 * @param region the region to check intersection with
	 * @return whether or not the two regions intersect.
	 */
	public abstract IntersectionType intersects(BoundingRegion region);

	public abstract IntersectionType intersects(BoundingBox box);

	public abstract IntersectionType intersects(BoundingSphere sphere);

}
