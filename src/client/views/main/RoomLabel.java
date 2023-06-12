package client.views.main;

import client.App;
import client.views.room.GameFrame;
import server.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoomLabel extends JPanel implements MouseListener, Protocol {
    public String id;
    public String title;
    public String theme;
    public String connectedUsers;

    private App control;
    private RoomFrame frame;
    private RoomsRefresher scheduler;

    //args0 id, args1 title, args2 users, args3 theme
    public RoomLabel(String[] args, App control, RoomsRefresher scheduler, RoomFrame frame) {

        this.id = args[0];
        this.title = args[1];
        this.connectedUsers = args[2];
        this.theme = args[3];
        
        this.frame = frame;
        this.control = control;
        this.scheduler = scheduler;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel idLabel = new JLabel();
        idLabel.setText("id: " + id);
        idLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        idLabel.setForeground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel();
        titleLabel.setText(title);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel themeLabel = new JLabel();
        themeLabel.setText("Tema: \"" + theme + "\" | Usuarios: " + connectedUsers);
        themeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        themeLabel.setForeground(Color.LIGHT_GRAY);
        
        add(Box.createHorizontalStrut(8));
        add(idLabel);
        add(titleLabel);
        add(themeLabel);
        add(Box.createVerticalStrut(16));

        this.setPreferredSize(new Dimension(450, 100));
        this.setBackground(new Color(0x601A80));
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
