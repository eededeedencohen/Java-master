package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import elements.Camera;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;


import java.util.List;

import static primitives.Util.alignZero;

/**
 * class that receive a way to write an image and the scene herself and create to us render of the scene in the picture
 */
public class Render {
    private int _threads =1;
    private final int SPARE_THREADS = 2;
    private boolean _print = false;
    private final ImageWriter _imageWriter;
    private final Scene _scene;
    // private static final double DELTA = 0.01;//constant for movement of the ray
    private static final int MAX_CALC_COLOR_LEVEL = 50;//constant for how many levels we calculate the color until end
    private static final double MIN_CALC_COLOR_K = 0.00001;//constant for how much the mekadem that bottom it-we consider it to color black
    private static final double sampling =49;//how much rays will pass through Pixel except for the main ray


    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {//I finish one percent of the scan
                    ++_percents;//increase the value of percent
                    _nextCounter = _pixels * (_percents + 1) / 100;//increase the next Percentage
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }

    /**
     * constructor that have 2 parameters
     *
     * @param imageWriter way to write
     * @param scene       we want to render
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    /**
     * method to create ag grid in the picture
     *
     * @param interval where we create the grid
     * @param color    the color of the grid
     */
    public void printGrid(int interval, java.awt.Color color) {
        int Ny = _imageWriter.getNy();
        int Nx = _imageWriter.getNx();


        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * method we already do in another class
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * method that doing the render in fact
     */

    public void renderImage() throws Exception {
        final int Nx = _imageWriter.getNx();
        final int Ny = _imageWriter.getNy();
        java.awt.Color background = _scene.getBackground().getColor();
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        double distance = _scene.getDistance();
        Color temporary = new Color(0, 0, 0);
        //width and height are the number of pixels in the rows
        //and columns of the view plane
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        final Pixel thePixel = new Pixel(Ny, Nx);
        if (_threads == 1) {
            for (int row = 0; row < Ny; row++) {
                for (int column = 0; column < Nx; column++) {
                    Ray ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);//the RAY from the center of the pixel
                    List<Ray> rays = camera.constructRaysSamplingThroughPixel(Nx, Ny, column, row, distance, width, height, sampling);//the
                    rays.add(ray);
                    Color average = calc_average_color(rays, temporary);
                    _imageWriter.writePixel(column, row, average.getColor());
                }
            }
        }
        // Generate threads
        Thread[] threads = new Thread[_threads];

        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    Ray ray = camera.constructRayThroughPixel(Nx, Ny, pixel.col, pixel.row, distance, width, height);//the RAY from the center of the pixel
                    List<Ray> rays = camera.constructRaysSamplingThroughPixel(Nx, Ny, pixel.col, pixel.row, distance, width, height, sampling);
                    rays.add(ray);
                    Color average = null;
                    try {
                        average = calc_average_color(rays, temporary);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    _imageWriter.writePixel(pixel.col, pixel.row, average.getColor());

                }

            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
        if (_print) System.out.printf("\r100%%\n");

    }
    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }


    /**
     * function that calculate the average of the color from all rays
     *
     * @param rays      list of ray to pixel
     * @param temporary the temporary color we want to add to the calculate
     * @return the average color influenced by all the ray
     * @throws Exception
     */
    private Color calc_average_color(List<Ray> rays, Color temporary) throws Exception {
        for (int i = 0; i < rays.size(); i++) {
            GeoPoint closestPoint = findClosestIntersection(rays.get(i));
            if (closestPoint == null)//if we don't reflect neither of the geometries
                temporary = temporary.add(this._scene.getBackground().reduce(rays.size()));
            else {
                temporary = temporary.add(calcColor(closestPoint, rays.get(i)).reduce(rays.size()));
            }
        }
        return temporary;
    }


