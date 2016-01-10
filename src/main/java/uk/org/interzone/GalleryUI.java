package uk.org.interzone;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.lang.String;
import java.util.List;

/**
 * Created by nathan on 09/01/16.
 */
public class GalleryUI extends JFrame {

    public static final int BWIDTH = 120;
    public static final int BHEIGHT = 64;
    public static final String THUMB_PRE = "photos/.llgal/thumb_";
    private ImageIcon[] myImages = {new ImageIcon(THUMB_PRE + "P1000037.JPG"),
            new ImageIcon(THUMB_PRE + "P1000098.JPG"),
            new ImageIcon(THUMB_PRE + "P1000104.JPG"),
            new ImageIcon(THUMB_PRE + "P1000131.JPG")
    };
//            new ImageIcon[4];

    public GalleryUI(List<File> files) {
        super("Position helper");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(500, 520);
        super.setVisible(true);
        super.setLayout(null);
        for (int i = 0; i < 4; i++) {
            final JButton button = new JButton("", new ImageIcon(files.get(i).toString()));
            add(button);
            button.setBounds(i * BWIDTH, BHEIGHT, BWIDTH, BHEIGHT);
            button.addMouseMotionListener(new MouseAdapter() {
                // this works though the images lurch unless you grab them by the top left
                public void mouseDragged(MouseEvent E) {
                    int X = E.getX() + button.getX();
                    int Y = E.getY() + button.getY();
                    System.out.println("X: " +X +"  Y: " +Y);
                    button.setBounds(X, Y, BWIDTH, BHEIGHT);
                }
            });
        }
    }


}
