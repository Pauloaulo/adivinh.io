package GUI.login;

import GUI.Control;
import GUI.room.RoomFrame;
import server.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginFrame extends JFrame implements KeyListener, ActionListener, Protocol {
    protected Control control;
    protected String nickname;

    private JTextField login;

    public LoginFrame(String nickname, Control control) {
        super("Program");

        this.nickname = nickname;
        this.control = control;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500,650);
        this.setLocationRelativeTo(null);

        this.add(setMainPanel());
        this.setVisible(true);
    }

    protected JPanel setMainPanel() {
        JPanel panel = new JPanel();

        login = new JTextField();
        login.addKeyListener(this);
        login.setBounds(150,180,200,50);
        login.setFont(new Font("Comic Sans MS", Font.BOLD, 30));

        panel.setBackground(new Color(0x601A80));
        panel.setLayout(null);
        panel.add(login);
        return panel;
    }

    protected JMenu setMenu() {
        JMenu menu = new JMenu("options");
        JMenuItem item = new JMenuItem("sair");
        item.setActionCommand(LOGOUT_STRING);
        item.addActionListener(this);

        menu.add(item);
        return menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //usused
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //unused
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            String nickname = login.getText();
            if (!nickname.equals("")) {
                control.setRequest(LOGIN_STRING+","+nickname);

                String response = control.getResponse();
                if(response.equals(SUCESSFULL_STRING)) {
                    new RoomFrame(nickname,control);
                    this.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null,ACCOUNT_ALREADY_EXIST_STRING);
                }
            }
        }
    }

    public String getNickname() { return nickname; }

}
