package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.Light;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Scene {
    private String _name;
    private Color background;
    private AmbientLight _ambientLight;
    private Camera _camera;
    private double _distance;
    private Geometries _geometries = null;
    private List<LightSource> _lights ;//a new field to initialized several source light


    /**
     * constructor that build a new empty geometries
     *
     * @param name represent the name of the scene
     */
    public Scene(String name) {
        this._name = name;
        _geometries = null;
    }

    /******************getters********/

    public String getName() {
        return _name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public Camera getCamera() {
        return _camera;
    }

    public double getDistance() {
        return _distance;
    }

    public Geometries getGeometries() {
        return _geometries;
    }


    public List<LightSource> get_lights() {
        if (this._lights!=null) return this._lights;
        return _lights=new LinkedList<>();
    }

    /**********setters*********************/

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    /**
     * method that we add a couple of shapes to the our geometries
     *
     * @param geo a list of shapes
     */
    public void addGeometries(List<Intersectable> geo) {
        if (_geometries == null)//build a new
            _geometries = new Geometries(geo);
        else
            _geometries.add(geo);
    }

    public void addLights(LightSource... lights) {
        if (this._lights==null)
            this._lights=new LinkedList<>();
         for ( LightSource lightSource : lights)
             this._lights.add(lightSource);
     }




}
