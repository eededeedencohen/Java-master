package geometries;
import geometries.Intersectable.GeoPoint;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void getNormal() {
        Triangle t1=new Triangle(new Point3D(0,1,0),new Point3D(1,0,0),new Point3D(0,0,1));
        Vector v1=t1.getNormal(new Point3D(3,3,3));
        Triangle t2=new Triangle(new Point3D(0,0,1),new Point3D(1,0,0),new Point3D(0,1,0));
        Vector v2=t2.getNormal(new Point3D(3,3,3));
        assertNotEquals(v1,v2," the same direct and its wrong");
        Plane pl3=new Plane(new Point3D(1,0,0),new Point3D(0,0,1),new Point3D(0,1,0));
        Vector v3=pl3.getNormal();
        assertEquals(v1,v3,"not the same direct and its wrong");
    }

    @Test
    void  findIntersections() {
        Triangle t1=new Triangle(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
        Plane pl1=new Plane(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
        // ============ Equivalence Partitions Tests ==============
        Ray ray1=new Ray (new Point3D(0,-1,0),new Vector(1,3,1));
        // TC01: Ray intersects the triangle (1 points)
        List<GeoPoint> result0=t1.findIntersections(ray1);
        assertEquals( 1, result0.size(),"the ray intersect the triangle");

        // TC02: Ray doesn't intersects the triangle-against the edge (0 points)
        Ray ray2=new Ray (new Point3D(0,-1,0),new Vector(2,2,1));
        assertNull(t1.findIntersections(ray2),"the ray doesn't intersect the triangle-against the edge ");
        // TC03: Ray doesn't intersects the triangle-against the vertices (0 points)
        Ray ray3=new Ray (new Point3D(0,-1,0),new Vector(2,1,0));
        assertNull(t1.findIntersections(ray3),"the ray doesn't intersect the triangle-against the vertices ");

        // =============== Boundary Values Tests ==================
        // TC04: Ray  intersects the triangle on the edge (0 points)
        Ray ray4=new Ray (new Point3D(0,-1,0),new Vector(1,2,1));
        assertNull(t1.findIntersections(ray4)," Ray  intersects the triangle on the edge ");

        // TC05: Ray  intersects the triangle on the vertices (0 points)
        Ray ray5=new Ray (new Point3D(0,-1,0),new Vector(1,1,0));
        assertNull(t1.findIntersections(ray5)," Ray intersects the triangle on the vertices");

        // TC06: Ray intersects on edge's continuation (0 points)
        Ray ray6=new Ray (new Point3D(0,-1,0),new Vector(1,0,0));
        assertNull(t1.findIntersections(ray6),"  Ray intersects on edge's continuation ");



    }
}