package uk.org.interzone;

import java.io.File;

/**
 * Created by nathan on 23/01/16.
 */
public class Image {
    protected File file;

    public Image(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
