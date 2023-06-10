<<<<<<< HEAD
<<<<<<< HEAD
package GUI.room;

import GUI.Control;
import GUI.main.GameFrame;
import server.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoomLabel extends JLabel implements MouseListener, Protocol {
    private String id;

    private Control control;
    private RoomFrame frame;
    private RoomsRefresher scheduler;

    //args0 id, args1 title
    public RoomLabel(String[] args, Control control, RoomsRefresher scheduler, RoomFrame frame) {
        super(" " + args[1]);

        this.id = args[0];
        this.frame = frame;
        this.control = control;
        this.scheduler = scheduler;

        this.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        this.setPreferredSize(new Dimension(450, 50));
        this.setBackground(new Color(0x601A80));
        this.setForeground(Color.LIGHT_GRAY);
        this.addMouseListener(this);
        this.setOpaque(true);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == this) {
           control.setRequest(ENTER_ROOM_STRING+","+id);
           String response = control.getResponse();

           if (response.contains(SUCESSFULL_STRING)) {
               scheduler.shutdown();
               frame.dispose();
               new GameFrame(frame.getNickname(),control);
           }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(new Color(0x500A80));
            frame.repaint();

            scheduler.shutdown();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(new Color(0x601A80));
            frame.repaint();

            scheduler.restart();
        }
    }
}
=======
package GUI.room;

import GUI.Control;
import GUI.main.GameFrame;
import server.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoomLabel extends JLabel implements MouseListener, Protocol {
    private String id;

    private Control control;
    private RoomFrame frame;
    private RoomsRefresher scheduler;

    //args0 id, args1 title
    public RoomLabel(String[] args, Control control, RoomsRefresher scheduler, RoomFrame frame) {
        super(" " + args[1]);

        this.id = args[0];
        this.frame = frame;
        this.control = control;
        this.scheduler = scheduler;

        this.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        this.setPreferredSize(new Dimension(450, 50));
        this.setBackground(new Color(0x601A80));
        this.setForeground(Color.LIGHT_GRAY);
        this.addMouseListener(this);
        this.setOpaque(true);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == this) {
           control.setRequest(ENTER_ROOM_STRING+","+id);
           String response = control.getResponse();

           if (response.contains(SUCESSFULL_STRING)) {
               scheduler.shutdown();
               frame.dispose();
               new GameFrame(frame.getNickname(),control);
           }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(new Color(0x500A80));
            frame.repaint();

            scheduler.shutdown();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(new Color(0x601A80));
            frame.repaint();

            scheduler.restart();
        }
    }
}
>>>>>>> 23b1d70e725adc06e152eac01322ca46d1b6d8ce
=======
package GUI.room;

import GUI.Control;
import GUI.main.GameFrame;
import server.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoomLabel extends JLabel implements MouseListener, Protocol {
    private String id;

    private Control control;
    private RoomFrame frame;
    private RoomsRefresher scheduler;

    //args0 id, args1 title
    public RoomLabel(String[] args, Control control, RoomsRefresher scheduler, RoomFrame frame) {
        super(" " + args[1]);

        this.id = args[0];
        this.frame = frame;
        this.control = control;
        this.scheduler = scheduler;

        this.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        this.setPreferredSize(new Dimension(450, 50));
        this.setBackground(new Color(0x601A80));
        this.setForeground(Color.LIGHT_GRAY);
        this.addMouseListener(this);
        this.setOpaque(true);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == this) {
           control.setRequest(ENTER_ROOM_STRING+","+id);
           String response = control.getResponse();

           if (response.contains(SUCESSFULL_STRING)) {
               scheduler.shutdown();
               frame.dispose();
               new GameFrame(frame.getNickname(),control);
           }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(new Color(0x500A80));
            frame.repaint();

            scheduler.shutdown();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(new Color(0x601A80));
            frame.repaint();

            scheduler.restart();
        }
    }
}
>>>>>>> 12f24ba8b94604303eed8067a5a21cabccc7d0d3
