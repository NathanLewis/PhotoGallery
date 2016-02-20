package uk.org.interzone;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;
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
    protected int x_centre, y_centre, prevX, prevY;
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
                if(col > 0) {
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
            this.setBounds(X - diff, Y, height, width); // we want this
            this.orientation = Orientation.Landscape;
        }
    }

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

    public void setInitialBounds(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        this.point = new Point(x, y);
//        this.rectangle = new Rectangle(x, y, width, height);
        this.x_centre = x + this.diff;
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
                moveTo(X, Y);

                if( X < prevX /* + GalleryUI.BWIDTH/2 */ ) {
//                    System.out.println("Moving Left  X: " + X + " prevX: " + prevX);
                    // look to left neighbor  but what if we are the left most??
                    if( col > 0 ) {
                        int left = col - 1;
                        // Watch out for Pictures in Portrait. Currently not handling this correctly
                        // don't blindly trade rectanges if one is Landscape and the other is potrait.
                        int leftXcentre = grid[row][left].getXcentre();
//                        System.out.println("leftXcentre: " + leftXcentre);
                        if( X < leftXcentre) {
                            System.out.println("Current X: " + X + " leftXcentre: " + leftXcentre);
                            System.out.println("Current col: " + col + " left: " + left);
                            Point leftPoint = grid[row][left].point;
//                            NButton.this.x_centre = grid[row][left].getXcentre();
                            grid[row][left].rePosition( NButton.this.point, row, col, NButton.this.getXcentre() );
                            grid[row][col] = grid[row][left];
                            grid[row][left] = NButton.this;
                            NButton.this.rePosition(leftPoint, row, left, leftXcentre);
//                            NButton.this.col = left;
                        }
                    }

                } else if( X > prevX /* - GalleryUI.BWIDTH/2 */) {
//                    System.out.println("Moving Right");
                    if( col < 3 ) {
                        int right = col + 1;
                        // Watch out for Pictures in Portrait. Currently not handling this correctly
                        // don't blindly trade rectanges if one is Landscape and the other is potrait.
                        int rightXcentre = grid[row][right].getXcentre();
//                        System.out.println("rightXcentre: " + rightXcentre);
                        if( X > rightXcentre) {
                            System.out.println("Current X: " + X + " rightXcentre: " + rightXcentre);
                            System.out.println("Current col: " + col + " right: " + right);
                            Point rightPoint = grid[row][right].point;
                            grid[row][right].rePosition( NButton.this.point, row, col, NButton.this.getXcentre() );
                            grid[row][col] = grid[row][right];
                            grid[row][right] = NButton.this;
                            NButton.this.rePosition(rightPoint, row, right, rightXcentre);
                        }

                    }
                }
                else if( Y < prevY ) {
                    System.out.println("Moving Up");
                    if( row > 0 ) {
                        int above = row - 1;
                        // Watch out for Pictures in Portrait. Currently not handling this correctly
                        int aboveYcentre = grid[above][col].getYcentre();
                        if( Y < aboveYcentre  ) {
                            System.out.println("Current Y: " + Y + " aboveYcentre: " + aboveYcentre);
                            System.out.println("Current col: " + row + " above: " + above);
                            Point abovePoint = grid[above][col].point;
                            grid[above][col].rePositionY( NButton.this.point, row, col, NButton.this.getYcentre() );
                            grid[row][col] = grid[above][col];
                            grid[above][col] = NButton.this;
                            NButton.this.rePositionY(abovePoint, above, col, aboveYcentre);
                        }
                    }
                } else if( Y > prevY ) {
                    System.out.println("Moving Down");
                    if( row < 3 ) {
                        int below = row + 1;
                        // Watch out for Pictures in Portrait. Currently not handling this correctly
                        int belowYcentre = grid[below][col].getYcentre();
                        // On Y the number increases as you go down.
                        if( Y > belowYcentre ) {
                            System.out.println("Current Y: " + Y + " belowYcentre: " + belowYcentre);
                            System.out.println("Current col: " + row + " below: " + below);
                            Point belowPoint = grid[below][col].point;
//                            NButton.this.Y_centre = grid[row][below].getYcentre();
                            grid[below][col].rePositionY( NButton.this.point, row, col, NButton.this.getYcentre() );
                            grid[row][col] = grid[below][col];
                            grid[below][col] = NButton.this;
                            NButton.this.rePositionY(belowPoint, below, col, belowYcentre);
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
                setBounds(that.point.x, that.point.y, that.width, that.height);
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

    protected void rePosition(Point point, int row, int col, int x_centre) {
        setBounds(point.x, point.y, width, height);
        this.point = point;
        this.row = row;
        this.col = col;
        this.x_centre = x_centre;
    }

    protected void rePositionY(Point point, int row, int col, int y_centre) {
        setBounds(point.x, point.y, width, height);
        this.point = point;
        this.row = row;
        this.col = col;
        this.y_centre = y_centre;
    }
}
