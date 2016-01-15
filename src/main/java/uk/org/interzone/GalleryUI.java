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

    public static final int BWIDTH = 120;
    public static final int BHEIGHT = 64;

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
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 4; i++) {
                String filename = files.get(ind).toString();
                final NButton button = new NButton("", filename, ind);
                add(button);
                button.setBounds(i * BWIDTH + i * BWIDTH / 5 + BWIDTH / 5, j * BHEIGHT + j * BHEIGHT / 5 + BHEIGHT / 5, BWIDTH, BHEIGHT);
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
