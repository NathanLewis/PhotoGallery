package uk.org.interzone;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Created by nathan on 26/02/16.
 */
class MouseKeyListener implements KeyListener {
    private NButton nButton;

    public MouseKeyListener(NButton nButton) {
        this.nButton = nButton;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
//            System.out.println("typed: " + keyChar);
        if ('r' == keyChar || 'R' == keyChar) {
            try {
                for (NButton selected : nButton.selectedButtons) {
                    selected.rotateRight();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if ('l' == keyChar || 'L' == keyChar) {
            try {
                for (NButton selected : nButton.selectedButtons) {
                    selected.rotateLeft();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if ('h' == keyChar || 'H' == keyChar) {
            for (NButton selected : nButton.selectedButtons) {
                selected.moveLeft();
            }
        } else if ('j' == keyChar || 'J' == keyChar) {
            for (NButton selected : nButton.selectedButtons) {
                selected.moveRight();
            }
        } else if ('i' == keyChar || 'I' == keyChar) {
            int left = nButton.col;
            if (nButton.col > 0) {
                left = nButton.col - 1;
            }
            System.out.println("Xcentre: " + nButton.x_centre + "  Column: " + nButton.col + " leftXcentre: " + nButton.grid[nButton.row][left].getXcentre());
        } else if ('d' == keyChar || 'D' == keyChar) {
            System.out.println("delete");
            // the button self destructs
            nButton.setIcon(NButton.initialIcon); // blank the icon
            // remove all listeners
//            nButton.removeKeyListener(this);
            nButton.deselect();
//            nButton.removeActionListener(nButton.getActionListeners()[0]);
//            nButton.removeMouseListener(nButton.g);
            // give up its place and delete the picture
            // delete the button and move everything after it over by one

            // one way to do this might be to swap with each successive button
            // until it is at the end and then grab the next picture.
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
