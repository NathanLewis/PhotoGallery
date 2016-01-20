package uk.org.interzone;

import uk.co.jaimon.test.SimpleImageInfo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by nathan on 10/01/16.
 */
public class NButton extends JButton {
    protected final String imageFilename;
    protected final Set<NButton> selectedButtons;
    private final int diff;
    protected boolean bSelected = false;
    protected int index;
    protected int X, Y, width, height;
    protected Border emptyBorder = BorderFactory.createEmptyBorder();
    protected Border selectedBorder = new LineBorder(Color.YELLOW, 2);
    protected Orientation orientation = Orientation.Landscape;
    protected KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            char keyChar = e.getKeyChar();
            System.out.println("typed: " + keyChar);
            if ('r' == keyChar || 'R' == keyChar) {
                try {
                    for (NButton selected : selectedButtons) {
                        selected.rotateRight();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if ('l' == keyChar || 'L' == keyChar) {
                try {
                    for (NButton selected : selectedButtons) {
                        selected.rotateLeft();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if ('h' == keyChar || 'H' == keyChar) {
                for (NButton selected : selectedButtons) {
                    selected.moveLeft();
                }
            } else if ('j' == keyChar || 'J' == keyChar) {
                for (NButton selected : selectedButtons) {
                    selected.moveRight();
                }
            }

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    public NButton(String text, String imageFilename, int ind, Set<NButton> selectedButtons, int width, int height) {
        super(text, new ImageIcon(imageFilename));
        this.imageFilename = imageFilename;
        this.index = ind;
        this.selectedButtons = selectedButtons;
        this.width = width;
        this.height = height;
        if (width > height) {
            this.diff = (width - height) / 2;
        } else {
            this.diff = (height - width) / 2;
        }
    }

    void rotateLeft() throws IOException {
        System.out.println("Rotating " + imageFilename + ", index " + index + " left");
        BufferedImage rotated = ImageUtils.rotateLeft(imageFilename);
        setNewBounds();
        this.setIcon(new ImageIcon(rotated));
    }

    protected void setNewBounds() {
        if (Orientation.Landscape == this.orientation) {
            this.setBounds(X + diff, Y, height, width);
            this.orientation = Orientation.Portrait;
        } else {
            this.setBounds(X - diff, Y, height, width);
            this.orientation = Orientation.Landscape;
        }
    }

    void moveLeft() {
        this.setBounds(X - diff, Y, width, height);
    }

    void moveRight() {
        this.setBounds(X + diff, Y, width, height);
    }

    void rotateRight() throws IOException {
        System.out.println("Rotating " + imageFilename + ", index " + index + " right");
        BufferedImage rotated = ImageUtils.rotateRight(imageFilename);
        setNewBounds();
        this.setIcon(new ImageIcon(rotated));
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.X = x;
        this.Y = y;
        this.width = width;
        this.height = height;
    }

    public void select() {
        this.setBorder(selectedBorder);
        this.addKeyListener(keyListener);
        this.bSelected = true;
        try {
            SimpleImageInfo simpleImageInfo = new SimpleImageInfo(new File(imageFilename));
            System.out.println("Width " + simpleImageInfo.getWidth() + " Height: " + simpleImageInfo.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deselect() {
        this.setBorder(emptyBorder);
        this.removeKeyListener(keyListener);
        this.bSelected = false;
    }

    public boolean isSelected() {
        return bSelected;
    }

    public enum Orientation {
        Landscape,
        Portrait
    }
}
