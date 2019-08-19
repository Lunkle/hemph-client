package logics.globe;

import java.util.ArrayList;
import java.util.List;

import graphics.primitive.HalfEdge;
import graphics.primitive.Primitive;

public class Plate {

	private List<TerrainTriangle> triangles;
	private List<TerrainTriangle> adjacentEmptyTriangles;
	private List<HalfEdge> boundaries;
	private Biomes biome;

	public Plate(Biomes biome, TerrainTriangle triangle) {
		this.biome = biome;
		triangles = new ArrayList<>();
		adjacentEmptyTriangles = new ArrayList<>();
		boundaries = new ArrayList<>();
		triangles.add(triangle);
		for (TerrainTriangle adjacentTriangle : Primitive.getAdjacentTerrainTriangles(triangle)) {
			if (adjacentTriangle.getTerrainIndex() == -1) {
				adjacentEmptyTriangles.add(adjacentTriangle);
			}
		}
		HalfEdge edge = triangle.getEdge();
		boundaries.add(edge);
		HalfEdge next = edge.getNext();
		boundaries.add(next);
		boundaries.add(next.getNext());
		triangle.setTerrainIndex(biome.getIndex());
	}

	public boolean addTriangle(TerrainTriangle triangle) {
		if (triangle.getTerrainIndex() == -1 && adjacentEmptyTriangles.contains(triangle)) {
			triangles.add(triangle);
			adjacentEmptyTriangles.remove(adjacentEmptyTriangles.indexOf(triangle));
			List<HalfEdge> potentialBoundaries = new ArrayList<>();
			potentialBoundaries.add(triangle.getEdge());
			potentialBoundaries.add(triangle.getEdge().getNext());
			potentialBoundaries.add(triangle.getEdge().getNext().getNext());
			List<HalfEdge> newBoundaries = new ArrayList<>();
			for (HalfEdge boundary : potentialBoundaries) {
				if (boundaries.contains(boundary.getPair()))
					boundaries.remove(boundary.getPair());
				else
					newBoundaries.add(boundary);
			}
			for (HalfEdge boundary : potentialBoundaries) {
				boundaries.add(boundary);
			}
			for (TerrainTriangle adjacentTriangle : Primitive.getAdjacentTerrainTriangles(triangle)) {
				if (!adjacentEmptyTriangles.contains(adjacentTriangle) && adjacentTriangle.getTerrainIndex() == -1) {
					adjacentEmptyTriangles.add(adjacentTriangle);
				}
			}
			triangle.setTerrainIndex(biome.getIndex());
			return true;
		}
		return false;
	}

	public List<TerrainTriangle> getAdjacentTriangles() {
		return adjacentEmptyTriangles;
	}

	public List<TerrainTriangle> getTriangles() {
		return triangles;
	}

	public List<HalfEdge> getBoundaries() {
		return boundaries;
	}

}
