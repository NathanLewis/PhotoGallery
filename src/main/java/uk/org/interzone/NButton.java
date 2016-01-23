package uk.org.interzone;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Set;

/**
 * Created by nathan on 10/01/16.
 */
public class NButton extends JButton {
    protected final Set<NButton> selectedButtons;
    private final int diff;
    private final Image image;
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

    public NButton(Image image, int ind, Set<NButton> selectedButtons, int width, int height) {
        this.image = image;
        this.index = ind;
        this.selectedButtons = selectedButtons;
        this.width = width;
        this.height = height;
        if (width > height) {
            this.diff = (width - height) / 2;
        } else {
            this.diff = (height - width) / 2;
        }
        this.setIcon(new ImageIcon(image.toString()));
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

    void rotateLeft() throws IOException {
        System.out.println("Rotating " + image.toString() + ", index " + index + " left");
        setNewBounds();
        this.setIcon(new ImageIcon(image.rotateLeft()));
    }

    void rotateRight() throws IOException {
        System.out.println("Rotating " + image.toString() + ", index " + index + " right");
        setNewBounds();
        this.setIcon(new ImageIcon(image.rotateRight()));
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

    protected void addEventHandling() {
        addMouseMotionListener(new MouseAdapter() {
            // this works though the images lurch unless you grab them by the top left
            public void mouseDragged(MouseEvent E) {
                int X = E.getX() + getX() - GalleryUI.BWIDTH / 2;  // - BWIDTH / 2  so we are dragging from the centre
                int Y = E.getY() + getY() - GalleryUI.BHEIGHT / 2; // - BHEIGHT / 2  so we are dragging from the centre
//                        System.out.println("X: " + X + "  Y: " + Y);
                setBounds(X, Y, GalleryUI.BWIDTH, GalleryUI.BHEIGHT);
            }
        });
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // when the shift button is pressed
                if ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0) {
                    select();
                    selectedButtons.add(NButton.this);
                } else {
                    select();
                    for (NButton b : selectedButtons) {
                        b.deselect();
                        selectedButtons.remove(b);
                    }
                    selectedButtons.add(NButton.this);
                }
            }
        });
    }

}
