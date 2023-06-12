package client.views.main;

import client.App;
import client.views.room.GameFrame;
import server.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoomLabel extends JLabel implements MouseListener, Protocol {
    private String id;
    private String theme;

    private App control;
    private RoomFrame frame;
    private RoomsRefresher scheduler;

    //args0 id, args1 title
    public RoomLabel(String[] args, App control, RoomsRefresher scheduler, RoomFrame frame) {
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
           System.out.println(response);
           if (response.contains(JOINED_IN_A_ROOM_STRING)) {
               scheduler.shutdown();
               frame.dispose();
               new GameFrame(id,frame.getNickname(),control);
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
