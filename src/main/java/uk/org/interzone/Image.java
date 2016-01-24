package uk.org.interzone;

import uk.co.jaimon.test.SimpleImageInfo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by nathan on 23/01/16.
 */
public class Image {
    protected Orientation orientation;
    protected File thumbnail;
    protected File original;
    protected int orig_height, orig_width, thumb_height, thumb_width;
    private static Executor executor = Executors.newSingleThreadExecutor();

    public Image(File picfile, String photodir) throws IOException {
        this.original = picfile;
        getDimensions(original);
        if( orig_width >= orig_height ) {
            this.orientation = Orientation.Landscape;
            this.thumbnail = ImageUtils.res(picfile.getName(), photodir, GalleryUI.BWIDTH, GalleryUI.BHEIGHT);
        } else {
            this.orientation = Orientation.Portrait;
            System.out.println(original.getName() + " is in Portrait");
            this.thumbnail = ImageUtils.res(picfile.getName(), photodir, GalleryUI.BHEIGHT, GalleryUI.BWIDTH);
        }
    }

    protected void getDimensions(File file) {
        try {
            SimpleImageInfo simpleImageInfo = new SimpleImageInfo(file);
            orig_height = simpleImageInfo.getHeight();
            orig_width = simpleImageInfo.getWidth();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return thumbnail.toString();
    }

    BufferedImage rotateRight() throws IOException {
        toggleOrientation();
        BufferedImage bufferedImage = ImageUtils.rotateRight(thumbnail);
        Runnable r = new Runnable() {
            public void run() {
                try {
                    ImageUtils.rotateRight(original);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        executor.execute(r);
        return bufferedImage;
    }

    BufferedImage rotateLeft() throws IOException {
        toggleOrientation();
        BufferedImage bufferedImage = ImageUtils.rotateLeft(thumbnail);
        Runnable r = new Runnable() {
            public void run() {
                try {
                    ImageUtils.rotateLeft(original);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        executor.execute(r);
        return bufferedImage;
    }

    protected void toggleOrientation() {
        if (Orientation.Landscape == this.orientation) {
            this.orientation = Orientation.Portrait;
        } else {
            this.orientation = Orientation.Landscape;
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
