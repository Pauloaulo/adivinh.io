package client.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Screen extends JPanel {
    public int[][] pixels;
    public static final int PIXEL_SIZE = 1;
    public static final int SCREEN_WIDTH = 360;
    public static final int SCREEN_HEIGHT = 240;

    public static void main(String[] args) {
        Screen screen = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT);
        
        JFrame window = new JFrame();
        window.setAlwaysOnTop(true);
        window.setTitle("ARTE DO VICTINHO SINISTRÃO");
        window.add(screen);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public Screen (int w, int h)
    {
        setPreferredSize(new Dimension(w, h));
    }

    public void paint(Graphics gp)
    {
        Graphics2D scrn = (Graphics2D) gp;

        for (int x = 0; x < SCREEN_WIDTH; x++)
        {
            for (int y = 0; y < SCREEN_HEIGHT; y++)
            {
                scrn.fillRect(x, y, PIXEL_SIZE,PIXEL_SIZE);
                scrn.setColor(new Color((x/2)%255, (y/2)%255, (y/2)%255, 255));
            }
        }
    }
}