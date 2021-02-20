package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Tube extends RadialGeometry {
    protected final Ray _axisRay;

    /**
     *
     * @param _axisRay represent the axis of the ray
     * @param _radius
     * @throws Exception if the radius is equal to zero or approximate to it
     */
    public Tube(Ray _axisRay, double _radius) throws Exception {
        super(_radius);
        this._axisRay = new Ray(_axisRay);
    }
/**************getter*************/
    public Ray get_axisRay() {
        return new Ray(this._axisRay);
    }




/*********admin****************/
    @Override
    public String toString() {
        return "ray: " +this._axisRay +
                ", radius: " + _radius;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Tube))
            return false;
        if (this == obj)
            return true;
        Tube other = (Tube) obj;

        //the two vectors needs to be in the same direction,
        //but not necessary to have the same length.
        try {
            Vector v = this._axisRay.getDirect().crossProduct(other._axisRay.getDirect());
        } catch (IllegalArgumentException ex) {
            return (isZero(this._radius - other._radius) && this._axisRay.getSource().equals((other._axisRay.getSource())));
        }
        throw new IllegalArgumentException("direction cross product with parameter.direction == Vector(0,0,0)");
    }

    /**
     *
     * @param p that represent the point from it we build normal vector to the sphere -it's necessity!
     * @return normal vector to the sphere from the point p
     */
    @Override
    public Vector getNormal(Point3D p) {

        Point3D o = this._axisRay.getSource();
        Vector v = this._axisRay.getDirect();

        Vector vector1 = p.subtract(o);

        //check if the vector1 is orthogonal to the direct of the tube
        double projection = vector1.dotProduct(v);
        if(!isZero(projection))//it's not orthogonal
        {
            // projection of P-O on the ray:
            o.add(v.scale(projection));//fix the direct of the ray that will be orthogonal
        }

        //This vector is orthogonal to the _direction vector.
        Vector check = p.subtract(o);
        return check.normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray lightRay, double distance) {
        return null;
    }
}
