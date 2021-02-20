package primitives;

/**
 * class that represent the kind of the shape-what the material he is made
 */
public class Material {
    private double _kD;
    private double _kS;
    private int _nShininess;
    private double _kT;//mekadem transparency -how much light pass through shape
    private double _kR;//mekadem reflection -how much mirror


    /**
     * main constructor with all the parameters we need
     *
     * @param _kD         mekadem diffuce- the powreful of how much light will spread from the shape
     * @param _kS         mekadem spectral-the powreful of the the flash
     * @param _nShininess how many shine do you want to see
     * @param _kT         mekadem of transparency
     * @param _kR         mekadem of  reflection
     */
    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
        this._kT = _kT;
        this._kR = _kR;
    }

    /**
     * constructor with three parameters
     *
     * @param _kD         mekadem diffuce- the powreful of how much light we can see that reach to camera
     * @param _kS         mekadem spectral-the powreful of how much light we can see as a mirror-
     * @param _nShininess how many shine do you want to see
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this(_kD, _kS, _nShininess, 0, 0);
    }

    /***************getters*************/

    public double get_Kd() {
        return _kD;
    }

    public double get_Ks() {
        return _kS;
    }

    public int get_nShininess() {
        return _nShininess;
    }

    public double get_kT() { return _kT; }

    public double get_kR() { return _kR; }
}
