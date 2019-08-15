package logics.globe;

public class Globe {

	private int subdivisions;
	private int scale;

//	public GlobeRawData subdivide(GlobeRawData data, int iterations) {
//		GlobeRawData currentSubdividedGlobeMesh;
//		GlobeRawData nextSubdividedGlobeMesh = data;
//		for (int i = 0; i < iterations; i++) {
//			currentSubdividedGlobeMesh = nextSubdividedGlobeMesh;
//			nextSubdividedGlobeMesh = new GlobeRawData();
//			for (Triangle triangle : currentSubdividedGlobeMesh.getTriangles()) {
//				Vertex p1 = triangle.getPoint1();
//				Vertex p2 = triangle.getPoint2();
//				Vertex p3 = triangle.getPoint3();
//				// Interpolated
//				Vertex ip12 = new Vertex(Vector3f.add(p1.vector(), p2.vector()).normalise(null));
//				Vertex ip23 = new Vertex(Vector3f.add(p2.vector(), p3.vector()).normalise(null));
//				Vertex ip31 = new Vertex(Vector3f.add(p3.vector(), p1.vector()).normalise(null));
//				nextSubdividedGlobeMesh.addTriangle(new Triangle(p1, ip12, ip31));
//				nextSubdividedGlobeMesh.addTriangle(new Triangle(ip12, p2, ip23));
//				nextSubdividedGlobeMesh.addTriangle(new Triangle(ip12, ip23, ip31));
//				nextSubdividedGlobeMesh.addTriangle(new Triangle(ip31, ip23, p3));
//			}
//		}
//		return nextSubdividedGlobeMesh;
//	}

}
