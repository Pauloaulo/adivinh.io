package client.views.login;

import client.App;
import client.views.main.RoomFrame;
import server.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginFrame extends JFrame implements KeyListener, ActionListener, Protocol {
    protected App control;
    protected String nickname;
    private JTextField login;

    public LoginFrame(String nickname, App control) {
        super("Program");

        this.nickname = nickname;
        this.control = control;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500,650);
        this.setLocationRelativeTo(null);

        this.add(setMainPanel());
        this.setVisible(true);
        setAlwaysOnTop(true);
    }

    protected JPanel setMainPanel() {
        JPanel panel = new JPanel();
        
        login = new JTextField();
        login.addKeyListener(this);
        login.setBounds(150,180,200,50);
        login.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        login.setPreferredSize(new Dimension(200, 30));
        login.setBorder(BorderFactory.createEmptyBorder());

        JLabel lbel = new JLabel();
        lbel.setText("nickname:");
        lbel.setForeground(Color.WHITE);

        JButton loginbtn = new JButton();
        loginbtn.setText("entrar");
        loginbtn.addActionListener(this);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.PAGE_AXIS));
        formPanel.add(lbel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(login);
        formPanel.add(Box.createVerticalStrut(16));
        formPanel.add(loginbtn);
        formPanel.setBackground(new Color(0x601A80));

        //panel.setLayout(new BorderLayout());
        panel.add(formPanel);
        panel.setSize(new Dimension(300, 400));
        panel.setBackground(new Color(0x601A80));
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
        login();
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
            login();
        }
    }

    public void login()
    {
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

    public String getNickname() { return nickname; }

}
