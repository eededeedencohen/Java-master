package elements;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.Random;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * class represent a source light that distribute light all over the direction
 */
public class PointLight extends Light implements LightSource {
    protected Point3D _position;//position of the light
    protected double _kC, _kL, _kQ;//mekadem hanhata
    protected double DELTA = 0.01;



    /**
     * main constructor with 5 parameters
     *
     * @param _intensity the powerful of light in the source
     * @param _position  the position of the light
     * @param _kC        mekadem hanatha
     * @param _kL        mekadem hanatha
     * @param _kQ        mekadem hanatha
     */
    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intensity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    /**
     * @param p the point in the shape
     * @return IL -the powerful of the light in point P
     */

    @Override
    public Color getIntensity(Point3D p) {
        Color IL = this._intensity.scale(1 / (this._kC + this._kL * this.getDistance(p)
                + this._kQ * this.getDistance(p) * this.getDistance(p)));
        return IL;
    }

    /**
     * @param p point in the shape
     * @return vector from the source of the light to the point p
     */
    @Override
    public Vector getL(Point3D p) {
        return new Vector(p.subtract(_position)).normalized();
    }

    /**
     * @param point
     * @return
     */
    @Override
    public double getDistance(Point3D point) {
        return this._position.distance(point);
    }




}


