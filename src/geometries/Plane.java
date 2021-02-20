package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry {

    Point3D P1;
    Vector normal;

    /**
     * the main constructor with all the parameters represent a plane
     * @param _emissionLight the color of the plane
     * @param _material the material of the plane
     * @param vertices1 -relation point
     * @param vertices2
     * @param vertices3
     */
    public Plane(Color _emissionLight, Material _material, Point3D vertices1, Point3D vertices2, Point3D vertices3) {
        super(_emissionLight,_material);
        this.P1 = new Point3D(vertices1);//relation point of the plane
        Vector temp1 = vertices2.subtract(vertices1);
        Vector temp2 = vertices3.subtract(vertices1);
        this.normal = temp1.crossProduct(temp2).normalized();
    }

    /**
     * constructor use in the main constructor with default material
     * @param _emissionLight
     * @param vertices1
     * @param vertices2
     * @param vertices3
     */
    public Plane(Color _emissionLight, Point3D vertices1, Point3D vertices2, Point3D vertices3) {
      this(_emissionLight,new Material(0,0,0),vertices1,vertices2,vertices3);
    }

    /**
     * constructor use in the main constructor with default material and default color
     * @param vertices1
     * @param vertices2
     * @param vertices3
     */
    public Plane( Point3D vertices1, Point3D vertices2, Point3D vertices3) {
        this(Color.BLACK, vertices1, vertices2,  vertices3);

    }


    /**
     * constructor with 2 parameters point and vector normal
     *
     * @param p1      relation point
     * @param normal1
     */
    public Plane(Point3D p1, Vector normal1) {
        this.P1 = p1;
        this.normal = normal1.normalized();
    }

/*****************getter*******************/
    public Vector getNormal() {
        return new Vector(normal.normalized());

    }

    /**
     * @param _point that represent the end of the normal vector
     * @return normal to the plane
     */

    @Override
    public Vector getNormal(Point3D _point) {
        return this.getNormal();
    }

    /**
     * function that check the intersection of the plane with ray
     * @param ray we want to check intersection with the plane
     * @param distance the distance from
     * @return
     */

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double distance) {
        Vector p0Q;
        try {
            p0Q = P1.subtract(ray.getSource());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }

        double nv = normal.dotProduct(ray.getDirect());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;

        double t = alignZero(normal.dotProduct(p0Q) / nv);

        if (alignZero(distance - t) < 0) {//the distance between the source of
            // the ray and the intersect is bigger than a given distance
            return null;
        }


        return t <= 0 ? null : List.of(new GeoPoint(this,ray.getTargetPoint(t)));
    }

}
