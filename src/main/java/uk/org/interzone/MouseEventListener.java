package uk.org.interzone;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by nathan on 27/02/16.
 */
class MouseEventListener implements MouseListener {
    private NButton nButton;

    public MouseEventListener(NButton nButton) {
        this.nButton = nButton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released x: " + nButton.getX() + " y: " + nButton.getY() + " " + nButton.getBounds());
        NButton that = nButton;
        nButton.setBounds(that.point.x + nButton.portrait_offset, that.point.y, that.width, that.height);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//                System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
//                System.out.println("mouseExited");
    }
}
