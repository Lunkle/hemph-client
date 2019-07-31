package graphics.entity;

public class PointLightLocation {

	private int location_position;
	private int location_ambient;
	private int location_diffuse;
	private int location_specular;

	public PointLightLocation(int positionLocation, int ambientLocation, int diffuseLocation, int specularLocation) {
		location_position = positionLocation;
		location_ambient = ambientLocation;
		location_diffuse = diffuseLocation;
		location_specular = specularLocation;
	}

	public int getLocation_position() {
		return location_position;
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
