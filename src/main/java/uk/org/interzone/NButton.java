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
    protected int x_centre, y_centre, prevX, prevY, portrait_offset = 0;
    protected Rectangle rectangle;
    protected Point point;

    protected KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            char keyChar = e.getKeyChar();
//            System.out.println("typed: " + keyChar);
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
            } else if ('i' == keyChar || 'I' == keyChar) {
                int left = col;
                if (col > 0) {
                    left = col - 1;
                }
                System.out.println("Xcentre: " + x_centre + "  Column: " + col + " leftXcentre: " + grid[row][left].getXcentre());
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
            this.orientation = Orientation.Landscape;
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
            this.portrait_offset = diff;
        } else {
            this.setBounds(X - diff, Y, height, width); // we want this
            this.orientation = Orientation.Landscape;
            this.portrait_offset = 0;
        }
    }

    // moveLeft and moveRight were just test methods
    void moveLeft() {
        this.moveTo(X - diff, Y);
    }

    void moveRight() {
        this.moveTo(X + diff, Y);
    }

    void moveTo(int x, int y) {
        super.setBounds(x, y, this.width, this.height);
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

    public void setInitialBounds(int x, int portrait_offset, int y, int width, int height) {
        setBounds(x + portrait_offset, y, width, height);
        if (0 < portrait_offset) {
            System.out.println("portrait_offset: " + portrait_offset);
        }
        this.point = new Point(x, y);
        this.portrait_offset = portrait_offset;
        this.x_centre = x + portrait_offset + this.diff;  // So we get the centre not the corner
        this.y_centre = y;
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

    public int getYcentre() {
        return y_centre;
    }

    protected void addEventHandling() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent E) {
                int X = E.getX() + getX() - GalleryUI.BWIDTH / 2;  // - BWIDTH / 2  so we are dragging from the centre
                int Y = E.getY() + getY() - GalleryUI.BHEIGHT / 2; // - BHEIGHT / 2  so we are dragging from the centre
                moveTo(X, Y);   // We could try to lock the moving so that diagonal swaps are not possible

                if (X < prevX) {
//                    System.out.println("Moving Left  X: " + X + " prevX: " + prevX);
                    // look to left neighbor  but what if we are the left most??
                    if (col > 0) {
                        int left = col - 1;
                        int leftXcentre = grid[row][left].getXcentre();
                        if (X < leftXcentre) {
                            System.out.println("Current X: " + X + " leftXcentre: " + leftXcentre);
                            System.out.println("Current col: " + col + " left: " + left);
                            swapXpositions(left, leftXcentre);
                        }
                    }

                } else if (X > prevX) {
//                    System.out.println("Moving Right");
                    if (col < 3) {
                        int right = col + 1;
                        int rightXcentre = grid[row][right].getXcentre();
                        if (X > rightXcentre) {
                            System.out.println("Current X: " + X + " rightXcentre: " + rightXcentre);
                            System.out.println("Current col: " + col + " right: " + right);
                            swapXpositions(right, rightXcentre);
                        }

                    }
                } else if (Y < prevY) {
                    System.out.println("Moving Up");
                    if (row > 0) {
                        int other = row - 1;
                        int otherYcentre = grid[other][col].getYcentre();
                        if (Y < otherYcentre) {
                            System.out.println("Current Y: " + Y + " aboveYcentre: " + otherYcentre);
                            System.out.println("Current col: " + row + " above: " + other);
                            swapYpositions(other, otherYcentre);
                        }
                    }
                } else if (Y > prevY) {
                    System.out.println("Moving Down");
                    if (row < 3) {
                        int other = row + 1;
                        int otherYcentre = grid[other][col].getYcentre();
                        // On Y the number increases as you go down.
                        if (Y > otherYcentre) {
                            System.out.println("Current Y: " + Y + " belowYcentre: " + otherYcentre);
                            System.out.println("Current col: " + row + " below: " + other);
                            swapYpositions(other, otherYcentre);
                        }
                    }
                }
                prevX = X;
                prevY = Y;
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println("mouseClicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {
//                System.out.println("mousePressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse released x: " + getX() + " y: " + getY() + " " + getBounds());
                NButton that = NButton.this;
                setBounds(that.point.x + portrait_offset, that.point.y, that.width, that.height);
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

    void swapYpositions(int other, int otherYcentre) {
        Point otherPoint = grid[other][col].point;
        grid[row][col] = grid[other][col];
        grid[other][col].rePositionY(NButton.this.point, row, col, NButton.this.getYcentre());
        NButton.this.rePositionY(otherPoint, other, col, otherYcentre);
        grid[other][col] = NButton.this;
    }

    void swapXpositions(int other, int otherXcentre) {
        Point otherPoint = grid[row][other].point;
        grid[row][col] = grid[row][other];
        grid[row][other].rePosition(NButton.this.point, row, col, NButton.this.getXcentre());
        NButton.this.rePosition(otherPoint, row, other, otherXcentre);
        grid[row][other] = NButton.this;
    }

    protected void rePosition(Point point, int row, int col, int x_centre) {
        setBounds(point.x + portrait_offset, point.y, width, height);
        this.point = point;
        this.row = row;
        this.col = col;
        this.x_centre = x_centre;
    }

    protected void rePositionY(Point point, int row, int col, int y_centre) {
        setBounds(point.x + portrait_offset, point.y, width, height);
        this.point = point;
        this.row = row;
        this.col = col;
        this.y_centre = y_centre;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
