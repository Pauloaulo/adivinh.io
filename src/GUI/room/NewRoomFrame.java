<<<<<<< HEAD
<<<<<<< HEAD
package GUI.room;

import GUI.Control;
import GUI.main.GameFrame;
import GUI.login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewRoomFrame extends LoginFrame {
    private JMenuBar menuBar;
    private JTextField roomName;
    private JTextField roomTheme;

    public NewRoomFrame(String nickname, Control control) {
        super(nickname,control);

        menuBar = new JMenuBar();
        menuBar.add(setMenu());
        setJMenuBar(menuBar);
    }

    @Override
    protected JPanel setMainPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x601A80));
        panel.setLayout(null);

        JLabel title = new JLabel("Nova Sala");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        title.setForeground(Color.LIGHT_GRAY);
        title.setBounds(170,0,200,45);

        JLabel name = new JLabel("Nome");
        name.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        name.setForeground(Color.LIGHT_GRAY);
        name.setBounds(200,100,100,45);

        roomName = new JTextField();
        roomName.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        roomName.setBounds(90,150,300,50);
        roomName.addKeyListener(this);

        JLabel theme = new JLabel("Tema");
        theme.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        theme.setForeground(Color.LIGHT_GRAY);
        theme.setBounds(200,250,100,45);

        roomTheme = new JTextField();
        roomTheme.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        roomTheme.setBounds(90,300,300,50);
        roomTheme.addKeyListener(this);

        panel.add(roomName);
        panel.add(roomTheme);
        panel.add(name);
        panel.add(theme);
        panel.add(title);
        panel.setVisible(true);
        return panel;
    }

    @Override
    protected JMenu setMenu() {
        JMenu menu = new JMenu("options");

        JMenuItem item1 = new JMenuItem("sair");
        item1.setActionCommand(LOGOUT_STRING);
        item1.addActionListener(this);

        JMenuItem item2 = new JMenuItem("voltar");
        item2.setActionCommand("go_back");
        item2.addActionListener(this);

        menu.add(item2);
        menu.add(item1);
        return menu;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            String name = roomName.getText();
            String theme = roomTheme.getText();
            if (!name.equals("") && !theme.equals("")) {
                control.setRequest(CREATE_ROOM_STRING+","+name);

                this.dispose();
                new GameFrame(nickname,control);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(LOGOUT_STRING)) {
            control.setRequest(LOGOUT_STRING);
            dispose();
        }
        if (e.getActionCommand().equals("go_back")) {
            new RoomFrame(nickname, control);
            dispose();
        }
    }
}
=======
package GUI.room;

import GUI.Control;
import GUI.main.GameFrame;
import GUI.login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewRoomFrame extends LoginFrame {
    private JMenuBar menuBar;
    private JTextField roomName;
    private JTextField roomTheme;

    public NewRoomFrame(String nickname, Control control) {
        super(nickname,control);

        menuBar = new JMenuBar();
        menuBar.add(setMenu());
        setJMenuBar(menuBar);
    }

    @Override
    protected JPanel setMainPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x601A80));
        panel.setLayout(null);

        JLabel title = new JLabel("Nova Sala");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        title.setForeground(Color.LIGHT_GRAY);
        title.setBounds(170,0,200,45);

        JLabel name = new JLabel("Nome");
        name.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        name.setForeground(Color.LIGHT_GRAY);
        name.setBounds(200,100,100,45);

        roomName = new JTextField();
        roomName.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        roomName.setBounds(90,150,300,50);
        roomName.addKeyListener(this);

        JLabel theme = new JLabel("Tema");
        theme.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        theme.setForeground(Color.LIGHT_GRAY);
        theme.setBounds(200,250,100,45);

        roomTheme = new JTextField();
        roomTheme.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        roomTheme.setBounds(90,300,300,50);
        roomTheme.addKeyListener(this);

        panel.add(roomName);
        panel.add(roomTheme);
        panel.add(name);
        panel.add(theme);
        panel.add(title);
        panel.setVisible(true);
        return panel;
    }

    @Override
    protected JMenu setMenu() {
        JMenu menu = new JMenu("options");

        JMenuItem item1 = new JMenuItem("sair");
        item1.setActionCommand(LOGOUT_STRING);
        item1.addActionListener(this);

        JMenuItem item2 = new JMenuItem("voltar");
        item2.setActionCommand("go_back");
        item2.addActionListener(this);

        menu.add(item2);
        menu.add(item1);
        return menu;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            String name = roomName.getText();
            String theme = roomTheme.getText();
            if (!name.equals("") && !theme.equals("")) {
                control.setRequest(CREATE_ROOM_STRING+","+name);

                this.dispose();
                new GameFrame(nickname,control);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(LOGOUT_STRING)) {
            control.setRequest(LOGOUT_STRING);
            dispose();
        }
        if (e.getActionCommand().equals("go_back")) {
            new RoomFrame(nickname, control);
            dispose();
        }
    }
}
>>>>>>> 23b1d70e725adc06e152eac01322ca46d1b6d8ce
=======
package GUI.room;

import GUI.Control;
import GUI.main.GameFrame;
import GUI.login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewRoomFrame extends LoginFrame {
    private JMenuBar menuBar;
    private JTextField roomName;
    private JTextField roomTheme;

    public NewRoomFrame(String nickname, Control control) {
        super(nickname,control);

        menuBar = new JMenuBar();
        menuBar.add(setMenu());
        setJMenuBar(menuBar);
    }

    @Override
    protected JPanel setMainPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x601A80));
        panel.setLayout(null);

        JLabel title = new JLabel("Nova Sala");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        title.setForeground(Color.LIGHT_GRAY);
        title.setBounds(170,0,200,45);

        JLabel name = new JLabel("Nome");
        name.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        name.setForeground(Color.LIGHT_GRAY);
        name.setBounds(200,100,100,45);

        roomName = new JTextField();
        roomName.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        roomName.setBounds(90,150,300,50);
        roomName.addKeyListener(this);

        JLabel theme = new JLabel("Tema");
        theme.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        theme.setForeground(Color.LIGHT_GRAY);
        theme.setBounds(200,250,100,45);

        roomTheme = new JTextField();
        roomTheme.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        roomTheme.setBounds(90,300,300,50);
        roomTheme.addKeyListener(this);

        panel.add(roomName);
        panel.add(roomTheme);
        panel.add(name);
        panel.add(theme);
        panel.add(title);
        panel.setVisible(true);
        return panel;
    }

    @Override
    protected JMenu setMenu() {
        JMenu menu = new JMenu("options");

        JMenuItem item1 = new JMenuItem("sair");
        item1.setActionCommand(LOGOUT_STRING);
        item1.addActionListener(this);

        JMenuItem item2 = new JMenuItem("voltar");
        item2.setActionCommand("go_back");
        item2.addActionListener(this);

        menu.add(item2);
        menu.add(item1);
        return menu;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            String name = roomName.getText();
            String theme = roomTheme.getText();
            if (!name.equals("") && !theme.equals("")) {
                control.setRequest(CREATE_ROOM_STRING+","+name);

                this.dispose();
                new GameFrame(nickname,control);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(LOGOUT_STRING)) {
            control.setRequest(LOGOUT_STRING);
            dispose();
        }
        if (e.getActionCommand().equals("go_back")) {
            new RoomFrame(nickname, control);
            dispose();
        }
    }
}
>>>>>>> 12f24ba8b94604303eed8067a5a21cabccc7d0d3
