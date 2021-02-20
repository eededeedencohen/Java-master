package elements;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class that represent a light that reach in one direction rather than the point light
 */
public class SpotLight extends PointLight {
    private Vector _direction;


    /**
     * main constructor inherit from point light
     * @param _intensity the color of the source of the light
     * @param _position the position of the source of the light
     * @param _kC mekadem hanhata
     * @param _kL  mekadem hanhata
     * @param _kQ  mekadem hanhata
     * @param _direction the direction of the ray from the source of light-only to one side!!
     */

    public SpotLight(Color _intensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        this._direction = new Vector(_direction).normalized();
    }


    /**
     * @param p the point in the shape
     * @return IL -the powerful of the light in point P
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0d, this._direction.dotProduct(this.getL(p))));
    }

}
