package uk.org.interzone;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by nathan on 10/01/16.
 */
public class NButton  extends JButton {
    protected boolean bSelected = false;
    protected int index;
    protected Border emptyBorder = BorderFactory.createEmptyBorder();
    protected Border selectedBorder = new LineBorder(Color.YELLOW, 2);
    protected KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("typed: " + e.getKeyChar());
        }

        @Override
        public void keyPressed(KeyEvent e) {
//            System.out.println("pressed: " + e.getKeyChar());
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    NButton() { super(); }

    public NButton(String text, ImageIcon imageIcon, int ind) {
        super(text, imageIcon);
        this.index = ind;
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
