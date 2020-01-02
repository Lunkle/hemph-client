package logics.collision;

public enum IntersectionType {
	INTERSECT,
	CONTAINED,
	CONTAINS,
	NO_INTERSECTION;

	public IntersectionType invert() {
		if (this == CONTAINED) {
			return CONTAINS;
		}
		if (this == CONTAINS) {
			return CONTAINED;
		}
		return this;
	}

	public boolean intersects() {
		return !(this == NO_INTERSECTION);
	}

}
