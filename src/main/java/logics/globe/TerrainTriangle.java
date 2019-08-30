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
		HalfEdge next = edge.getNext();
		Vector3f p1 = edge.getVertex().getPosition();
		Vector3f p2 = next.getVertex().getPosition();
		Vector3f p3 = next.getNext().getVertex().getPosition();
		Vector3f[] sideVectors = new Vector3f[] { Vector3f.sub(p2, p1), Vector3f.sub(p3, p2), Vector3f.sub(p1, p3) };
		float[] sideLengths = new float[] { sideVectors[0].length(), sideVectors[1].length(), sideVectors[2].length() };
		int indexOfLongestSide = 0;
		for (int i = 1; i < 3; i++) {
			if (sideLengths[i] > sideLengths[0])
				indexOfLongestSide = i;
		}
		int indexOfNext = (indexOfLongestSide == 2 ? 0 : indexOfLongestSide + 1);
//
		float thirdPointXValue = -Vector3f.dot(sideVectors[indexOfNext], sideVectors[indexOfLongestSide]) / (sideLengths[indexOfLongestSide] * sideLengths[indexOfLongestSide]);
		float thirdPointYValue = (float) Math.sqrt(sideLengths[indexOfNext] * sideLengths[indexOfNext] - thirdPointXValue * thirdPointXValue) / sideLengths[indexOfLongestSide];
//		System.out.println(thirdPointXValue + " " + thirdPointYValue);
		Vector2f tip = new Vector2f(col * squareSize + PADDING, row * squareSize + PADDING);
		return new Vector2f[] { tip, Vector2f.add(tip, new Vector2f(squareSize - 2 * PADDING, 0)), Vector2f.add(tip, new Vector2f(squareSize * 0.5f - PADDING, squareSize - 2 * PADDING)) };
//		return new Vector2f[] { tip, tip, tip };
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
