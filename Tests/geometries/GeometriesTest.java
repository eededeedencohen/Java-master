package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for intersection of shapes together
 *
 */
class GeometriesTest {

    @Test
    void findIntersections() {

        Plane pl1=new Plane(new Point3D(0,1,0),new Point3D(1,0,0),new Point3D(0,0,1));
        Sphere sp1 = new Sphere(new Point3D(0, 0, 1),1.0);
        Triangle t1=new Triangle(new Point3D(0,1,0),new Point3D(1,0,0),new Point3D(0,0,1));
        List<Intersectable>shapes= List.of(pl1,sp1,t1);
        Geometries geo=new Geometries(shapes);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects 2 from 3 of the shapes (2 points)
        Ray ray1= new Ray(new Point3D(-1, -0.5, 1), new Vector(1, -0.25, -0.75));
        assertEquals(2,geo.findIntersections(ray1).size(), "Ray intersects 2 from 3 of the shapes" );

        // =============== Boundary Values Tests ==================
        // TC02: empty list of shapes (0 points)
        Geometries Empty_geo=new Geometries();
        Ray ray2= new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0));
        assertNull(Empty_geo.findIntersections(ray2), "empty list of shapes " );
        // TC03: Ray doesn't intersect the shapes (0 points)
        Ray ray3= new Ray(new Point3D(2, 1, 0), new Vector(1, 2, 0));
        assertNull(geo.findIntersections(ray3), "Ray doesn't intersect the shapes" );

        // TC04: Ray intersects only one of the shapes (1 points)
        Ray ray4= new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0));
        assertEquals(1,geo.findIntersections(ray4).size(), "Ray intersects only one of the shapes" );

        // TC05: Ray intersects all the shapes (3 points)
        Ray ray5= new Ray(new Point3D(-1, -1, 1), new Vector(1, 1, -1));
        assertEquals(3,geo.findIntersections(ray5).size(), "Ray intersects all  the shapes" );
        }
    }
