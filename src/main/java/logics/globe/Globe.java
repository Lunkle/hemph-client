package logics.globe;

import graphics.model.VAO;
import graphics.primitive.Point;
import graphics.primitive.Triangle;
import math.Vector2f;
import math.Vector3f;

public class Globe {

	private VAO mesh;

	public Globe(int iterations) {
		MeshData icosahedron = createIcosahedoron();
		MeshData subdividedIcosahedron = subdivide(icosahedron, iterations);
		mesh = subdividedIcosahedron.toMesh();
	}

	public VAO getMesh() {
		return mesh;
	}

	private MeshData createIcosahedoron() {
		Vector2f vector = new Vector2f(1, (float) (2 * Math.sin(Math.toRadians(54))));
		vector.normalise();
		float a = vector.x;
		float b = vector.y;
		// XY plane rectangle
		Point p1 = new Point(-a, b, 0);
		Point p2 = new Point(a, b, 0);
		Point p3 = new Point(-a, -b, 0);
		Point p4 = new Point(a, -b, 0);
		// YZ plane rectangle
		Point p5 = new Point(0, -a, b);
		Point p6 = new Point(0, a, b);
		Point p7 = new Point(0, -a, -b);
		Point p8 = new Point(0, a, -b);
		// XZ plane rectangle
		Point p9 = new Point(b, 0, -a);
		Point p10 = new Point(b, 0, a);
		Point p11 = new Point(-b, 0, -a);
		Point p12 = new Point(-b, 0, a);

		MeshData globeMesh = new MeshData();
		// Top five triangles around point 1
		globeMesh.addTriangle(new Triangle(p1, p11, p12));
		globeMesh.addTriangle(new Triangle(p1, p12, p6));
		globeMesh.addTriangle(new Triangle(p1, p6, p2));
		globeMesh.addTriangle(new Triangle(p1, p2, p8));
		globeMesh.addTriangle(new Triangle(p1, p8, p11));
		// Middle ten triangles forming a strip
		globeMesh.addTriangle(new Triangle(p12, p11, p3));
		globeMesh.addTriangle(new Triangle(p12, p3, p5));
		globeMesh.addTriangle(new Triangle(p6, p12, p5));
		globeMesh.addTriangle(new Triangle(p6, p5, p10));
		globeMesh.addTriangle(new Triangle(p2, p6, p10));
		globeMesh.addTriangle(new Triangle(p2, p10, p9));
		globeMesh.addTriangle(new Triangle(p8, p2, p9));
		globeMesh.addTriangle(new Triangle(p8, p9, p7));
		globeMesh.addTriangle(new Triangle(p11, p8, p7));
		globeMesh.addTriangle(new Triangle(p11, p7, p3));
		// Bottom five triangles around point 4
		globeMesh.addTriangle(new Triangle(p4, p9, p10));
		globeMesh.addTriangle(new Triangle(p4, p10, p5));
		globeMesh.addTriangle(new Triangle(p4, p5, p3));
		globeMesh.addTriangle(new Triangle(p4, p3, p7));
		globeMesh.addTriangle(new Triangle(p4, p7, p9));

		return globeMesh;
	}

	public MeshData subdivide(MeshData data, int iterations) {
		MeshData currentSubdividedGlobeMesh;
		MeshData nextSubdividedGlobeMesh = data;
		for (int i = 0; i < iterations; i++) {
			currentSubdividedGlobeMesh = nextSubdividedGlobeMesh;
			nextSubdividedGlobeMesh = new MeshData();
			for (Triangle triangle : currentSubdividedGlobeMesh.getTriangles()) {
				Point p1 = triangle.getPoint1();
				Point p2 = triangle.getPoint2();
				Point p3 = triangle.getPoint3();
				// Interpolated
				Point ip12 = new Point(Vector3f.add(p1.vector(), p2.vector()).normalise(null));
				Point ip23 = new Point(Vector3f.add(p2.vector(), p3.vector()).normalise(null));
				Point ip31 = new Point(Vector3f.add(p3.vector(), p1.vector()).normalise(null));
				nextSubdividedGlobeMesh.addTriangle(new Triangle(p1, ip12, ip31));
				nextSubdividedGlobeMesh.addTriangle(new Triangle(ip12, p2, ip23));
				nextSubdividedGlobeMesh.addTriangle(new Triangle(ip12, ip23, ip31));
				nextSubdividedGlobeMesh.addTriangle(new Triangle(ip31, ip23, p3));
			}
		}
		return nextSubdividedGlobeMesh;
	}

}
