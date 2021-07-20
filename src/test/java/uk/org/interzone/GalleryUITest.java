package uk.org.interzone;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class GalleryUITest {

    @Test
    public void test_getGridDimensions() {
        Point gridDimensions = GalleryUI.getGridDimensionsPoint(16);
        assertEquals(4, (int) gridDimensions.getX());
        assertEquals(4, (int) gridDimensions.getY());
    }

    @Test
    public void test_getGridDimensions2() {
        Point gridDimensions = GalleryUI.getGridDimensionsPoint(95);
        assertEquals(10, (int) gridDimensions.getX());
        assertEquals(10, (int) gridDimensions.getY());
    }
}