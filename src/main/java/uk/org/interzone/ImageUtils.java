package uk.org.interzone;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.imgscalr.Scalr.resize;

/**
 * Created by nathan on 14/01/16.
 */
public class ImageUtils {

    public static File res(String sourceFilename, String dirname, int targetWidth, int targetHeight) throws IOException {

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

        BufferedImage thumbnail = resize(img, targetWidth, targetHeight);

        thumbnail.createGraphics().drawImage(thumbnail, 0, 0, null);
        ImageIO.write(thumbnail, "jpg", outputFile);
        System.out.println("wrote " + outputFile);
        return outputFile;
    }

    public static BufferedImage rotateRight(File imageFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        BufferedImage rotated = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_90);
        rotated.createGraphics().drawImage(rotated, 0, 0, null);
        ImageIO.write(rotated, "jpg", imageFile);
        return rotated;
    }

    public static BufferedImage rotateLeft(File imageFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        BufferedImage rotated = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_270);
        rotated.createGraphics().drawImage(rotated, 0, 0, null);
        ImageIO.write(rotated, "jpg", imageFile);
        return rotated;
    }

}
