package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    /**
     * test for subtract
     */
    @Test
    void subtract() {
        Vector v1 = new Vector(0, 0, 1);
        Vector v2 = new Vector(0, 1, -2);
        Vector v3 = v1.subtract(v2);
        assertEquals(new Vector(0, -1, 3), v3);
        Vector v4 = v2.subtract(v1);
        assertEquals(new Vector(0, 1, -3), v4);
        Vector v5 = new Vector(5, 5, 1);
        Vector v6 = new Vector(v5);
        try {
            v6.subtract(v5);
            fail("vector zero is illegal");
        } catch (IllegalArgumentException ex) {
            //System.out.println(ex.getMessage());
            assertTrue(ex.getMessage() != null);
        }


    }

    /**
     * test for add
     */
    @Test
    void add() {
        Vector v1 = new Vector(0, 0, 1);
        Vector v2 = new Vector(0, 1, -2);
        v1 = v1.add(v2);
        assertEquals(new Vector(0, 1, -1), v1);
        v2 = v2.add(v1);
        assertEquals(new Vector(0, 2, -3), v2);

    }

    /**
     * test for scale
     */
    @Test
    void scale() {
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Vector(5, 10, 15), v1.scale(5));
        //System.out.println(v2.toString());
        try {
            v1.scale(0);
            fail("vector zero is illegal");
        } catch (IllegalArgumentException ex) {
            // System.out.println(ex.getMessage());
            assertTrue(ex.getMessage() != null);
        }


    }

    /**
     * test for dot product
     */
    @Test
    void dotProduct() {

        /***********test for positive dot product****/
        Vector v1 = new Vector(1, 3.4, 5);
        Vector v2 = new Vector(8, 2, 13.7);
        assertEquals(83.3, v2.dotProduct(v1), 1e-10);


        /***********test for negative dot product****/
        Vector v3 = new Vector(1, 3, 5);
        Vector v4 = new Vector(-1, -3, -5);
        assertEquals(-35, v3.dotProduct(v4), 1e-10);

        /***********test for zero dot product****/
        Vector v5 = new Vector(1, 6.5, -0.8);
        Vector v6 = new Vector(-1, 0, -1.25);
        assertEquals(0, v5.dotProduct(v6), 1e-10);

    }

    /**
     * test for cross product
     */
    @Test
    void crossProduct() {
        Vector v1 = new Vector(3.5, -5, 10);
        Vector v2 = new Vector(2.5, 7, 0.5);

        Vector v3 = v1.crossProduct(v2);
        assertEquals(0, v3.dotProduct(v2), 1e-10);
        assertEquals(0, v3.dotProduct(v1), 1e-10);
        Vector v4 = v2.crossProduct(v1);
//        System.out.println(v3.toString());
//        System.out.println(v4.toString());
        try {
            v3 = v3.add(v4);
            fail("the zero vector is illegal");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage() != null);
        }
        assertEquals(84.65, v3.length(), 1e-2);


    }

    /**
     * test for length
     */
    @Test
    void length() {
        Vector v1 = new Vector(2.7, 5, 89);
        assertEquals(89.18122, v1.length(), 1e-5);
        Vector v2 = new Vector(4, 5, 6.7);
        Vector v3 = v1.add(v2);
        assertEquals(96.45403, v3.length(), 1e-5);


    }

    /**
     * test for normalize
     */
    @Test
    void normalize() {
        Vector v = new Vector(3.5, -5, 10);
        v.normalize();
        assertEquals(1, v.length(), 1e-10);
        try {
            Vector v1 = new Vector(0, 0, 0);
            v1.normalize();
            fail("Didn't throw divide by zero exception!");
        } catch (IllegalArgumentException ex) {
            assertEquals("A vector can't have head point to be the ZERO point", ex.getMessage());
        }
        assertTrue(true);
    }



}