package graphics.primitive;

import java.util.ArrayList;
import java.util.List;

import logics.globe.TerrainTriangle;
import math.Vector3f;

public class Primitive {

	/**
	 * Calculates the normal of the given triangle. The magnitude of the normal
	 * vector is porportional to the triangle's area.
	 * 
	 * @param edge
	 * @return the normal
	 */
	public static Vector3f getTriangleNormal(Triangle triangle) {
		HalfEdge nextEdge = triangle.getEdge().getNext();
		Vector3f planeVector1 = Vector3f.sub(triangle.getEdge().getVertex().getPosition(), nextEdge.getVertex().getPosition());
		Vector3f planeVector2 = Vector3f.sub(nextEdge.getNext().getVertex().getPosition(), nextEdge.getVertex().getPosition());
		Vector3f cross = new Vector3f(Vector3f.cross(planeVector2, planeVector1));
		return cross;
	}

	/**
	 * Finds whether or not a half edge exists between the two points and returns it
	 * if it does or creates a new one if it doesn't.
	 * 
	 * @param from
	 * @param to
	 * @return the half edge if it exists or null if it doesnt
	 */
	public static HalfEdge findHalfEdge(Vertex from, Vertex to) {
		for (HalfEdge edge : from.getEmanatingEdges()) {
			if (edge.getVertex().equals(to)) {
				return edge;
			}
		}
		return new HalfEdge(to);
	}

	public static Vertex getMidpointOfHalfEdge(HalfEdge edge) {
		Vertex midpoint = edge.getMidpoint();
		if (midpoint != null) {
			return midpoint;
		}
		Vector3f to = edge.getVertex().getPosition();
		Vector3f from = edge.getNext().getNext().getVertex().getPosition();
		Vector3f midpointVector = Vector3f.add(to, from).scale(0.5f);
		midpoint = new Vertex(midpointVector.x, midpointVector.y, midpointVector.z);
		edge.setMidpoint(midpoint);
		edge.getPair().setMidpoint(midpoint);
		return midpoint;
	}

	public static void pairEdges(HalfEdge edge1, HalfEdge edge2) {
		edge1.setPair(edge2);
		edge2.setPair(edge1);
	}

	public static void pairWithPotentialOtherEdge(HalfEdge edge, Vertex from) {
		HalfEdge potentialPairEdge = Primitive.findHalfEdge(edge.getVertex(), from);
		if (potentialPairEdge != null) {
			Primitive.pairEdges(edge, potentialPairEdge);
		}
	}

	public static List<Triangle> getAdjacentTriangles(Triangle triangle) {
		List<Triangle> adjacentTriangles = new ArrayList<>();
		HalfEdge edge = triangle.getEdge();
		adjacentTriangles.add(edge.getPair().getTriangle());
		HalfEdge next = edge.getNext();
		Triangle nextTriangle = next.getPair().getTriangle();
		if (!nextTriangle.equals(adjacentTriangles.get(0))) {
			adjacentTriangles.add(nextTriangle);
		}
		Triangle previousTriangle = next.getNext().getPair().getTriangle();
		if (!adjacentTriangles.contains(previousTriangle)) {
			adjacentTriangles.add(previousTriangle);
		}
		return adjacentTriangles;
	}

	public static List<TerrainTriangle> getAdjacentTerrainTriangles(TerrainTriangle triangle) {
		List<Triangle> adjacentTriangles = Primitive.getAdjacentTriangles(triangle);
		List<TerrainTriangle> adjacentTerrainTriangles = new ArrayList<>();
		for (Triangle triangleAdjacentTriangle : adjacentTriangles) {
			adjacentTerrainTriangles.add((TerrainTriangle) triangleAdjacentTriangle);
		}
		return adjacentTerrainTriangles;
	}

	/**
	 * Given two triangles this method determines whether or not an edge is shared
	 * between the two and returns the halfedge of the first triangle from the pair.
	 * 
	 * @param triangle1
	 * @param triangle2
	 * @return the halfedge of triangle1 if the edge is shared or null if an edge is
	 *         not shared
	 */
	public static HalfEdge getSharedEdge(Triangle triangle1, Triangle triangle2) {
		HalfEdge firstTriangleEdge = triangle1.getEdge();
		for (int i = 0; i < 3; i++) {
			firstTriangleEdge = firstTriangleEdge.getNext();
			HalfEdge firstTriangleEdgePair = firstTriangleEdge.getPair();
			HalfEdge secondTriangleEdge = triangle2.getEdge();
			for (int j = 0; j < 3; j++) {
				secondTriangleEdge = secondTriangleEdge.getNext();
				if (firstTriangleEdgePair.equals(secondTriangleEdge)) {
					return firstTriangleEdge;
				}
			}
		}
		return null;
	}

	public static List<HalfEdge> getEdges(Triangle triangle) {
		List<HalfEdge> list = new ArrayList<>();
		list.add(triangle.getEdge());
		list.add(list.get(0).getNext());
		list.add(list.get(1).getNext());
		return list;
	}

}
