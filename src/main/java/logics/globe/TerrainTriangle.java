package logics.globe;

import graphics.primitive.HalfEdge;
import graphics.primitive.Triangle;
import graphics.primitive.Vertex;
import math.Vector2f;
import math.Vector3f;

public class TerrainTriangle extends Triangle {

	private static final float PADDING = 0.05f;
	private static int textureGridSize = 4;

	// An index to keep track of what terrain type the triangle is.
	private int terrainIndex = -1;

	// The vector that defines the movement of the triangle. All triangles of a
	// certain plate will rotate about the same axis.
	private Vector3f movementVector;

	// The closest boundary is not chosen by distance but by number of halfedges
	// inbetween.
	private HalfEdge closestBoundary;

	public TerrainTriangle(Vertex p1, Vertex p2, Vertex p3) {
		super(p1, p2, p3);
	}

	public int getTerrainIndex() {
		return terrainIndex;
	}

	public void setRandomTerrainIndex() {
		terrainIndex = (int) Math.round(Math.random() * textureGridSize * textureGridSize);
	}

	public Vector2f[] getTextureCoordinates() {
		int row = terrainIndex / textureGridSize;
		int col = terrainIndex % textureGridSize;
		float squareSize = 1.0f / textureGridSize;
		Vector2f tip = new Vector2f(col * squareSize + PADDING, row * squareSize + PADDING);
		return new Vector2f[] { tip, Vector2f.add(tip, new Vector2f(squareSize - 2 * PADDING, 0)), Vector2f.add(tip, new Vector2f(squareSize * 0.5f - PADDING, squareSize - 2 * PADDING)) };
	}

	public static void setTextureGridSize(int textureGridSize) {
		TerrainTriangle.textureGridSize = textureGridSize;
	}

	public void setTerrainIndex(int terrainIndex) {
		this.terrainIndex = terrainIndex;
	}

	public Vector3f getMovementVector() {
		return movementVector;
	}

	public void setMovementVector(Vector3f movementVector) {
		this.movementVector = movementVector;
	}

	public HalfEdge getClosestBoundary() {
		return closestBoundary;
	}

	public void setClosestBoundary(HalfEdge closestBoundary) {
		this.closestBoundary = closestBoundary;
	}

}
