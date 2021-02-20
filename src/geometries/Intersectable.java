package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * we add a new class in this class because of the function find intersection
 */
public interface Intersectable {
    default List<GeoPoint> findIntersections(Ray ray) { //we keep what we did with the calculate of the intersection
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }

    List<GeoPoint> findIntersections(Ray lightRay, double distance);//the new function

    /**
     * inner class that used to know this point of intersect what the shape it intersect(to relate point to the shape)
     * we don't know it without this class -thus we do it
     */
    static class GeoPoint {
        protected Geometry geometry;
        protected Point3D point;

        /**
         * constructor with 2 parameters
         *
         * @param geometry to define a shape with color
         * @param point    the intersection point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**************getters************/

        public Geometry getGeometry() {
            return geometry;
        }

        public Point3D getPoint() {
            return point;
        }

        /***admin***/
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint other = (GeoPoint) o;
            return this.geometry.equals(other.geometry) && this.point.equals(other.point);
        }
    }

}
