package uk.org.interzone;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Created by nathan on 10/01/16.
 */
public class NButton  extends JButton {
    private final String imageFilename;
    protected boolean bSelected = false;
    protected int index;
    protected Border emptyBorder = BorderFactory.createEmptyBorder();
    protected Border selectedBorder = new LineBorder(Color.YELLOW, 2);
    protected KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            char keyChar = e.getKeyChar();
            System.out.println("typed: " + keyChar);
            if('r' == keyChar || 'R' == keyChar) {
                System.out.println("Rotating " + imageFilename + ", index " + index + " right");
                try {
                    ImageUtils.rotateRight(imageFilename);
//                    reloadIcon();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if('l' == keyChar || 'L' == keyChar) {
                System.out.println("Rotating " + imageFilename + ", index " + index + " right");
                try {
                    ImageUtils.rotateLeft(imageFilename);
//                    reloadIcon();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }


        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("pressed: " + e.getKeyChar());
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    public NButton(String text, String imageFilename, int ind) {
        super(text, new ImageIcon(imageFilename));
        this.imageFilename = imageFilename;
        this.index = ind;
    }

    protected void reloadIcon() {
        super.setIcon(new ImageIcon(imageFilename));
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
