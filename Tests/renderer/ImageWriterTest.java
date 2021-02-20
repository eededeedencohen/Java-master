package renderer;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
public class ImageWriterTest {
    /**
     * test that check if we can create an image to the writer
     */
    @Test
    void writeToImage() {
        ImageWriter imageWriter = new ImageWriter("first_picture123", 1600, 1000, 800, 500);
        int Ny = imageWriter.getNy();
        int Nx = imageWriter.getNx();


        for (int i =0 ; i < Ny; i++) {
            for (int j =0 ; j < Nx; j++) {
                if(i%50==0||j%50==0)//adding a grid
                 imageWriter.writePixel(j, i, Color.yellow);
                else
                {
                    imageWriter.writePixel(j, i, Color.green);
                }
            }
        }
        imageWriter.writeToImage();
    }
}
