package uk.org.interzone;

import uk.co.jaimon.test.SimpleImageInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nathan on 09/01/16.
 */
public class GalleryUI extends JFrame {

    public static int BWIDTH = 96;
    public static int BHEIGHT = 54;
    public static final int BIGGEST = BWIDTH > BHEIGHT ? BWIDTH : BHEIGHT;
    public static int numRows = 4;
    public static int numCols = 4;
    final Set<NButton> selectedButtons;

    public GalleryUI(List<Image> files) {
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
                final NButton button = new NButton(files.get(ind), ind, selectedButtons, BWIDTH, BHEIGHT);
                add(button);
                button.setBounds(i * BIGGEST + i * BIGGEST / 2 + BIGGEST / 2,
                        j * BIGGEST + j * BIGGEST / 2 + BIGGEST / 2, BWIDTH, BHEIGHT);

                button.addEventHandling();
                ind++;
            }
        }
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (NButton b : selectedButtons) {
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
