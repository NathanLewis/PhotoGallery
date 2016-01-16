package uk.org.interzone;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;

/**
 * Created by nathan on 10/01/16.
 */
public class NButton  extends JButton {
    protected final String imageFilename;
    protected final Set<NButton> selectedButtons;
    protected boolean bSelected = false;
    protected int index;
    protected int X,Y, width, height;
    protected Border emptyBorder = BorderFactory.createEmptyBorder();
    protected Border selectedBorder = new LineBorder(Color.YELLOW, 2);
    protected KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            char keyChar = e.getKeyChar();
            System.out.println("typed: " + keyChar);
            if('r' == keyChar || 'R' == keyChar) {
                try {
                    for(NButton selected: selectedButtons) {
                        selected.rotateRight();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if('l' == keyChar || 'L' == keyChar) {
                try {
                    for(NButton selected: selectedButtons) {
                        selected.rotateLeft();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
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

    void rotateLeft() throws IOException {
        System.out.println("Rotating " + imageFilename + ", index " + index + " right");
        BufferedImage rotated = ImageUtils.rotateLeft(imageFilename);
        this.setBounds(X, Y, height, width);
        this.setIcon(new ImageIcon(rotated));
    }

    void rotateRight() throws IOException {
        System.out.println("Rotating " + imageFilename + ", index " + index + " right");
        BufferedImage rotated = ImageUtils.rotateRight(imageFilename);
        this.setBounds(X, Y, height, width);
        this.setIcon(new ImageIcon(rotated));
    }

    public NButton(String text, String imageFilename, int ind, Set<NButton> selectedButtons) {
        super(text, new ImageIcon(imageFilename));
        this.imageFilename = imageFilename;
        this.index = ind;
        this.selectedButtons = selectedButtons;
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
    }

    public void deselect() {
        this.setBorder(emptyBorder);
        this.removeKeyListener(keyListener);
        this.bSelected = false;
    }

    public boolean isSelected() {
        return bSelected;
    }
}
