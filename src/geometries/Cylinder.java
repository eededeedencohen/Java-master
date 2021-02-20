package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Cylinder extends Tube {
    private double _height;

    /**
     * @param _axisRay represent the axis of the ray
     * @param _radius
     * @param _radius
     * @throws Exception if the radius is equal to zero or approximate to it
     */
    public Cylinder(Ray _axisRay, double _radius, double _height) throws Exception {
        super(_axisRay, _radius);
        this._height = _height;
    }


    /***************admin*******************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Cylinder cylinder = (Cylinder) o;

        return super.equals((Tube) o) && isZero(this._height - this._height);
    }

    @Override
    public String toString() {
        return "Cylinder{" + super.toString() +
                "_height=" + _height +
                '}';
    }


    /***********getter***********/
    public double get_height() {
        return _height;
    }


}
