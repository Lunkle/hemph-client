package input.mouse;

import math.Vector2f;

public class Mouse {

	private Vector2f position;
	private Vector2f previousPosition;

	private int zoom = 0;

	public Mouse() {
		position = new Vector2f(0, 0);
		previousPosition = new Vector2f(0, 0);
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getMovementVector() {
		return Vector2f.sub(position, previousPosition);
	}

	public void updatePosition(float newPositionX, float newPositionY) {
		previousPosition.set(position);
		position.set(newPositionX, newPositionY);
	}

	public int getZoom() {
		return zoom;
	}

	public void addZoom(float amount) {
		zoom += amount;
	}

}
