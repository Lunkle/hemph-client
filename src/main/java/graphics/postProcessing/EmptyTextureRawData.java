package graphics.postProcessing;

import graphics.loader.InterpretedData;
import graphics.loader.RawData;

public abstract class EmptyTextureRawData implements RawData {

	private int textureWidth;
	private int textureHeight;

	@Override
	public void load(String filePath) {}

	public EmptyTextureRawData(int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}

	@Override
	public void accept(InterpretedData data) {
		data.interpret(this);
	}

	public int getTextureWidth() {
		return textureWidth;
	}

	public int getTextureHeight() {
		return textureHeight;
	}

	public abstract int getInternalFormat();

	public abstract int getFormat();

	public abstract int getType();

}
