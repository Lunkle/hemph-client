package graphics.light;

public class DirectionalLightLocation extends LightLocation {

	private int location_direction;

	public DirectionalLightLocation(int directionLocation, int ambientLocation, int diffuseLocation, int specularLocation, int strengthLocation) {
		super(ambientLocation, diffuseLocation, specularLocation, strengthLocation);
		location_direction = directionLocation;
	}

	public int getLocation_direction() {
		return location_direction;
	}

}
