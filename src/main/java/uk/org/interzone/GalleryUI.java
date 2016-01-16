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

    public GalleryUI(List<File> files) {
        super("Photo Gallery");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(620, 620);
        super.setVisible(true);
        super.setLayout(null);
        this.getContentPane().setBackground(Color.BLACK);
        int ind = 0;
        final Set<NButton> selectedButtons = Collections.newSetFromMap(new ConcurrentHashMap<NButton, Boolean>());
        if (files.size() == 0) {
            return;
        }
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numCols; i++) {
                String filename = files.get(ind).toString();
                final NButton button = new NButton("", filename, ind, selectedButtons);
                add(button);
                button.setBounds(i * BIGGEST + i * BIGGEST / 2 + BIGGEST / 2, j * BIGGEST + j * BIGGEST / 2 + BIGGEST / 2, BWIDTH, BHEIGHT);
                button.addMouseMotionListener(new MouseAdapter() {
                    // this works though the images lurch unless you grab them by the top left
                    public void mouseDragged(MouseEvent E) {
                        int X = E.getX() + button.getX();
                        int Y = E.getY() + button.getY();
                        System.out.println("X: " + X + "  Y: " + Y);
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
    }


}
