package geometries;

import primitives.*;


import java.util.List;

import static primitives.Util.isZero;

public class Triangle extends Polygon {

    /**
     * the main constructor with 5 parameters color ,material and 3 vertices
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same point
     *                                  <li>The vertices are not in the same plane</li>
     *                                  <li>The order of vertices is not according to edge path</li>
     *                                  <li>Three consequent vertices lay in the same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Triangle(Color _emmision , Material _material, Point3D vertices1, Point3D vertices2, Point3D vertices3) {

        super(_emmision,_material,vertices1,vertices2,vertices3);
    }

    /**
     * constructor use the main constructor with default material
     * @param _emmision
     * @param vertices1
     * @param vertices2
     * @param vertices3
     */
    public Triangle( Color _emmision,Point3D vertices1, Point3D vertices2, Point3D vertices3) {

       this(_emmision,new Material(0,0,0),vertices1,vertices2,vertices3);
    }
    /**
     * constructor  with default material and default color
     * @param vertices1
     * @param vertices2
     * @param vertices3
     */
    public Triangle( Point3D vertices1, Point3D vertices2, Point3D vertices3) {
        this(Color.BLACK,new Material(0,0,0),vertices1,vertices2,vertices3);
    }
    /**
     *
     * @param _point that represent the vector normal
     * @return vector normal
     */
    @Override
    public Vector getNormal(Point3D _point) {
        return super.getNormal(_point);
    }

    /**
     *
     * @param ray that intersects with the triangle
     * @param distance that if the intersection is beyond this distance-it doesn't matter what the intersection is
     * @return list of intersection points
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double distance) {

        //first of all the func checks if the ray intersect the plane and
        // then it checks whether the points are in the triangle or not
        List<GeoPoint> intersections = _plane.findIntersections(ray,distance);

        if (intersections == null) return null;//there are no intersection even with the plane

        Point3D p0 = ray.getSource();
        Vector v = ray.getDirect();

        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;

        if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) {
            //for GeoPoint
            for (GeoPoint geo : intersections) {
                geo.geometry= this;
            }
            return intersections;
        }

      return null;
    }

    /*************admin***************/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Triangle)) return false;

        Triangle tr = (Triangle) obj;

        return _vertices.get(0).equals(tr._vertices.get(0)) &&
                _vertices.get(1).equals(tr._vertices.get(1)) &&
                _vertices.get(2).equals(tr._vertices.get(2));
    }
    @Override
    public String toString() {
        String result = "";
        for (Point3D p : _vertices ) {
            result += p.toString();
        }
        return  result;
    }



}
