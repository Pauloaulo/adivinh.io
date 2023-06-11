package client.views.room;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Chat extends JPanel implements ActionListener, KeyListener {
    public static void main(String[] args) {
        JFrame wd = new JFrame();
        wd.setTitle("CHAT TEST");
        wd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        wd.add(new Chat("victinho"));
        wd.pack();
        wd.setResizable(false);
        wd.setAlwaysOnTop(true);
        wd.setVisible(true);
    }

    public JPanel msgpanel;
    public JTextField input;
    public JButton sendMsgBtn;
    public JPanel iptpanel;

    public Chat (String nickname) {
        msgpanel = new JPanel();
        sendMsgBtn = new JButton();
        input = new JTextField();
        iptpanel = new JPanel();

        msgpanel.setBackground(Color.ORANGE);
        msgpanel.setPreferredSize(new Dimension(300, 400));

        JLabel messageLabel = new JLabel("teste");
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setBackground(new Color(255, 200, 0));
        msgpanel.add(messageLabel);

        input = new JTextField();
        input.addKeyListener(this);
        input.setPreferredSize(new Dimension(200, 32));
        input.setBackground(new Color(200, 150, 10));
        input.setBorder(BorderFactory.createEmptyBorder());
        input.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        input.setForeground(Color.WHITE);

        sendMsgBtn.addActionListener(this);
        sendMsgBtn.setText("enviar");

        iptpanel.add(input);
        iptpanel.add(sendMsgBtn);
        iptpanel.setBackground(new Color(150, 100, 10));

        add(msgpanel);
        add(iptpanel);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void appendMsgOnPanel(String msg)
    {
        JLabel messageLabel = new JLabel(msg);
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setBackground(new Color(250, 150, 0));
        msgpanel.add(messageLabel);
        msgpanel.repaint();
        repaint();
    }

    public void sendMsg()
    {
        appendMsgOnPanel(input.getText());
        input.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!input.getText().equals("") && KeyEvent.getKeyText(e.getKeyCode()).equals("Enter"))
            sendMsg();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sendMsg();
    }   
}