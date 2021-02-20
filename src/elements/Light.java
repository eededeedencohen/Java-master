package elements;

import primitives.Color;

abstract public class Light {
    protected Color _intensity;
    protected double radius=0;
    /**
     * constructor with one parameter
     * @param _intensity the color we paint
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }



    /***********getter**************/
    public Color get_intensity() {
        return _intensity;
    }
    public double getRadius() {
        return radius;
    }
}
