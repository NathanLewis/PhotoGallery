package uk.org.interzone;

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

        ArrayList<Image> images = new ArrayList<>();
        if (null != files) {
            for (File picfile : files) {
                try {
                    images.add(new Image(picfile, photodir));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        GalleryUI galleryUI = new GalleryUI(images);
    }

}
