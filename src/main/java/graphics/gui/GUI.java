package graphics.gui;

import graphics.texture.Texture;
import graphics.transformation.ProjectionTransformation;
import graphics.transformation.ProjectionWrapper;
import graphics.transformation.WorldTransformation;
import input.command.Command;
import input.information.Action;
import input.information.Key;
import input.mouse.Mouse;
import input.mouse.MousePicker;
import input.observer.MouseButtonObserver;
import input.observer.MouseCheck;
import input.observer.MouseMovementObserver;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;

public class GUI {

	private String name;

	private boolean selected = true;
	private boolean clickSet = false;
	private MouseButtonObserver onPress;
	private MouseButtonObserver onRelease;
	private MouseMovementObserver onHover;
	private Texture texture;
	private WorldTransformation worldTransformation;

	protected GUI() {}

	/**
	 * Takes in dimesnions in normalized device coordinates.
	 * 
	 * @param posX   The x-position of the gui
	 * @param posY   The y-position of the gui
	 * @param width  The width of the gui
	 * @param height The height of the gui
	 */
	protected void setDimensions(float posX, float posY, float width, float height) {
		worldTransformation = new WorldTransformation(2.0f * posX - 1.0f, 1.0f - 2.0f * posY, 0, new Vector3f(0, 1, 0), 0, 2.0f * width, 2.0f * height, 1);
	}

	/**
	 * Returns the position of the top-left corner of the gui in normalized device
	 * coordinates.
	 * 
	 * @return The position of the gui stored in a Vector2f
	 */
	public Vector2f getPosition() {
		Vector3f position = worldTransformation.getPosition();
		return new Vector2f(position.x, position.y);
	}

	/**
	 * Returns the size of the gui in normalized device coordinates.
	 * 
	 * @return The size of the gui stored in a Vector2f
	 */
	public Vector2f getSize() {
		Vector3f scale = worldTransformation.getScale();
		return new Vector2f(scale.x, scale.y);
	}

	/**
	 * Returns the position of the center of the gui in normalized device
	 * coordinates.
	 * 
	 * @return The position of the center of the gui stored in a Vector2f
	 */
	public Vector2f getCenterPosition() {
		Vector2f size = getSize();
		size.scale(0.5f);
		Vector2f position = getPosition();
		Vector2f center = new Vector2f(position.x + size.x, position.y - size.y);
		return center;
	}

	public Matrix4f getTransformationMatrix() {
		return worldTransformation.getMatrix();
	}

	protected void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getTextureID() {
		if (texture != null) {
			return texture.getID();
		}
		return -1;
	}

	public void activateTextures() {
		texture.activateTexture();
	}

	/**
	 * A check to see if a given mouse's coordinates overlap this gui or not.
	 * 
	 * @param mouse
	 * @return whether or not the mouse is on this gui
	 */
	private boolean isSelectedBy(Mouse mouse, ProjectionTransformation projectionTransformation) {
		Vector2f mouseCoords = MousePicker.getNormalizedDeviceCoordinates(mouse, projectionTransformation);
		Vector2f guiCoords = getPosition();
		Vector2f guiSize = getSize();
		if (mouseCoords.x >= guiCoords.x && mouseCoords.y <= guiCoords.y && mouseCoords.x <= guiCoords.x + guiSize.x && mouseCoords.y >= guiCoords.y - guiSize.y) {
			return true;
		}
		return false;
	}

	/**
	 * Note that you cannot change these once you have added them in.
	 * 
	 * @param mouse
	 * @param command
	 */
	public void setOnPressCommand(Mouse mouse, Command command, ProjectionWrapper projectionWrapper) {
		if (!clickSet) {
			clickSet = true;
			setOnReleaseCommand(mouse, new Command(() -> {}), projectionWrapper);
		}
		onPress = new MouseButtonObserver();
		onPress.setName(toString() + " Press Observer");
		MouseCheck selectionCheck = () -> {
			if (isSelectedBy(mouse, projectionWrapper.getTransformation())) {
				selected = true;
				return true;
			}
			return false;
		};
		onPress.setConsumes(true);
		onPress.addCheck(Key.MOUSE_LEFT, Action.PRESS, selectionCheck, command);
	}

	public void setOnReleaseCommand(Mouse mouse, Command command, ProjectionWrapper projectionWrapper) {
		if (!clickSet) {
			clickSet = true;
			setOnPressCommand(mouse, new Command(() -> {}), projectionWrapper);
		}
		onRelease = new MouseButtonObserver();
		onRelease.setName(toString() + " Release Observer");
		MouseCheck selectionCheck = () -> {
			if (selected == false) {
				return false;
			}
			selected = false;
			return isSelectedBy(mouse, projectionWrapper.getTransformation());
		};
		onRelease.setConsumes(true);
		onRelease.addCheck(Key.MOUSE_LEFT, Action.RELEASE, selectionCheck, command);
	}

	public MouseButtonObserver getOnPress() {
		return onPress;
	}

	public MouseButtonObserver getOnRelease() {
		return onRelease;
	}

	public MouseMovementObserver getOnHover() {
		return onHover;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		if (name == null) {
			return "Unnamed GUI";
		}
		return name;
	}

}
