package elements;

import geometries.Intersectable.GeoPoint;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CameraIntegrationTest {
    Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
    Camera cam2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));

    // all the tests with same view plane !!
    @Test
    void constructRayThroughPixelWithSphere1() {

        ///****TC01  - 2 intersections with the little sphere
        Sphere sph = new Sphere(new Point3D(0, 0, 3), 1);
        List<GeoPoint> results = null;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Nx; ++i) {
            for (int j = 0; j < Ny; ++j) {
                results = sph.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null) {
                    count += results.size();
                 //    System.out.print(results.get(0) + ",");
                //      System.out.print(results.get(1));
                //    System.out.println();
                }

            }
        }
        assertEquals(2, count, "wrong number of points");
        System.out.println("count: " + count);
    }

    @Test
    void constructRayThroughPixelWithSphere2() {

        ///TC02 in the presentation - 18 intersections with the big sphere
        Sphere sph =  new Sphere(new Point3D(0, 0, 2.5), 2.5);

        List<GeoPoint> results;
        int count = 0;

        int Nx =3;
        int Ny =3;


        for (int i = 0; i < Nx; ++i) {
            for (int j = 0; j < Ny; ++j) {
                results = sph.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals(18,count,"wrong number of points");
        System.out.println("count: "+count);
    }


    @Test
    void constructRayThroughPixelWithSphere3() {

        ///TC03 in the presentation - 10 intersections with the sphere- the view plane start with the view plane

        Sphere sph = new Sphere(new Point3D(0, 0, 2), 2d);
        List<GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sph.findIntersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null) {
                    count += results.size();
                    //  System.out.print(results.get(0) + ",");
                    //  System.out.print(results.get(1));
                    // System.out.println();
                }
            }
        }

        assertEquals(10, count, "wrong points");
        System.out.println("count: " + count);
    }

    @Test
    void constructRayThroughPixelWithSphere4() {

        ///TC04 in the presentation - 9 intersections with the sphere -view plane in the sphere

        Sphere sph = new Sphere(new Point3D(0, 0, 0), 4d);
        List<GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sph.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null) {
                    count += results.size();
                    //  System.out.print(results.get(0) + ",");
                    //  System.out.print(results.get(1));
                    // System.out.println();
                }
            }
        }

        assertEquals(9, count, "wrong points");
        System.out.println("count: " + count);
    }

    @Test
    void constructRayThroughPixelWithSphere5() {

        ///TC05 in the presentation - 0 intersections with the sphere -behind the view plane

        Sphere sph = new Sphere(new Point3D(0, 0, -1), 0.5);
        List<GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sph.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null) {
                    count += results.size();
                    //  System.out.print(results.get(0) + ",");
                    //  System.out.print(results.get(1));
                    // System.out.println();
                }
            }
        }

        assertEquals(0, count, "wrong points");
        System.out.println("count: " + count);
    }

    @Test
    void constructRayThroughPixelWithPlane1() {
        //TC06 in the presentation - 9 intersections with the plane -the plane parallel to the view plane
        Plane pl1 = new Plane(new Point3D(0, 0, 4), new Point3D(1, 0, 4), new Point3D(0, 1, 4));
        List<GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = pl1.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null) {
                    count += results.size();
                    //  System.out.print(results.get(0) + ",");
                    //  System.out.print(results.get(1));
                    // System.out.println();
                }
            }
        }

        assertEquals(9, count, "wrong points");
        System.out.println("count: " + count);
    }

    @Test
    void constructRayThroughPixelWithPlane2() {
        //TC07 in the presentation - 9 intersections with the plane -the plane intersects the view plane
        Plane pl1 = new Plane(new Point3D(0, -2, 1), new Point3D(0, 1, 0), new Point3D(1, 0, 0));
        List<GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = pl1.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null) {
                    count += results.size();
                    //  System.out.print(results.get(0) + ",");
                    //  System.out.print(results.get(1));
                    // System.out.println();
                }
            }
        }

        assertEquals(9, count, "wrong points");
        System.out.println("count: " + count);
    }

    @Test
    void constructRayThroughPixelWithPlane3() {
        //TC08 in the presentation - 6 intersections with the plane -the plane intersects the view plane
        Plane pl1 = new Plane(new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(1, 0, 0));
        List<GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = pl1.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null) {
                    count += results.size();
                    //  System.out.print(results.get(0) + ",");
                    //  System.out.print(results.get(1));
                    // System.out.println();
                }
            }
        }

        assertEquals(6, count, "wrong points");
        System.out.println("count: " + count);
    }

    @Test
    void constructRayThroughPixelWithTriangle1() {
        //TC09 in the presentation - 1 intersections with the triangle
        // -the triangle parallel to the view plane and small!
        Triangle t1 = new Triangle(new Point3D(0, -1, 3), new Point3D(-1, 1, 2), new Point3D(1, 1, 2));
        List<GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = t1.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null) {
                    count += results.size();
                    // System.out.print(results.get(0) + ",");
                    // System.out.print(results.get(1));
                    System.out.println();
                }
            }
        }

        assertEquals(1, count, "wrong points");
        System.out.println("count: " + count);
    }

    @Test
    void constructRayThroughPixelWithTriangle2() {
        //TC10 in the presentation - 9 intersections with the triangle -
        // the triangle parallel to the view plane and big!
        Triangle t1 = new Triangle(new Point3D(0, -3, 2), new Point3D(-1, 1, 2), new Point3D(1, 1, 2));
        List<GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = t1.findIntersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null) {
                    count += results.size();
                    // System.out.print(results.get(0) + ",");
                    // System.out.print(results.get(1));
                    // System.out.println();
                }
            }
        }

        assertEquals(2, count, "wrong points");
        System.out.println("count: " + count);
    }
}
