package elements;

import primitives.Color;

/**
 * class represent the environment of the light-inherit from the abstract class Light have color
 */
public class AmbientLight extends Light {

    /**
     * constructor with 2 parameters
     *
     * @param Ia the source of the light
     * @param Ka mekadem of the light
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));

    }


    /************getter****************/
    public Color getIntensity() {
        return _intensity;
    }
}
