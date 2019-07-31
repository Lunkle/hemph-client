//package logics.globe;
//
//import graphics.model.VAO;
//import graphics.primitive.Point;
//import graphics.primitive.Triangle;
//import graphics.transformation.WorldTransformation;
//
//public class Globe {
//
//	private WorldTransformation transformation;
//	private VAO mesh;
//
//	private int degree = 1;
//	private static float[] positions;
//
//	public Globe(float posX, float posY, float posZ) {
//		transformation = new WorldTransformation(posX, posY, posZ, 0, 0, 0, 1, 1, 1);
//		mesh = createIcosahedoron();
//	}
//
//	private VAO createIcosahedoron() {
//		float t = (1.0f + (float) Math.sqrt(5.0f)) / 2.0f;
//
//		// XY plane rectangle
//		Point p1 = new Point(-1, t, 0);
//		Point p2 = new Point(1, t, 0);
//		Point p3 = new Point(-1, -t, 0);
//		Point p4 = new Point(1, -t, 0);
//
//		// YZ plane rectangle
//		Point p5 = new Point(0, -1, t);
//		Point p6 = new Point(0, 1, t);
//		Point p7 = new Point(0, -1, -t);
//		Point p8 = new Point(0, 1, -t);
//
//		// XZ plane rectangle
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
//		Triangle t6 = new Triangle(p2, p6, p10);
//		Triangle t7 = new Triangle(p6, p12, p5);
//		Triangle t8 = new Triangle(p12, p11, p3);
//		Triangle t9 = new Triangle(p12, p11, p3);
//		Triangle t10 = new Triangle(p12, p11, p3);
//		Triangle t11 = new Triangle(p12, p11, p3);
//		Triangle t12 = new Triangle(p12, p11, p3);
//		Triangle t13 = new Triangle(p12, p11, p3);
//		Triangle t14 = new Triangle(p12, p11, p3);
//		Triangle t15 = new Triangle(p12, p11, p3);
//		Triangle t16 = new Triangle(p12, p11, p3);
//		Triangle t17 = new Triangle(p12, p11, p3);
//		Triangle t18 = new Triangle(p12, p11, p3);
//		Triangle t19 = new Triangle(p12, p11, p3);
//		Triangle t20 = new Triangle(p12, p11, p3);
//
//	}
//
//}
