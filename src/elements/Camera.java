package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * class represent ray generator
 *
 * @author zafrir
 */

public class Camera {
    private static final double DELTA = 0.01;
    private Point3D _p0;
    private Vector _vTo;
    private Vector _vUp;
    private Vector _vRight;
    //  private static final double sampling =0;//how much rays will pass through Pixel
    private int sampling_shadow_rays = 50;

    /**
     * @param _p0  the point of the camera in the space
     * @param _vTo vector in the direct of the forward of the camera
     * @param _vUp vector in the direct of the top of the camera
     */

    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {
        this._p0 = new Point3D(_p0);
        if (!isZero(_vTo.dotProduct(_vUp))) throw new IllegalArgumentException("the vectors must be orthogonal");
        this._vTo = _vTo.normalized();
        this._vUp = _vUp.normalized();
        this._vRight = this._vTo.crossProduct(this._vUp);
    }

    /*************getters*******************/

    public Vector get_vUp() {
        return new Vector(_vUp);
    }

    public Vector get_vRight() {
        return new Vector(_vRight);
    }

    public Vector get_vTo() {
        return new Vector(_vTo);
    }

    public Point3D get_p0() {
        return new Point3D(_p0);
    }

    /**
     * func that calculate the ray through the view plane
     *
     * @param nX             number of pixels for the width
     * @param nY             number of pixels for the height
     * @param j              the index of y
     * @param i              the index of x
     * @param screenDistance the distance between the camera and the view plane
     * @param screenWidth    the width of the view plane
     * @param screenHeight   the height of the view plane
     * @return the ray which pass through the pixel (i,j) of the view plane
     */

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {
        {
            if (isZero(screenDistance)) {
                throw new IllegalArgumentException("distance cannot be 0");
            }

            Point3D Pc = _p0.add(_vTo.scale(screenDistance));

            double Ry = screenHeight / nY;
            double Rx = screenWidth / nX;

            double yi = ((i - nY / 2d) * Ry + Ry / 2d);
            double xj = ((j - nX / 2d) * Rx + Rx / 2d);//calculate the offset of the point in the view plane

            Point3D Pij = Pc;


            if (!isZero(xj)) {//addition the offset we have already calculate
                Pij = Pij.add(_vRight.scale(xj));//rise in the xi
            }
            if (!isZero(yi)) {
                Pij = Pij.subtract(_vUp.scale(yi));//rise in the opposite of yi because yi and VUp is in opposite direct
            }

            Vector Vij = Pij.subtract(_p0);

            return new Ray(_p0, Vij);

        }
    }

    public List<Ray> constructRaysSamplingThroughPixel(int nX, int nY, int j, int i, double distance, double width, double height, double sampling) {
        Point3D Pc = _p0.add(_vTo.scale(distance));
        double series = Math.sqrt(sampling);
        List<Ray> rays = new LinkedList<>();
        double Ry = height / nY;
        double Rx = width / nX;
        for (int count1 = 1; count1 <= series; count1++) {
            //int count2 = 1;//num of column
            for (int count2 = 1; count2 <= series; count2++) {//move faster than the external for
                double yi = ((i - nY / 2d) * Ry + Ry * (count2 / series));
                double xj = ((j - nX / 2d) * Rx + Rx * (count1 /series));//a new offset


                Point3D Pij = Pc;
                if (!isZero(xj)) {//addition the offset we have already calculate
                    Pij = Pij.add(_vRight.scale(xj));//rise in the xi
                }
                if (!isZero(yi)) {
                    Pij = Pij.subtract(_vUp.scale(yi));//rise in the opposite of yi because yi and VUp is in opposite direct
                }
                Vector Vij = Pij.subtract(_p0);
                rays.add(new Ray(_p0, Vij));

            }

        }
        return rays;
    }
}
