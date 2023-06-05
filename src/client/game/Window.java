package client.game;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window extends JFrame {
    public static final int SCREEN_WIDTH = 360;
    public static final int SCREEN_HEIGHT = 280;

    public Window ()
    {
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("ARTE DO VICTINHO SINISTRÃO");
        add(new Painting(Painting.PAINTING_WIDTH, Painting.PAINTING_HEIGHT));
        pack();
        setResizable(false);
        setVisible(true);
    }
}
