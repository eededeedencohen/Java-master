package geometries;
import geometries.Intersectable.GeoPoint;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
class SphereTest {

    @Test
    void getNormal() {
        Sphere sp1 = new Sphere(new Point3D(0, 0, 1),1.0);
        assertEquals(new Vector(0,0,1),sp1.getNormal(new Point3D(0,0,2)));
        Sphere sp2=new Sphere(new Point3D(1,1,1),1d);
        assertEquals(new Vector(-1,0,0),sp2.getNormal(new Point3D(0,1,1)));
        Sphere sp = new Sphere(new Point3D(0,0,1),1);
        assertNotEquals(new Vector(0,0,1),sp.getNormal(new Point3D(0,1,1)));
       // System.out.println(sp.getNormal(new Point3D(0,1,1)));
    }
    /**
     * @author Dan Zilberstein
     */

    @Test
    void findIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);


        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");


        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals( 2, result.size(),"wrong result");
        if (result.get(0).point.get_y().get() > result.get(1).point.get_y().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");


        // TC03: Ray starts inside the sphere (1 point)
        assertEquals(List.of(p2), sphere.findIntersections(new Ray(new Point3D(0.5, 0.5, 0), new Vector(3, 1, 0))),
                "Ray from inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull( sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(3, 1, 0))));

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(new Point3D(2, 0, 0)), sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(1, 1, 0))),
                "Ray from sphere inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 1, 0))));

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, -2, 0), new Vector(0, 1, 0)));

        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).point.get_y().get() > result.get(1).point.get_y().get()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point3D(1, -1, 0), new Point3D(1, 1, 0)), result, "Line through O, ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(new Point3D(1, 1, 0)), sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(0, 1, 0))),
                "Line through O, ray from and crosses sphere");

        // TC15: Ray starts inside (1 points)
        assertEquals(List.of(new Point3D(1, 1, 0)), sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, 1, 0))),
                "Line through O, ray from inside sphere");

        // TC16: Ray starts at the center (1 points)
        assertEquals(List.of(new Point3D(1, 1, 0)), sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))),
                "Line through O, ray from O");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull( sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0))));

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0, 1, 0))));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull( sphere.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0)))
        );

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0)))
        );

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0)))
        );

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        assertNull( sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(0, 0, 1))));

    }


}