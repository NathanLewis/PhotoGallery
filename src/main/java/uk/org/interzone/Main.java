package uk.org.interzone;

//import org.imgscalr.Scalr;
//import org.imgscalr.Scalr.*;
//import java.awt.image.*;
//import java.io.*;
//import javax.imageio.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import static org.imgscalr.Scalr.resize;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        System.out.println( "Working directory: " + System.getProperty("user.dir") );
        String photodir = "./photos";
        File dir = new File(photodir);
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".JPG");
            }
        });

        ArrayList<File> thumbnails = new ArrayList<File>();
        for (File picfile : files) {
//            System.out.println(picfile.toString());
            try {
                thumbnails.add(res(picfile.getName(), photodir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GalleryUI galleryUI = new GalleryUI(thumbnails);
    }

    public static File res(String sourceFilename, String dirname) throws IOException {

        File sourceImageFile = new File(dirname + "/" + sourceFilename);
        String thumbdir = dirname + "/thumbnails/";
        File dir = new File(thumbdir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String pathname = thumbdir + sourceFilename;
        File outputFile = new File(pathname);
        if (outputFile.exists()) {
            return outputFile;
        }
        BufferedImage img = ImageIO.read(sourceImageFile);

        BufferedImage thumbnail = resize(img, GalleryUI.BWIDTH, GalleryUI.BHEIGHT);

        thumbnail.createGraphics().drawImage(thumbnail, 0, 0, null);
        ImageIO.write(thumbnail, "jpg", outputFile);
        System.out.println("wrote " + outputFile);
        return outputFile;
    }

}
