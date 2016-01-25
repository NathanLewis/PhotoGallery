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

    public GalleryUI(List<Image> images) {
        super("Photo Gallery");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(620, 620);
        super.setVisible(true);
        super.setLayout(null);
        this.getContentPane().setBackground(Color.BLACK);
        int ind = 0;
        selectedButtons = Collections.newSetFromMap(new ConcurrentHashMap<NButton, Boolean>());
        if (images.size() == 0) {
            return;
        }

        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numCols; i++) {
                Image image = images.get(ind);
                final NButton button = new NButton(image, ind, selectedButtons, BWIDTH, BHEIGHT);
                add(button);
                int X = i * BIGGEST + i * BIGGEST / 2 + BIGGEST / 2;
                int Y = j * BIGGEST + j * BIGGEST / 2 + BIGGEST / 2;
                if(Orientation.Landscape == image.getOrientation()) {
//                    button.setBounds(new Rectangle(X, Y, BWIDTH, BHEIGHT));
                    button.setInitialBounds(X, Y, BWIDTH, BHEIGHT);
                } else {
                    button.setInitialBounds(X + (BWIDTH - BHEIGHT)/2, Y, BHEIGHT, BWIDTH);
                }
                button.addEventHandling();
                ind++;
            }
        }
        this.addMouseListener(new MouseAdapter() {
            // mouseClick on the canvas deselects all
            // selected thumbnails
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (NButton b : selectedButtons) {
                    b.deselect();
                    selectedButtons.remove(b);
                }
            }
        });
    }

}
