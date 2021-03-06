package elements;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CameraTest {

    @Test
    void constructRayThroughPixel() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
       // System.out.println(camera._vRight);
        assertEquals(new Ray(camera.get_p0(), new Vector(-2, -2, 10)), camera.constructRayThroughPixel(3, 3, 0, 0, 10, 6, 6),
                "Bad ray");

        // TC02: 4X4 Corner (0,0)
        assertEquals(new Ray(camera.get_p0(), new Vector(-3, -3, 10)), camera.constructRayThroughPixel(4, 4, 0, 0, 10, 8, 8),
                "Bad ray");

        // TC03: 4X4 Side (0,1)
        assertEquals(new Ray(camera.get_p0(), new Vector(-1, -3, 10)), camera.constructRayThroughPixel(4, 4, 1, 0, 10, 8, 8),
                "Bad ray");

        // TC04: 4X4 Inside (1,1)
        assertEquals(new Ray(camera.get_p0() ,new Vector(-1, -1, 10)), camera.constructRayThroughPixel(4, 4, 1, 1, 10, 8, 8),
                "Bad ray");

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        assertEquals(new Ray(camera.get_p0(), new Vector(0, 0, 10)), camera.constructRayThroughPixel(3, 3, 1, 1, 10, 6, 6),
                "Bad ray");

        // TC12: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(camera.get_p0(), new Vector(0, -2, 10)), camera.constructRayThroughPixel(3, 3, 1, 0, 10, 6, 6),
                "Bad ray");

        // TC13: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(camera.get_p0(), new Vector(-2, 0, 10)), camera.constructRayThroughPixel(3, 3, 0, 1, 10, 6, 6),
                "Bad ray");
    }
}