package client.views.main;

import client.App;
import client.views.login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class RoomFrame extends LoginFrame {
    private JMenuBar menuBar;
    private JButton newRoom;
    private JLabel profile;
    private JPanel lower;
    public JScrollPane ls;
    public JPanel upper;
    public JPanel panel;

    private RoomsRefresher scheduler;

    public RoomFrame(String nickname, App control) {
        super(nickname,control);

        profile.setText(nickname);
        profile.setIcon(new ImageIcon(iconPath()));

        menuBar = new JMenuBar();
        menuBar.add(setMenu());
        setJMenuBar(menuBar);

        scheduler = new RoomsRefresher(this);
        (new Thread(scheduler)).start();
    }

    private String iconPath() {
        Random rand = new Random();
        return "resources/profile"+rand.nextInt(5)+".png";
    }

    @Override
    protected JPanel setMainPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        upper = new JPanel();
        upper.setLayout(new BorderLayout());
        upper.setBackground(new Color(0x601A80));
        upper.setPreferredSize(new Dimension(10,100));

        newRoom = new JButton("+");
        newRoom.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        newRoom.setPreferredSize(new Dimension(100,100));
        newRoom.addActionListener(this);
        newRoom.setFocusable(false);

        profile = new JLabel();
        profile.setIconTextGap(10);
        profile.setPreferredSize(new Dimension(100,100));
        profile.setForeground(Color.LIGHT_GRAY);
        profile.setFont(new Font("Comic Sans MS", Font.BOLD, 30));

        upper.add(profile, BorderLayout.CENTER);
        upper.add(newRoom, BorderLayout.EAST);

        lower = new JPanel();
        lower.setBackground(new Color(0x801A80));
        lower.setPreferredSize(new Dimension(10,10));
        lower.setLayout(new FlowLayout());
        
        ls = new JScrollPane(lower, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        panel.add(upper,BorderLayout.NORTH);
        panel.add(ls,BorderLayout.CENTER);
        return panel;
    }
    
    public void getRooms() {
        lower.removeAll();
        control.setRequest(GET_ROOMS_STRING);
        String response = control.getResponse();

        String[] rooms = response.split("\n");
        for (String room : rooms) {
            String r[] = room.split(",");
            
            if (r.length == 4) {
                RoomLabel label = new RoomLabel(r, control,scheduler,this);
                lower.add(label);
            }
        }
        //PROBLEM: janela abre toda vez que a lista de sala é atualizada
        this.repaint();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newRoom) {
            new NewRoomFrame(nickname,control);
            scheduler.shutdown();
            dispose();
        }
        if (e.getActionCommand().equals(LOGOUT_STRING)) {
            scheduler.shutdown();
            control.setRequest(LOGOUT_STRING);
            dispose();
        }
    }

}
