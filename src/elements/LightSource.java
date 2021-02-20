package elements;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * interface represent the characteristics of the light -the distance from the source and the intensity of the light
 * and the Vector from point to the source
 */
public interface LightSource {
    Color getIntensity(Point3D p);
    Vector getL(Point3D p);
    double getDistance(Point3D point);


}
