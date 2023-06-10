package client.game;

import GUI.main.Painting;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window extends JFrame {
    public static final int SCREEN_WIDTH = 360;
    public static final int SCREEN_HEIGHT = 280;

    public Window() {
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("ARTE DO VICTINHO SINISTRÃO");
        Painting painting = new Painting(SCREEN_WIDTH, SCREEN_HEIGHT);
        add(painting);
        pack();
        setResizable(false);
        setVisible(true);
    }
}
