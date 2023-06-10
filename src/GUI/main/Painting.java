package GUI.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.BasicStroke;


import javax.swing.JPanel;

public class Painting extends JPanel implements MouseMotionListener, MouseListener, KeyListener {
    private Color[][] sketch;
    private static final int BRUSH_SIZE = 10;
    private final int PAINTING_WIDTH;
    private final int PAINTING_HEIGHT;
    private Coord2D previous;

    private boolean eraseMode;

    public Painting(int w, int h) {
        PAINTING_WIDTH = w;
        PAINTING_HEIGHT = h;

        previous = new Coord2D(-1, -1);
        sketch = new Color[PAINTING_WIDTH][PAINTING_HEIGHT];
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(w, h));
        eraseMode = false;
        clearSketch();
    }

    public void setEraseMode(boolean erase) {
        eraseMode = erase;
    }

    private void clearSketch() {
        for (int x = 0; x < PAINTING_WIDTH; x++) {
            for (int y = 0; y < PAINTING_HEIGHT; y++) {
                sketch[x][y] = Color.WHITE;
            }
        }
    }

    public int getPaintingWidth() {
        return PAINTING_WIDTH;
    }

    public int getPaintingHeight() {
        return PAINTING_HEIGHT;
    }

    public void paint(Graphics gp) {
        Graphics2D ctx = (Graphics2D) gp;

        for (int x = 0; x < PAINTING_WIDTH; x++) {
            for (int y = 0; y < PAINTING_HEIGHT; y++) {
                ctx.setColor(sketch[x][y]);
                ctx.fillOval(x, y, BRUSH_SIZE, BRUSH_SIZE);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();

    if (previous.x != -1 && previous.y != -1 && (-1 < x && x < PAINTING_WIDTH && -1 < y && y < PAINTING_HEIGHT)) {
        Graphics2D ctx = (Graphics2D) getGraphics();
        ctx.setColor(Color.BLACK);
        ctx.setStroke(new BasicStroke(BRUSH_SIZE));

        if (eraseMode) {
            ctx.setColor(Color.WHITE);
        }

        ctx.drawLine(previous.x, previous.y, x, y);
    }

    previous.x = x;
    previous.y = y;
}


    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        previous.x = -1;
        previous.y = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E) {
        if (eraseMode==true) {
            setEraseMode(false);
        }
        else{ 
            setEraseMode(true);
        }
     }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {
    }
}


class Coord2D {
    public int x;
    public int y;

    public Coord2D(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
