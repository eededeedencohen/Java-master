package geometries;

import primitives.*;

import java.util.List;

/**
 * a new class that have a color represent of the geometry- emission color
 */
abstract public class Geometry implements Intersectable {
    protected Color _emission;
    protected Material _material;


    public abstract Vector getNormal(Point3D p) throws Exception;

    /**
     * default constructor that determine the color to black and the material to the default
     */
    public Geometry() {
        this(Color.BLACK, new Material(0, 0, 0));
    }

    /**
     * constructor with one parameter
     *
     * @param _emission represent a color of this geometry/shape and material to default
     */
    public Geometry(Color _emission) {
        this(_emission, new Material(0, 0, 0));
    }

    /**
     * constructor with 2 parameterrs
     * @param _emission the color of the shape
     * @param _material the material of the shape
     */
    public Geometry(Color _emission, Material _material) {
        this._emission = _emission;
        this._material = _material;
    }

    /*********getters*****************/
    public Color getEmission() {
        return _emission;
    }

    public Material get_material() { return _material; }

}