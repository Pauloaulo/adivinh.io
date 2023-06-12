package client.views.room;

import client.App;
import client.views.login.LoginFrame;
import client.views.main.RoomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameFrame extends LoginFrame {
    private JMenuBar menuBar;
    private JPanel mainPanel;

    private Chat chat;

    public GameFrame(String id ,String nickname, App control) {
        super(nickname,control);

        int ID = Integer.parseInt(id);
        chat = new Chat(ID,nickname);
        mainPanel.add(chat, BorderLayout.CENTER);

        menuBar = new JMenuBar();
        menuBar.add(setMenu());
        setJMenuBar(menuBar);
    }

    @Override
    public JPanel setMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x601A80));
        return mainPanel;
    }

    @Override
    protected JMenu setMenu() {
        JMenu menu = new JMenu("options");

        JMenuItem item1 = new JMenuItem("sair");
        item1.setActionCommand(LOGOUT_STRING);
        item1.addActionListener(this);

        JMenuItem item2 = new JMenuItem("sair da sala");
        item2.setActionCommand(EXIT_ROOM_STRING);
        item2.addActionListener(this);

        menu.add(item2);
        menu.add(item1);
        return menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(LOGOUT_STRING)) {
            control.setRequest(LOGOUT_STRING);
            dispose();
        }
        if (e.getActionCommand().equals(EXIT_ROOM_STRING)) {
            control.setRequest(EXIT_ROOM_STRING);
            new RoomFrame(nickname, control);
            dispose();
        }
        chat.sendMessage("/quit");
        chat.disconnect();
    }
}
