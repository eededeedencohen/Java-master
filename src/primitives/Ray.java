package primitives;

/**
 * class ray represent a vector that doesn't start from the centre of the axis
 */


public class Ray {
    private static final double DELTA = 0.01;
    Point3D source;
    Vector direct;

    /**
     * constructor with 2 parameters
     *
     * @param source represent the point from the ray start
     * @param direct represent the destination of the ray
     */
    public Ray(Point3D source, Vector direct) {
        this.source = new Point3D(source);
        this.direct = new Vector(direct).normalized();
    }

    /**
     * copy constructor
     *
     * @param axisRay
     */
    public Ray(Ray axisRay) {
        this.source = axisRay.source;
        this.direct = axisRay.direct.normalized();

    }

    /**
     * constructor for the offset of the ray -DELTA
     *
     * @param point
     * @param direct
     * @param n
     */
    public Ray(Point3D point, Vector direct, Vector normal) {
        this.direct = new Vector(direct).normalized();
        double nv = normal.dotProduct(direct);
        Vector new_offset = normal.scale((nv > 0 ? DELTA : -DELTA));
        this.source = point.add(new_offset);
    }

    /***************getters***************/

    public Vector getDirect() {
        return new Vector(direct);
    }

    public Point3D getSource() {
        return new Point3D(source);
    }


    /*************** Admin *****************/

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Ray))
            return false;
        Ray ray = (Ray) obj;
        return this.direct.equals(ray.direct) && this.source.equals(ray.source);
    }

    @Override
    public String toString() {
        return "the direct of the ray =" + direct.toString() + " " + "and the ray start from the point=" + source.toString();
    }

    /**
     * refactoring!!
     *
     * @param t represent a scalar
     * @return new point that the direct of the ray multiply by the scalar t and sum with the source of the ray
     */
    public Point3D getTargetPoint(double t) {
        return this.source.add(this.direct.scale(t));
    }

}
