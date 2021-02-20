package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() throws Exception {
        Tube t1=new Tube(new Ray(new Point3D(1,0,0),new Vector(1,2,3)),1d);
        Vector v1=t1.getNormal(new Point3D(2,0,1));
        System.out.println(v1);
        assertEquals(new Vector(0.707106781186547,0.0,0.7071067811865475),v1);
        Vector v2=t1.getNormal(new Point3D(1,-3,2));
        System.out.println(v2);
        assertEquals(new Vector(0.0,-0.8320502943378437,0.5547001962252291),v2);

    }
}