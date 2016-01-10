package uk.org.interzone;

import javax.swing.*;

/**
 * Created by nathan on 10/01/16.
 */
public class NButton  extends JButton {
    protected boolean bSelected = false;
    protected int index;

    NButton() { super(); }

    public NButton(String text, ImageIcon imageIcon, int ind) {
        super(text, imageIcon);
        this.index = ind;
    }
}
