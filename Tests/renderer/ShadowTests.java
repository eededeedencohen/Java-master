package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.List;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void SphereTriangleInitial() throws Exception {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(List.of(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(0, 0, 200), 60), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4))));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle up-right
     */
    @Test
    public void SphereTriangleMove1() throws Exception {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(List.of(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(0, 0, 200), 60), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-25, 55, 0), new Point3D(-55, 25, 0), new Point3D(-53, 53, 4))));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleMove1", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle upper-righter
     */
    @Test
    public void SphereTriangleMove2() throws Exception {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(List.of(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(0, 0, 200), 60), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-15, 45, 0), new Point3D(-45, 15, 0), new Point3D(-43, 43, 4))));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleMove2", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move spot closer
     */
    @Test
    public void SphereTriangleSpot1() throws Exception {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(List.of(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(0, 0, 200), 60), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4))));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-85, 85, -120), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleSpot1", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move spot even more close
     */
    @Test
    public void SphereTriangleSpot2() throws Exception {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(List.of(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(0, 0, 200), 60), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4))));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-74, 74, -62), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleSpot2", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere producing a shading
     */
    @Test
    public void trianglesSphere() throws Exception {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(List.of(
                new Triangle(
                        Color.BLACK,
                        new Material(0, 0.8, 60), //
                        new Point3D(-150, 150, 115),
                        new Point3D(150, 150, 135),
                        new Point3D(75, -75, 150)), //
                new Triangle(
                        Color.BLACK,
                        new Material(0, 0.8, 60), //
                        new Point3D(-150, 150, 115),
                        new Point3D(-70, -70, 140),
                        new Point3D(75, -75, 150)), //
                new Sphere(
                        new Color(java.awt.Color.BLUE),
                        new Material(0.5, 0.5, 30), // )
                        new Point3D(0, 0, 115),
                        30)));


        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(40, -40, -115), new Vector(-1, 1, 4), 1, 4E-4, 2E-5));

        ImageWriter imageWriter = new ImageWriter("trianglesSphere", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * test with for mini project 1 super sampling
     * @throws Exception
     */
    @Test
    public void SphereSampling() throws Exception {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new Color(0,100,0));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(List.of(new Sphere(new Color(java.awt.Color.red), new Material(0.3, 0.5, 30, 0.5, 0), //
                        new Point3D(0, -30, 200), 60),
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(-40, -50, 300), 10),
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(0, -70, 300), 10),//
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(40, -50, 300), 10),//
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(30, -10, 300), 10),//
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(-30, -10, 300), 10),//
             new Triangle(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30, 0, 0.5), //
                      new Point3D(0, -20, 300), new Point3D(-5, -30, 300), new Point3D(5, -30, 300)),
               new Triangle(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30, 0, 0.5), //
                     new Point3D(0, -40, 300), new Point3D(-5, -30, 300), new Point3D(5, -30, 300)),
                new Plane(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0, 0.5), //
                        new Point3D(3, 3, 0), new Point3D(4, 3, 0), new Point3D(4, 3, 2))));

        scene.addLights(new PointLight(new Color(0, 255, 255), //
                new Point3D(0, -25, 100),  1, 1E-5, 1.5E-7),
                new SpotLight(new Color(0, 0, 255), //
                        new Point3D(100,-1000,80),new Vector(-2,30,1) , 1, 1E-5, 1.5E-7),
                new DirectionalLight(new Color(255, 255, 51), new Vector(500, 150, 100)));

        ImageWriter imageWriter = new ImageWriter("sphereSampling", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();


        render.renderImage();
        render.writeToImage();
    }

    /**
     * test for mini project 1 without super  sampling
     * @throws Exception
     */
    @Test
    public void SphereSampling2() throws Exception {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new Color(0,100,0));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(List.of(new Sphere(new Color(java.awt.Color.red), new Material(0.3, 0.5, 30, 0.5, 0), //
                        new Point3D(0, -30, 200), 60),
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(-40, -50, 300), 10),
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(0, -70, 300), 10),//
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(40, -50, 300), 10),//
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(30, -10, 300), 10),//
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30), //
                        new Point3D(-30, -10, 300), 10),//
                new Triangle(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30, 0, 0.5), //
                        new Point3D(0, -20, 300), new Point3D(-5, -30, 300), new Point3D(5, -30, 300)),
                new Triangle(new Color(java.awt.Color.yellow), new Material(0.5, 0.5, 30, 0, 0.5), //
                        new Point3D(0, -40, 300), new Point3D(-5, -30, 300), new Point3D(5, -30, 300)),
                new Plane(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0, 0.5), //
                        new Point3D(3, 3, 0), new Point3D(4, 3, 0), new Point3D(4, 3, 2))));

        scene.addLights(new PointLight(new Color(0, 255, 255), //
                        new Point3D(0, -25, 100),  1, 1E-5, 1.5E-7),
                new SpotLight(new Color(0, 0, 255), //
                        new Point3D(100,-1000,80),new Vector(-2,30,1) , 1, 1E-5, 1.5E-7),
                new DirectionalLight(new Color(255, 255, 51), new Vector(500, 150, 100)));

        ImageWriter imageWriter = new ImageWriter("sphereSampling2", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }



}
