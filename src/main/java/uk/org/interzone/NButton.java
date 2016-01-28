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
    private final NButton[][] grid;
    protected boolean bSelected = false;
    protected int row, col;
    protected int X, Y, width, height;
    protected Border emptyBorder = BorderFactory.createEmptyBorder();
    protected Border selectedBorder = new LineBorder(Color.YELLOW, 2);
    protected Orientation orientation;
    protected Rectangle rectangle;
    protected int x_centre, prevX, prevY;
    ;
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

    public NButton(Image image, int row, int col, Set<NButton> selectedButtons, NButton[][] grid, int width, int height) {
        this.image = image;
        this.row = row;
        this.col = col;
        this.selectedButtons = selectedButtons;
        this.grid = grid;
        this.width = width;
        this.height = height;
        if (width > height) {
            this.diff = (width - height) / 2;
        } else {
            this.diff = (height - width) / 2;
        }
        this.orientation = image.getOrientation();
        this.setIcon(new ImageIcon(image.toString()));
    }

    protected void setNewOrientation() {
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
        System.out.println("Rotating " + image.toString() + ", col " + col + " left");
        setNewOrientation();
        this.setIcon(new ImageIcon(image.rotateLeft()));
    }

    void rotateRight() throws IOException {
        System.out.println("Rotating " + image.toString() + ", col " + col + " right");
        setNewOrientation();
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

    public void setInitialBounds(int x, int y, int width, int height) {
        this.rectangle = new Rectangle(x, y, width, height);
        setBounds(rectangle);
        this.x_centre = x + this.diff;
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

    public int getXcentre() {
        return x_centre;
    }

    protected void addEventHandling() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent E) {
                int X = E.getX() + getX() - GalleryUI.BWIDTH / 2;  // - BWIDTH / 2  so we are dragging from the centre
                int Y = E.getY() + getY() - GalleryUI.BHEIGHT / 2; // - BHEIGHT / 2  so we are dragging from the centre
                if(Orientation.Landscape == orientation) {
                    setBounds(X, Y, GalleryUI.BWIDTH, GalleryUI.BHEIGHT);
                } else {
                    setBounds(X, Y, GalleryUI.BHEIGHT, GalleryUI.BWIDTH);
                }


                if( X < prevX ) {
                    System.out.println("Moving Left");
                    // look to left neighbor  but what if we are the left most??
                    if( col > 0 ) {
                        int left = col - 1;
                        // Watch out for Pictures in Portrait. Currently not handling this correctly
                        // don't blindly trade rectanges if one is Landscape and the other is potrait.
                        if( X < grid[row][left].getXcentre() ) {
                            Rectangle temp = rectangle;
                            NButton.this.rectangle = grid[row][left].rectangle;
                            int centerx = NButton.this.x_centre;
                            NButton.this.x_centre = grid[row][left].getXcentre();
                            grid[row][left].rePosition(temp,row,col,centerx);
                            grid[row][col] = grid[row][left];
                            grid[row][left] = NButton.this;
                            NButton.this.col = left;
                        }

                    }

                } else if( X > prevX ) {
                    System.out.println("Moving Right");
                }
                if( Y < prevY ) {
                    System.out.println("Moving Up");
                }
                prevX = X;
                prevY = Y;
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouseClicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {
//                System.out.println("mousePressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse released x: " + getX() + " y: " + getY() + " " + getBounds());
                System.out.println(rectangle);
                setBounds(rectangle);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println("mouseEntered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                System.out.println("mouseExited");
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

    protected void rePosition(Rectangle rect, int row, int col, int x_centre) {
        this.rectangle = rect;
        setBounds(rect);
        this.row = row;
        this.col = col;
        this.x_centre = x_centre;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
