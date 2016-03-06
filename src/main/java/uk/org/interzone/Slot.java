package uk.org.interzone;

/**
 * Created by nathan on 05/03/2016.
 */
public class Slot {
    protected NButton nButton;
    protected int X, Y, width, height;
    protected int x_centre, y_centre, prevX, prevY, portrait_offset = 0;

    public void add(NButton nButton) {
        this.nButton = nButton;
    }

    public NButton getnButton() {
        return nButton;
    }
}
