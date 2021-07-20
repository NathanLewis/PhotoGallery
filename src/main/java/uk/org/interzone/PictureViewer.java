package uk.org.interzone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PictureViewer extends JFrame {

    public PictureViewer(MyImage myImage) throws HeadlessException {
        super.setSize(620, 620); //
        super.setLayout(new GridLayout(1,1));
        super.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        System.out.println("Image path is " + myImage.toString());
        JLabel label = new JLabel(new ImageIcon(myImage.getOriginalPath()));
        JPanel panel = new JPanel();
        panel.add(label);
        panel.setBackground(Color.BLACK);
//        panel.setSize(620, 620);
        JScrollPane pane = new JScrollPane(panel);
        this.add(pane);
        this.pack();
        this.setVisible(true);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("TYPED: " + e.toString());
            }

            @Override
            public void keyPressed(KeyEvent evt) {
                System.out.println("KEY PRESSED: " + evt.toString());
                boolean isMetaDown = evt.META_DOWN_MASK == (evt.getModifiersEx() & InputEvent.META_DOWN_MASK);
                char keyChar = evt.getKeyChar();
                if( ('W' == keyChar || 'w' == keyChar) && isMetaDown ) {
                    System.out.println("CLOSE WINDOW");
                    dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
//                System.out.println("KEY EVENT: " + e.toString());
            }
        });
    }

}
