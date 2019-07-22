package graphics.transformation;

import math.Matrix4f;

public abstract class Transformation {

	protected Matrix4f matrix;
	protected boolean isDirty = true;

	protected abstract void calculateMatrix();

	public final void setFlag() {
		isDirty = true;
	}

	public final Matrix4f getMatrix() {
		if (isDirty) {
			calculateMatrix();
			isDirty = false;
		}
		return matrix;
	}

}
