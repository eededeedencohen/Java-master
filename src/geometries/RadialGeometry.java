package geometries;

import primitives.*;
import primitives.Util.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public abstract class RadialGeometry extends Geometry {
    double _radius;

    /**
     * the main constructor have 3 parameters -color material and radius
     * @param _emmision
     * @param _material
     * @param _radius
     */
    public RadialGeometry(Color _emmision, Material _material, double _radius)  {
        super(_emmision,_material);
        if ( isZero(_radius) || (_radius < 0.0))
            throw new IllegalArgumentException("radius "+ _radius +" is not valid");
        this._radius = alignZero(_radius);
    }

    /**
     * constructor with parameter radius and parameter color with default material
     * @param _radius represent the circle of the radial
     * @throws Exception if the radius is close to zero or negative
     */
    public RadialGeometry(Color _emmision,double _radius)  {
        this(_emmision,new Material(0,0,0),_radius);
    }
    /**
     * default constructor that determine the color of the geometry to black and default material
     * @param _radius
     */
    public RadialGeometry(double _radius) {
       this(Color.BLACK,_radius);
    }
    /**
     * copy constructor
     * @param radialGeometry
     */
    public RadialGeometry(RadialGeometry radialGeometry) {
        this._radius=radialGeometry._radius;
    }


    @Override
    public String toString() {
        return "_radius=" + _radius ;
    }

    /****************************getter*******/
    public double get_radius() {
        return _radius;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RadialGeometry that = (RadialGeometry) o;

        return isZero(this._radius -that._radius);
    }



}
