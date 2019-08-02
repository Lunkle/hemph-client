package graphics.primitive;

/**
 * A unit point is a point such that the distance from the point to the origin
 * is exactly 1.
 * 
 * @author Donny
 *
 */
public class UnitPoint extends Point {

	public UnitPoint(float x, float y, float z) {
		super(x, y, z);
		position.normalise();
	}

}
