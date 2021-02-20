package elements;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class represent a light of the sun for example -by a direction
 */
public class DirectionalLight extends Light implements LightSource {
 private Vector _direction;

 /**
  * constructor with 2 parameters
  * @param _intensity the intensity of the light
  * @param _direction the direction of the light-like sun
  */
 public DirectionalLight(Color _intensity,Vector _direction) {
  super(_intensity);
  this._direction=new Vector(_direction).normalized();
 }

 @Override
 public Color getIntensity(Point3D p) {
  return _intensity;//it's the same powerful of the light all over the space
 }

 @Override
 public Vector getL(Point3D p) {
  return this._direction;
 }

 @Override
 public double getDistance(Point3D point) {
  return Double.MAX_VALUE;
 }




}
