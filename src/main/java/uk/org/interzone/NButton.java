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
    protected final Slot[][] grid;
    private final int diff;
    private final Image image;
    protected boolean bSelected = false;
    protected Orientation orientation;
    protected int row, col;
    protected int X, Y, width, height;
    protected int x_centre, y_centre, prevX, prevY, portrait_offset = 0;
    protected Point point;
    protected Border emptyBorder = BorderFactory.createEmptyBorder();
    protected Border selectedBorder = new LineBorder(Color.YELLOW, 2);
    protected static Icon initialIcon;

    protected KeyListener keyListener = new MouseKeyListener(this);

    public NButton(Image image, int row, int col, Set<NButton> selectedButtons, Slot[][] grid, int width, int height) {
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
        initialIcon = this.getIcon();
        this.setIcon(new ImageIcon(image.toString()));
    }

    protected void toggleOrientation() {
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
        toggleOrientation();
        this.setIcon(new ImageIcon(image.rotateLeft()));
    }

    void rotateRight() throws IOException {
        System.out.println("Rotating " + image.toString() + ", col " + col + " right");
        toggleOrientation();
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

    protected void addEventHandling() {
        addMouseMotionListener(new MouseMovementAdapter(this));

        addMouseListener(new MouseEventListener(this));

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
        Point otherPoint = grid[other][col].getnButton().point;
        grid[row][col] = grid[other][col];
        grid[other][col].getnButton().rePositionY(NButton.this.point, row, col, NButton.this.getYcentre());
        NButton.this.rePositionY(otherPoint, other, col, otherYcentre);
        grid[other][col].setnButton(NButton.this);
    }

    void swapXpositions(int other, int otherXcentre) {
        Point otherPoint = grid[row][other].getnButton().point;
        grid[row][col] = grid[row][other];
        grid[row][other].getnButton().rePosition(NButton.this.point, row, col, NButton.this.getXcentre());
        NButton.this.rePosition(otherPoint, row, other, otherXcentre);
        grid[row][other].setnButton(NButton.this);
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

    public boolean isSelected() {
        return bSelected;
    }

    public int getXcentre() {
        return x_centre;
    }

    public int getYcentre() {
        return y_centre;
    }

}
