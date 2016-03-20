package uk.org.interzone;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by nathan on 27/02/16.
 */
class MouseMovementAdapter extends MouseAdapter {
    private NButton nButton;

    public MouseMovementAdapter(NButton nButton) {
        this.nButton = nButton;
    }

    @Override
    public void mouseDragged(MouseEvent E) {
        int X = E.getX() + nButton.getX() - GalleryUI.BWIDTH / 2;  // - BWIDTH / 2  so we are dragging from the centre
        int Y = E.getY() + nButton.getY() - GalleryUI.BHEIGHT / 2; // - BHEIGHT / 2  so we are dragging from the centre
        nButton.moveTo(X, Y);   // We could try to lock the moving so that diagonal swaps are not possible

        if (X < nButton.prevX) {
//                    System.out.println("Moving Left  X: " + X + " prevX: " + prevX);
            // look to left neighbor  but what if we are the left most??
            if (nButton.col > 0) {
                int left = nButton.col - 1;
                int leftXcentre = nButton.grid[nButton.row][left].getnButton().getXcentre();
                if (X < leftXcentre) {
                    System.out.println("Current X: " + X + " leftXcentre: " + leftXcentre);
                    System.out.println("Current col: " + nButton.col + " left: " + left);
                    nButton.swapXpositions(left, leftXcentre);
                }
            }

        } else if (X > nButton.prevX) {
//                    System.out.println("Moving Right");
            if (nButton.col < 3) {
                int right = nButton.col + 1;
                int rightXcentre = nButton.grid[nButton.row][right].getnButton().getXcentre();
                if (X > rightXcentre) {
                    System.out.println("Current X: " + X + " rightXcentre: " + rightXcentre);
                    System.out.println("Current col: " + nButton.col + " right: " + right);
                    nButton.swapXpositions(right, rightXcentre);
                }

            }
        } else if (Y < nButton.prevY) {
            System.out.println("Moving Up");
            if (nButton.row > 0) {
                int other = nButton.row - 1;
                int otherYcentre = nButton.grid[other][nButton.col].getnButton().getYcentre();
                if (Y < otherYcentre) {
                    System.out.println("Current Y: " + Y + " aboveYcentre: " + otherYcentre);
                    System.out.println("Current col: " + nButton.row + " above: " + other);
                    nButton.swapYpositions(other, otherYcentre);
                }
            }
        } else if (Y > nButton.prevY) {
            System.out.println("Moving Down");
            if (nButton.row < 3) {
                int other = nButton.row + 1;
                int otherYcentre = nButton.grid[other][nButton.col].getnButton().getYcentre();
                // On Y the number increases as you go down.
                if (Y > otherYcentre) {
                    System.out.println("Current Y: " + Y + " belowYcentre: " + otherYcentre);
                    System.out.println("Current col: " + nButton.row + " below: " + other);
                    nButton.swapYpositions(other, otherYcentre);
                }
            }
        }
        nButton.prevX = X;
        nButton.prevY = Y;
    }
}
