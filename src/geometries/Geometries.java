package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * class that
 */

public class Geometries implements Intersectable {
    List<Intersectable> _geometries =new ArrayList<>();

    /**
     * default constructor
     */
    public Geometries() {
        this._geometries = new ArrayList<>();
    }

    /**
     * constructor with one parameter
     *
     * @param _geometries intersectable elements
     */
    public Geometries(List<Intersectable>_geometries) {
        add(_geometries);
    }

    /**
     * add a new geometry to the list
     */
    public void add(List<Intersectable>_geometries) {
        this._geometries.addAll(_geometries);
    }

    /**
     * func that pass all over the geometries and check their intersection
     * @param ray
     * @param distance
     * @return
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double distance) {
        List<GeoPoint> intersections = null;

        for (Intersectable geo : _geometries) {
            List<GeoPoint> tempIntersections = geo.findIntersections(ray,distance);
            if (tempIntersections != null) {
                if (intersections == null)//in the first case it will be null
                    intersections = new ArrayList<>();
                intersections.addAll(tempIntersections);
            }

        }
        return intersections;
    }
}



