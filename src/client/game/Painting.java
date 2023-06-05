package client.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Painting extends JPanel implements MouseMotionListener, MouseListener
{    
    public boolean[][] sketch;
    public static final int BRUSH_SIZE = 10;
    public static final int PAINTING_WIDTH = 360;
    public static final int PAINTING_HEIGHT = 240;
    public Coord2D previous;

    public Painting (int w, int h)
    {
        previous = new Coord2D(-1, -1);
        sketch = new boolean[PAINTING_WIDTH][PAINTING_HEIGHT];   
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(w, h));
    }

    public void paint(Graphics gp)
    {
        Graphics2D ctx = (Graphics2D) gp;

        for(int x = 0; x < PAINTING_WIDTH; x++)
        {
            for (int y = 0; y < PAINTING_HEIGHT; y++)
            {
                if (sketch[x][y] == true)
                {
                    ctx.fillOval(x, y, BRUSH_SIZE, BRUSH_SIZE);
                    ctx.setColor(Color.BLACK);
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        
        // Funcao que criei (cirilo) para reduzir pontos soltos ao desenhar
        // rápido
        //
        // -> o campo desta classe chamado "previous" (P) 
        //    armazena a coordenada do ultimo ponto (x,y) do rabisco
        // 
        // -> P inicalmente possui coordenadas fora do painting (invalidas) 
        //
        // -> o metodo verifica se P esta dentro do painting,
        ///   se sim siginifica que esta numa posicao valida
        //
        // -> calcula o vetor unitario de P para a posicao atual Q. 
        //
        // -> preenche o espaco de P até Q.
        //
        // -> Q agora passa a ocupar o lugar de P.
        //
        // -> ao soltar o mouse, Q receberá coordenadas invalidas
        //    no metodo "mouseRelased()"

        if (previous.x != -1 && previous.y != -1 && (-1 < x && x < PAINTING_WIDTH && -1 < y && y < PAINTING_HEIGHT))
        {
            double vx = x - previous.x;
            double vy = y - previous.y;
            double modv = Math.sqrt((Math.pow(vx, 2) + Math.pow(vy, 2)));

            vx = (vx/modv);
            vy = (vy/modv);

            float i = previous.x;
            float j = previous.y;

            while (Math.floor(x-i) != 0 && Math.floor(y-j) != 0)
            {
                sketch[Math.round(i)][Math.round(j)] = true;
                i += vx;
                j += vy;
            }
            
            System.out.println("("+vx+","+vy+")");
        }
        
        if (-1 < x && x < PAINTING_WIDTH && -1 < y && y < PAINTING_HEIGHT)
        {
            sketch[x][y] = true;
            previous.x = x;
            previous.y = y;
        }
        
        repaint();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) {
        previous.x = -1;
        previous.y = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}

class Coord2D {
    public int x;
    public int y;
    
    public Coord2D (int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}