//


    /**
     * function that composite the main calcColor -but without the ambient light
     * we don't want tp involve ambient light in recursion
     *
     * @param geoPoint
     * @param inRay
     * @return
     * @throws Exception
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay) throws Exception {
        Color color = calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
        color = color.add(_scene.getAmbientLight().getIntensity());//in the end we add the influence of ambient
        return color;
    }

    /**
     * private method because this an help function for this class
     *
     * @param closestPoint the point we want to color
     * @return the color that paint this pixel-now it's just a constant color
     */
    private Color calcColor(GeoPoint closestPoint, Ray inRay, int level, double k) throws Exception {
        Color color = closestPoint.getGeometry().getEmission();
        if (level == 0 || k < MIN_CALC_COLOR_K) return Color.BLACK;
        Vector v = closestPoint.getPoint().subtract(_scene.getCamera().get_p0()).normalize();
        Vector n = closestPoint.getGeometry().getNormal(closestPoint.getPoint());
        Material material = closestPoint.getGeometry().get_material();
        double ks = material.get_Ks();
        double kd = material.get_Kd();
        int nShininess = material.get_nShininess();

        color = get_lights_color(closestPoint, color, v, n, ks, kd, nShininess, k);
        double kR = material.get_kR();
        double kkr = k * kR;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, closestPoint.getPoint(), inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay,
                        level - 1, kkr).scale(kR));
        }
        double kT = material.get_kT();
        double kkt = k * kT;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, closestPoint.getPoint(), inRay);
            GeoPoint reflectedPoint = findClosestIntersection(refractedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, refractedRay,
                        level - 1, kkt).scale(kT));
        }

        return color;


    }

    /**
     * help function for Calc color that calculate the intensity of source light
     * and the amount of shadow
     *
     * @param closestPoint the point we want to know what the color on it
     * @param color        the source color
     * @param v            vector from camera to the geometry
     * @param n            the normal of the shape int this closet point
     * @param ks           mekadem diffuse
     * @param kd           mekadem spectral
     * @param nShininess   number of shiness
     * @param k            represent the mekadem of the transparency
     * @return the color influenced by the lights
     */
    private Color get_lights_color(GeoPoint closestPoint, Color color, Vector v, Vector n, double ks, double kd, int nShininess, double k) {
        for (LightSource lightSource : _scene.get_lights()) {
            Vector l = lightSource.getL(closestPoint.getPoint());
            if (sign(n.dotProduct(l)) == sign(n.dotProduct(v))) {
                double ktr = transparency(lightSource, l, n, closestPoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color IL = lightSource.getIntensity(closestPoint.getPoint()).scale(ktr);//the powerful of the light in the point
                    color = color.add(calcDiffusive(kd, l, n, IL),
                            calcSpecular(ks, l, n, v, nShininess, IL));
                }
            }


        }
        return color;
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param v          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * @author Dan Zilberstein
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color ip) {
        double nl = n.dotProduct(l);
        int p = nShininess;
        Vector r = l.add(n.scale(-2 * nl)).normalized();
        double rv = r.dotProduct(v);
        double minus_vr = -alignZero(rv);
        if (minus_vr <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return ip.scale(ks * Math.pow(minus_vr, p));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component mekadem
     * @param IL light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein
     * <p>
     * The diffuse component is that dot product n•L that we discussed in class. It approximates light, originally
     * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * surface is paper. In general, you'll also want this to have a non-gray color value, so this term would in general
     * be a color defined as: [rd,gd,bd](n•L)
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color IL) {
        double nl = Math.abs(n.dotProduct(l));
        return IL.scale(nl * kd);
    }

    /**
     * help function
     *
     * @param val the value we want to compare
     * @return the sign of the val
     */
    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * private method because this an help function for this class
     * calculate the closet point
     *
     * @param intersection_points list that we bring from the intersections points
     * @return the point that closet more than another points in the list to the camera
     */

    private GeoPoint getClosestPoint(List<GeoPoint> intersection_points) {
        double min = Double.MAX_VALUE;//we want to decrease this value until we reach to the minimum point
        GeoPoint closet_point = null;
        for (int i = 0; i < intersection_points.size(); i++) {
            double distance = intersection_points.get(i).getPoint().distance(_scene.getCamera().get_p0());
            if (distance < min) {//the mininum distance from the begin of the ray
                min = distance;
                closet_point = intersection_points.get(i);
            }
        }
        return closet_point;

    }

    /**
     * inner function that help renderImage function to sure whether this point will be dark or not
     *
     * @param light the source light himself
     * @param l     the vector from the light source to the point of the geometry
     * @param n     normal of the geometry
     * @param gp    the point we want to reflect
     * @return true-if this point not be shadowing ,false-this point will be shadowed
     */

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1);//turn the vector
        // to know if the ray of this vector pass through another geometry

        Ray lightRay = new Ray(gp.getPoint(), lightDirection, n);//we create new ray to the opposite direct of vector l
        List<GeoPoint> intersections = _scene.getGeometries()
                .findIntersections(lightRay, light.getDistance(gp.getPoint()));
        return intersections == null;


    }

    /**
     * we find the closet intersection of the ray with any of the geometry
     * the first geometry in the scene we meet from the source point of the ray
     *
     * @param ray we want to find the intersect of it with geometry
     * @return closet Geopoint intersection with the ray
     */

    private GeoPoint findClosestIntersection(Ray ray) {
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.getSource();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.getPoint());
            if (distance < closestDistance) {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }

    /**
     * func produce a new ray that reflect the geometry
     *
     * @param normal the normal of the geometry
     * @param point  point of the geometry
     * @param inRay  ray intersect this geopoint
     * @return reflection ray
     */
    private Ray constructReflectedRay(Vector normal, Point3D point, Ray inRay) {
        double nv = normal.dotProduct(inRay.getDirect());
        if (nv != 0) {
            Vector r = inRay.getDirect().add(normal.scale(-2 * nv));
            return new Ray(point, r, normal);
        }

        return null;
    }

    /**
     * func produce a new ray that refract the geometry
     *
     * @param point the point of the geometry
     * @param inRay the ray intersect the geometry
     * @return a new ray represent refracted ray
     */
    private Ray constructRefractedRay(Vector normal, Point3D point, Ray inRay) {
        return new Ray(point, inRay.getDirect(), normal);
    }

    /**
     * the function calculate the value of the shadow because now the shapes are transparency
     * and can pass through them a light
     *
     * @param light    the source light
     * @param l        vector from the source light to  point
     * @param n        normal
     * @param geopoint the point of the geometry
     * @return amount of how many shadow will be according to the geometry
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay, light.getDistance(geopoint.getPoint()));
        if (intersections == null) return 1d;//without any shadow
        // double lightDistance = light.getDistance(geopoint.getPoint());
        double ktr = 1d;
        for (GeoPoint gp : intersections) {
            ktr *= gp.getGeometry().get_material().get_kT();
            if (ktr < MIN_CALC_COLOR_K) return 0.0;
        }
        return ktr;
    }
}
