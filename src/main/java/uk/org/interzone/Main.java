package uk.org.interzone;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import static org.imgscalr.Scalr.resize;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        File dir = new File("../photos");
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".JPG");
            }
        });

        for (File picfile : files) {
            System.out.println(picfile);
            System.out.println(picfile.toString());
            //            try {
//                res(picfile.getName(),"../photos");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

    }

    public static void res(String sourceFilename, String dirname) throws IOException {

        File sourceImageFile = new File(sourceFilename);
        File outputFile = new File("thumbnails/" + sourceImageFile.getName());
        if (outputFile.exists()) {
            return;
        }
        BufferedImage img = ImageIO.read(sourceImageFile);

        BufferedImage thumbnail = resize(img, 500);

        thumbnail.createGraphics().drawImage(thumbnail, 0, 0, null);
        ImageIO.write(thumbnail, "jpg", outputFile);
    }

}
