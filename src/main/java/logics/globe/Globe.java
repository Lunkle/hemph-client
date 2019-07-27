//package logics.globe;
//
//import graphics.model.Point;
//import graphics.model.Triangle;
//
//public class Globe {
//
//	private int degree = 1;
//
//	private static float[] positions;
//
//	private void createIcosahedoron() {
//		float t = (1.0f + (float) Math.sqrt(5.0f)) / 2.0f;
//
//		Point p1 = new Point(-1, t, 0);
//		Point p2 = new Point(1, t, 0);
//		Point p3 = new Point(-1, -t, 0);
//		Point p4 = new Point(1, -t, 0);
//		Point p5 = new Point(0, -1, t);
//		Point p6 = new Point(0, 1, t);
//		Point p7 = new Point(0, -1, -t);
//		Point p8 = new Point(0, 1, -t);
//		Point p9 = new Point(t, 0, -1);
//		Point p10 = new Point(t, 0, 1);
//		Point p11 = new Point(-t, 0, -1);
//		Point p12 = new Point(-t, 0, 1);
//
//		Triangle t1 = new Triangle(p1, p12, p6);
//		Triangle t2 = new Triangle(p1, p6, p2);
//		Triangle t3 = new Triangle(p1, p2, p8);
//		Triangle t4 = new Triangle(p1, p8, p11);
//		Triangle t5 = new Triangle(p1, p11, p12);
//
//		// 5 adjacent faces
//		Triangle t1 = new Triangle(p2, p6, p10);
//		Triangle t1 = new Triangle(p6, p12, p5);
//		Triangle t1 = new Triangle(p12, p11, p3);
//
//		faces.Add(new TriangleIndices(10, 7, 6));
//		faces.Add(new TriangleIndices(7, 1, 8));
//
//		// 5 faces around point 3
//		faces.Add(new TriangleIndices(3, 9, 4));
//		faces.Add(new TriangleIndices(3, 4, 2));
//		faces.Add(new TriangleIndices(3, 2, 6));
//		faces.Add(new TriangleIndices(3, 6, 8));
//		faces.Add(new TriangleIndices(3, 8, 9));
//
//		// 5 adjacent faces
//		faces.Add(new TriangleIndices(4, 9, 5));
//		faces.Add(new TriangleIndices(2, 4, 11));
//		faces.Add(new TriangleIndices(6, 2, 10));
//		faces.Add(new TriangleIndices(8, 6, 7));
//		faces.Add(new TriangleIndices(9, 8, 1));
//
//	}
//
//}
