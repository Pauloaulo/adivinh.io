package server.handlers;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import server.Chat;
import server.Server;
import server.protocol.Protocol;
import server.room.User;

public class ChatConnectionHandler implements Runnable
{
    public User boundUser;
    public String message;
    public Socket socket;
    
    public ChatConnectionHandler (Socket s)
    {
        this.socket = s;
        this.message = "";
    }

    @Override
    public void run ()
    {
        try {

            /** VINCULANDO USUÁRIO AO SOCKET **/
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String[] login = in.readUTF().split(",");

            // primeira reqisição do cliente precisa ser "join-chat,idSala,nickname"
            if (login.length == 3 && login[0].equals(Protocol.JOIN_CHAT_STRING)) {

                Integer roomId = null;

                try { roomId = Integer.parseInt(login[1]); } catch (Exception e) { }
                
                String nickname = login[2];

                if (roomId != null ) {

                    boundUser = Server.getUser(nickname);
                    // System.out.println("bound player nick? " + boundPlayer.getNickname());
                
                    if (boundUser != null) {
                        boundUser.setChatSocket(socket);
                        boundUser.setCh(this);

                        // System.out.println("bound player socket exist? " + (boundPlayer.getChatSocket() != null));
                        Chat.join(roomId, this);
                        startChating();
                    }
                }
            }
        } catch (Exception e) { }
    }

    private void startChating ()
    {
        try {
            String msg = new String();
            DataInputStream in = new DataInputStream(boundUser.getChatSocket().getInputStream());

            while (boundUser.getChatSocket() != null && !boundUser.getChatSocket().isClosed()) {
                msg = in.readUTF();
                
                if (msg.startsWith("/quit")) {
                    shutdown();
                    break;
                } else {
                    msg = String.format("%s: %s", boundUser.getNickname(), msg);
                    message = msg;
                    Chat.broadcast(boundUser.getRoom().getId(), msg);
                }
            }

            
        } catch (Exception e ) {
            try {socket.close();} catch (Exception e2) { }
        }
        shutdown();
    }

    public void shutdown ()
    {
        if (boundUser != null) {
            Chat.quit(boundUser.getRoom().getId(), this);
            try {socket.close();} catch (IOException e ) {}
        }
    }

    public void receiveMessage(String msg)
    {
        try {
            DataOutputStream out = new DataOutputStream(boundUser.getChatSocket().getOutputStream());
            out.writeUTF(msg);
            out = null;
        } catch (Exception e ) { shutdown(); }
    }

    public User getBoundPlayer ()
    {
        return boundUser;
    }

    public boolean hasBoundPlayer ()
    {
        return boundUser != null;
    }
}