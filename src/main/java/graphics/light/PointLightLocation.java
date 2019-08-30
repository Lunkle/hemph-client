package graphics.light;

public class PointLightLocation extends LightLocation {

	private int location_position;
	private int location_constants;

	public PointLightLocation(int positionLocation, int constantsLocation, int ambientLocation, int diffuseLocation, int specularLocation, int strengthLocation) {
		super(ambientLocation, diffuseLocation, specularLocation, strengthLocation);
		location_position = positionLocation;
		location_constants = constantsLocation;
	}

	public int getLocation_position() {
		return location_position;
	}

	public int getLocation_constants() {
		return location_constants;
	}

}
