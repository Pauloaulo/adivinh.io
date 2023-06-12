package client.views.room;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.protocol.Protocol;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Chat extends JPanel implements ActionListener, KeyListener {

    public int roomId;
    public Socket socket;
    public DataInputStream in;
    public DataOutputStream out;
    public String nickname;
    public JTextArea messagesArea;
    public JPanel inputPanel;
    public JTextField input;
    public JButton sendButton;
    public JScrollPane msgScroll;

    public Chat (int roomId, String nickname)
    {
        this.roomId = roomId;
        this.nickname = nickname;

        sendButton = new JButton();
        input = new JTextField();
        inputPanel = new JPanel();
        messagesArea = new JTextArea();
        msgScroll = new JScrollPane(messagesArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        messagesArea.setEditable(false);
        messagesArea.setForeground(Color.WHITE);
        messagesArea.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        messagesArea.setBackground(new Color(0x801A80));
        messagesArea.setLineWrap(true);

        msgScroll.setPreferredSize(new Dimension(280, 400));
        //msgScroll.add(messagesArea);

        input = new JTextField();
        input.addKeyListener(this);
        input.setPreferredSize(new Dimension(300, 32));
        input.setBackground(Color.WHITE);
        input.setBorder(BorderFactory.createEmptyBorder());
        input.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        input.setForeground(Color.BLACK);

        sendButton.addActionListener(this);
        sendButton.setText("enviar");

        inputPanel.add(input);
        inputPanel.add(sendButton);
        inputPanel.setBackground(new Color(0x601A80));

        add(msgScroll);
        add(inputPanel);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        connect();
    }

    public void reciveMsg(String msg)
    {
        messagesArea.append("\n"+msg);
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
        {
            sendMessage(input.getText());
            input.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!input.getText().equals(""))
        {
            sendMessage(input.getText());
            input.setText("");
        }
    }

    public void connect ()
    {
        try {
            socket = new Socket("localhost", 3000);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            
            // logando no chat
            out.writeUTF(String.format("%s,%d,%s", Protocol.JOIN_CHAT_STRING, roomId, nickname));
            System.out.println("logou?: " + in.readUTF());

            new MSGPrinter(socket).start();

        } catch (Exception e) { }
    }
    public void sendMessage (String msg)
    {
        try {
            out.writeUTF(msg);
            if (msg.startsWith("/quit")) socket.close();
        } catch (Exception e) { }
    }

    class MSGPrinter extends Thread {
        public Socket s;
        public MSGPrinter (Socket s) { this.s = s; }
        public void run ()
        {
            try {
                DataInputStream in = new DataInputStream(s.getInputStream());
                String msg = new String();

                while (!s.isClosed())
                {
                    msg = in.readUTF();
                    reciveMsg(msg);
                }

            } catch (Exception e ) {}
        }
    }
}