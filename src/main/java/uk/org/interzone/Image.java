package uk.org.interzone;

import java.io.File;

/**
 * Created by nathan on 23/01/16.
 */
public class Image {
    protected File thumbnail;

    public Image(File thumbnail) {
        this.thumbnail = thumbnail;
    }

    public File getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return thumbnail.toString();
    }
}
