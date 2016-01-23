package uk.org.interzone;

import uk.co.jaimon.test.SimpleImageInfo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by nathan on 23/01/16.
 */
public class Image {
    protected File thumbnail;
    protected File original;

    public Image(File picfile, File thumbnail) {
        this.original = picfile;
        this.thumbnail = thumbnail;
    }

    protected void getDimensions() {
//        SimpleImageInfo simpleImageInfo = null;
//        try {
//            simpleImageInfo = new SimpleImageInfo(files.get(0).getThumbnail());
//            System.out.println("Width " + simpleImageInfo.getWidth() + " Height: " + simpleImageInfo.getHeight());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public File getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return thumbnail.toString();
    }

    BufferedImage rotateRight() throws IOException {
        ImageUtils.rotateRight(original);
        return ImageUtils.rotateRight(thumbnail);
    }

    BufferedImage rotateLeft() throws IOException {
        ImageUtils.rotateLeft(original);
        return ImageUtils.rotateLeft(thumbnail);
    }
}
