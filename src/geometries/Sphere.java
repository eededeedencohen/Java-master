package geometries;

import primitives.*;

import java.util.List;
import java.util.concurrent.RecursiveTask;

import static primitives.Util.alignZero;

/**
 * sphere represent a kind of a ball with radius and centre point
 */
public class Sphere extends RadialGeometry {
    Point3D _center;

    /**
     * the main constructor inherit from the father constructor with 3 parameters
     *
     * @param _emmision the color of the sphere
     * @param _material the material of the sphere
     * @param _center   the center of the sphere
     * @param _radius   the radius of the sphere
     */
    public Sphere(Color _emmision, Material _material, Point3D _center, double _radius) {
        super(_emmision, _material, _radius);
        this._center = new Point3D(_center);

    }

    /**
     * constructor use  the main constructor with default material
     *
     * @param _emmision
     * @param _center
     * @param _radius
     */
    public Sphere(Color _emmision, Point3D _center, double _radius) {
        this(_emmision, new Material(0, 0, 0), _center, _radius);

    }

    /**
     * constructor without the color that determine the color to black and the material to the default
     *
     * @param _center
     * @param _radius
     */

    public Sphere(Point3D _center, double _radius) {
        super(_radius);
        this._center = new Point3D(_center);

    }

    /**********admin***************/

    @Override
    public String toString() {
        return super.toString() + " " + "_center=" + _center;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Sphere)) return false;
        Sphere other = (Sphere) o;
        return this._center.equals(other._center) && (super.equals(_radius));
    }

    /**********************getter*/////////////////
    public Point3D get_center() {
        return new Point3D(_center);
    }

    @Override
    public Vector getNormal(Point3D p) {
        Vector normal = new Vector(p.subtract(this._center));
        return normal.normalize();

    }

    /**
     * @param ray      the ray that intersect the sphere
     * @param distance given distance that beyond it-it doesn't matter if the ray intersect the geometry
     * @return
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        Point3D p0 = ray.getSource();
        Vector v = ray.getDirect();
        Vector u;

        try {
            u = _center.subtract(p0);   // p0 == _center
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(
                    this,
                    ray.getTargetPoint(this._radius)));
        }

        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(this._radius * this._radius - dSquared);

        if (thSquared <= 0) {
            return null;
        }

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) {
            return null;
        }

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

//        double t1dist = alignZero(maxDistance - t1);
//        double t2dist = alignZero(maxDistance - t2);

        if (alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0 && t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.getTargetPoint(t1)), new GeoPoint(this, ray.getTargetPoint(t2)));
        if (alignZero(t1 - maxDistance) <= 0 && t1 > 0)
            return List.of(new GeoPoint(this, ray.getTargetPoint(t1)));
        if (alignZero(t2 - maxDistance) <= 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.getTargetPoint(t2)));
        return null;
    }
}
