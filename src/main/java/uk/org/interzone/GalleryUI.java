package uk.org.interzone;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

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
        final Border selectedBorder = new LineBorder(Color.YELLOW, 2);
        if (files.size() == 0) {
            return;
        }
        final Border emptyBorder = BorderFactory.createEmptyBorder();
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 4; i++) {
                final NButton button = new NButton("", new ImageIcon(files.get(ind).toString()), ind);
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
                        // when the button is pressed
                        if(button.bSelected) {
//                            button.bSelected = false;
//                            button.setBorder(emptyBorder);
                        } else {
                            button.setBorder(selectedBorder);
                            button.bSelected = true;
                        }
                    }
                });
                ind++;
            }
        }
    }


}
