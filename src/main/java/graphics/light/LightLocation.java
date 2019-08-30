package graphics.light;

public class LightLocation {

	private int location_ambient;
	private int location_diffuse;
	private int location_specular;
	private int location_strength;

	public LightLocation(int ambientLocation, int diffuseLocation, int specularLocation, int strengthLocation) {
		location_ambient = ambientLocation;
		location_diffuse = diffuseLocation;
		location_specular = specularLocation;
		setLocation_strength(strengthLocation);
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

	public int getLocation_strength() {
		return location_strength;
	}

	public void setLocation_strength(int location_strength) {
		this.location_strength = location_strength;
	}

}
