package uk.org.interzone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nathan on 09/01/16.
 */
public class GalleryUI extends JFrame {

    public static final int BWIDTH = 96;
    public static final int BHEIGHT = 54;
    public static final int BIGGEST = BWIDTH > BHEIGHT ? BWIDTH : BHEIGHT;
    public static int numRows = 4;
    public static int numCols = 4;
    final Set<NButton> selectedButtons;

    public GalleryUI(List<File> files) {
        super("Photo Gallery");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(620, 620);
        super.setVisible(true);
        super.setLayout(null);
        this.getContentPane().setBackground(Color.BLACK);
        int ind = 0;
        selectedButtons = Collections.newSetFromMap(new ConcurrentHashMap<NButton, Boolean>());
        if (files.size() == 0) {
            return;
        }
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numCols; i++) {
                String filename = files.get(ind).toString();
                final NButton button = new NButton("", filename, ind, selectedButtons, BWIDTH, BHEIGHT);
                add(button);
                button.setBounds(i * BIGGEST + i * BIGGEST / 2 + BIGGEST / 2,
                        j * BIGGEST + j * BIGGEST / 2 + BIGGEST / 2, BWIDTH, BHEIGHT);
                
                button.addMouseMotionListener(new MouseAdapter() {
                    // this works though the images lurch unless you grab them by the top left
                    public void mouseDragged(MouseEvent E) {
                        int X = E.getX() + button.getX() - BWIDTH / 2;  // - BWIDTH / 2  so we are dragging from the centre
                        int Y = E.getY() + button.getY() - BHEIGHT / 2; // - BHEIGHT / 2  so we are dragging from the centre
//                        System.out.println("X: " + X + "  Y: " + Y);
                        button.setBounds(X, Y, BWIDTH, BHEIGHT);
                    }
                });
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // when the shift button is pressed
                        if((e.getModifiers() & InputEvent.SHIFT_MASK) != 0) {
                            button.select();
                            selectedButtons.add(button);
                        } else {
                            button.select();
                            for(NButton b : selectedButtons) {
                                b.deselect();
                                selectedButtons.remove(b);
                            }
                            selectedButtons.add(button);
                        }
                    }
                });
                ind++;
            }
        }
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for(NButton b : selectedButtons) {
                    b.deselect();
                    selectedButtons.remove(b);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
