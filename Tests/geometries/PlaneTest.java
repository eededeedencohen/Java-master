package geometries;
import geometries.Intersectable.GeoPoint;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void getNormal() {
        Plane pl1=new Plane(new Point3D(0,1,0),new Point3D(1,0,0),new Point3D(0,0,1));
        Vector v1=pl1.getNormal();
      //  System.out.println(v1.toString());
        Plane pl2=new Plane(new Point3D(0,0,1),new Point3D(1,0,0),new Point3D(0,1,0));
        Vector v2=pl2.getNormal();
        assertNotEquals(v1,v2," the same direct and its wrong");
       // System.out.println(v2.toString());
        Plane pl3=new Plane(new Point3D(1,0,0),new Point3D(0,0,1),new Point3D(0,1,0));
        Vector v3=pl3.getNormal();
        assertEquals(v1,v3,"not the same direct and its wrong");

    }
    @Test
    void findIntersections(){
        Plane pl1=new Plane(new Point3D(0,1,0),new Point3D(1,0,0),new Point3D(0,0,1));
        //System.out.println(pl1.normal);
        // ============ Equivalence Partitions Tests ==============
        Ray ray1=new Ray (new Point3D(0,-1,0),new Vector(1,3,1));
        // TC01: Ray intersects the plane (1 points)
        List<GeoPoint> result0=pl1.findIntersections(ray1);
        assertEquals( 1, result0.size(),"Ray intersected the plane");

        // TC02: Ray  doesn't intersects the plane(0 points)
        Ray ray2=new Ray (new Point3D(3,4,2),new Vector(1,2,1));
       // System.out.println(pl1.findIntersections(ray2));
         assertNull(pl1.findIntersections(ray2),"Ray's line out of sphere");


      // =============== Boundary Values Tests ==================
        // TC03 :Ray is parallel to the plane and included in the plane (0 points)
        Ray ray3=new Ray (new Point3D(0.5,0.5,0),new Vector(1,-1,0));
        assertNull(pl1.findIntersections(ray3),"Ray is parallel to the plane and included in the plane");

        // TC04 :Ray is parallel to the plane and doesn't included in the plane (0 points)
        Ray ray4=new Ray (new Point3D(0.5,0,0),new Vector(1,-1,0));
        assertNull(pl1.findIntersections(ray4),"Ray is parallel to the plane and doesn't included in the plane");

        // TC05 :Ray is orthogonal to the plane and  before the plane (1 points)
        Ray ray5=new Ray (new Point3D(1,1,1),
                new Vector(-0.5773502691896258,-0.5773502691896258,-0.5773502691896258));
        List<GeoPoint> result1=pl1.findIntersections(ray5);
        assertEquals( 1, result1.size(),"Wrong number of points");

        // TC06 :Ray is orthogonal to the plane and in the plane (0 points)
        Ray ray6=new Ray (new Point3D(0.5,0.25,0.25),
                new Vector(-0.5773502691896258,-0.5773502691896258,-0.5773502691896258));
        List<GeoPoint> result2=pl1.findIntersections(ray6);
        assertNull(pl1.findIntersections(ray6),"Ray is orthogonal to the plane and in the plane  ");


        // TC07 :Ray is orthogonal to the plane and after the plane (0 points)
        Ray ray7=new Ray (new Point3D(-1,-1,-1),
                new Vector(-0.5773502691896258,-0.5773502691896258,-0.5773502691896258));
        assertNull(pl1.findIntersections(ray7),"Ray is orthogonal to the plane and after the plane ");

        // TC08 :Ray is neither orthogonal nor parallel  and begins at the plane (0 points)
        Ray ray8=new Ray (new Point3D(0.5,0.25,0.25),
                new Vector(-4,1,0));
        assertNull(pl1.findIntersections(ray8),"Ray is neither orthogonal nor parallel  and begins at the plane ");

        // TC09 :Ray is neither orthogonal nor parallel  and  begins in
        //the same point which appears as reference point in the plane (0 points)
        Ray ray9=new Ray (new Point3D(0,1,0),new Vector(-2,1,1));
        assertNull(pl1.findIntersections(ray9),"Ray is neither orthogonal nor parallel  and  begins in " +
                "the same point which appears as reference point in the plane");
    }
}