package graphics.entity;

public class SpotLightLocation {

	private int location_direction;
	private int location_position;
	private int location_angle;
	private int location_ambient;
	private int location_diffuse;
	private int location_specular;

	public SpotLightLocation(int directionLocation, int positionLocation, int angleLocation, int ambientLocation, int diffuseLocation, int specularLocation) {
		location_direction = directionLocation;
		location_position = positionLocation;
		location_angle = angleLocation;
		location_ambient = ambientLocation;
		location_diffuse = diffuseLocation;
		location_specular = specularLocation;
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

	public int getLocation_ambient() {
		return location_ambient;
	}

	public int getLocation_diffuse() {
		return location_diffuse;
	}

	public int getLocation_specular() {
		return location_specular;
	}

}
