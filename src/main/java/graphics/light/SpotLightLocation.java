package graphics.light;

public class SpotLightLocation extends LightLocation {

	private int location_direction;
	private int location_position;
	private int location_angle;

	public SpotLightLocation(int directionLocation, int positionLocation, int angleLocation, int ambientLocation, int diffuseLocation, int specularLocation, int strengthLocation) {
		super(ambientLocation, diffuseLocation, specularLocation, strengthLocation);
		location_direction = directionLocation;
		location_position = positionLocation;
		location_angle = angleLocation;
	}

	public int getLocation_direction() {
		return location_direction;
	}

	public int getLocation_position() {
		return location_position;
	}

	public int getLocation_angle() {
		return location_angle;
	}

}
