package primitives;

/**
 * Point3D class represent a point in 3D space
 *
 * @author zafrir
 *
 */
public class Point3D {
    public static final Point3D ZERO = new Point3D(0, 0, 0);
    Coordinate x,y,z;


    /************* constructors *************/
    /**
     * Constructor to build Point with 3 Coordinates
     *
     * @param x represent a coordinate in x axis
     * @param y represent a coordinate in y axis
     * @param z represent a coordinate in z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructor to build Point with 3 doubles
     *
     * @param x represent a value in x axis
     * @param y represent a coordinate in y axis
     * @param z represent a coordinate in z axis
     */
    public Point3D(double x, double y, double z) {
        this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    public Point3D(Point3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    /*********************** getters ***************/

    public Coordinate get_x() {
        return new Coordinate(x);
    }

    public Coordinate get_y() {
        return new Coordinate(y);
    }

    public Coordinate get_z() {
        return new Coordinate(z);
    }

    /**
     *
     * @param p the point that subtract from another point :point-point
     * @return vector that is the difference between 2 points
     */
    public Vector subtract(Point3D p) {
        return new Vector(this.x._coord - p.x._coord, this.y._coord - p.y._coord, this.z._coord - p.z._coord);
    }
    /**
     *
     * @param v the vector that subtract from another point:point-vector
     * @return vector that is the difference between point and vector
     */

    public Point3D subtract(Vector v) {
        return new Point3D(this.x._coord - v._head.x._coord,
                this.y._coord - v._head.y._coord,
                this.z._coord - v._head.z._coord);
    }

    /**
     *
     * @param v adding from another point: point+vector
     * @return sum of point and vector that it's vector
     */
    public Point3D add(Vector v) {
        return new Point3D(this.x._coord + v._head.x._coord, this.y._coord + v._head.y._coord,
                this.z._coord + v._head.z._coord);
    }

    /**
     *
     * @param p represent a point that we calculate the distance between it and another point
     * @return the distance between 2 points multiply by itself
     */
    public double distanceSquared(Point3D p) {
        return (double) ((this.x._coord - p.x._coord) * (this.x._coord - p.x._coord)
                + (this.y._coord - p.y._coord) * (this.y._coord - p.y._coord)
                + (this.z._coord - p.z._coord) * (this.z._coord - p.z._coord));
    }
    /**
     *
     * @param p represent a point that we calculate the distance between it and another point
     * @return the real distance between 2 points
     */
    public double distance(Point3D p) {
        return Math.sqrt(distanceSquared(p));

    }


    /*************** Admin *****************/

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Point3D))
            return false;
        Point3D p = (Point3D) obj;
        return this.x.equals(p.x) && this.y.equals(p.y) && this.z.equals(p.z);
    }

    @Override
    public String toString() {
        return ("("+x.toString() + "," + y.toString() + "," + z.toString()+")");

    }

}
