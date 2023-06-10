package GUI.main;

import GUI.Control;
import GUI.login.LoginFrame;
import GUI.room.RoomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameFrame extends LoginFrame {
    private Painting paint;
    private JMenuBar menuBar;

    public GameFrame(String nickname, Control control) {
        super(nickname,control);

        menuBar = new JMenuBar();
        menuBar.add(setMenu());
        setJMenuBar(menuBar);
    }

    @Override
    public JPanel setMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0x601A80));

        paint = new Painting(500,280);

        panel.add(paint, BorderLayout.NORTH);
        return panel;
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
    }
}
