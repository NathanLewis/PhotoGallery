package uk.org.interzone;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.imgscalr.Scalr.resize;

/**
 * Created by nathan on 14/01/16.
 */
public class ImageUtils {
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
