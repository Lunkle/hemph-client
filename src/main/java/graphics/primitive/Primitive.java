package graphics.primitive;

import math.Vector3f;

public class Primitive {

	/**
	 * Calculates the normal of a triangle given one of the triangle's half edges.
	 * The magnitude of the normal vector is porportional to the triangle's area.
	 * 
	 * @param edge
	 * @return the normal
	 */
	public static Vector3f getTriangleNormal(HalfEdge edge) {
		HalfEdge nextEdge = edge.getNext();
		Vector3f planeVector1 = Vector3f.sub(edge.getVertex().getPosition(), nextEdge.getVertex().getPosition());
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
	public static HalfEdge findHalfEdge(FullAdjacencyVertex from, FullAdjacencyVertex to) {
		for (HalfEdge edge : from.getEmanatingEdges()) {
			if (edge.getVertex().equals(to)) {
				return edge;
			}
		}
		return new HalfEdge(to);
	}

	public static void pairEdges(HalfEdge edge1, HalfEdge edge2) {
		edge1.setPair(edge2);
		edge2.setPair(edge1);
	}

	public static void pairWithPotentialOtherEdge(HalfEdge edge, FullAdjacencyVertex from) {
		HalfEdge potentialPairEdge = Primitive.findHalfEdge(edge.getVertex(), from);
		if (potentialPairEdge != null) {
			Primitive.pairEdges(edge, potentialPairEdge);
		}
	}

}
