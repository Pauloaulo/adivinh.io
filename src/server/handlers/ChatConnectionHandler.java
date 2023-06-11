package server.handlers;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import server.Chat;
import server.Server;
import server.protocol.Protocol;
import server.room.Player;

public class ChatConnectionHandler implements Runnable
{
    public Player boundPlayer;
    public Socket socket;
    
    public ChatConnectionHandler (Socket s)
    {
        this.socket = s;
    }

    @Override
    public void run ()
    {
        try {

            /** VINCULANDO JOGADOR AO SOCKET **/
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String[] login = in.readUTF().split(",");

            // primeira reqisição do cliente precisa ser "join-chat,idSala,nickname"
            if (login.length == 3 && login[0].equals(Protocol.JOIN_CHAT_STRING)) {

                Integer roomId = null;

                try { roomId = Integer.parseInt(login[1]); } catch (Exception e) { }
                
                String nickname = login[2];

                if (roomId != null ) {

                    boundPlayer = Server.getPlayer(nickname);
                    System.out.println("bound player nick? " + boundPlayer.getNickname());
                
                    if (boundPlayer != null) {
                        boundPlayer.setChatSocket(socket);

                        System.out.println("bound player socket exist? " + (boundPlayer.getChatSocket() != null));
                        Chat.join(roomId, this);
                        startChating();
                    }
                }
            }
        } catch (Exception e) {
            boundPlayer.setChatSocket(null);
        }
    }

    private void startChating ()
    {
        if (boundPlayer != null) {
            try {
                String msg = new String();
                DataInputStream in = new DataInputStream(boundPlayer.getChatSocket().getInputStream());

                while (!boundPlayer.getChatSocket().isClosed()) {
                    msg = in.readUTF();
                    msg = String.format("%s: %s", boundPlayer.getNickname(), msg);
                    Chat.broadcast(boundPlayer.getRoom().getId(), msg);
                }
                
            } catch (Exception e ) {}
        }
    }

    public void reciveMessage (String msg)
    {
        try {
            DataOutputStream out = new DataOutputStream(boundPlayer.getChatSocket().getOutputStream());
            out.writeUTF(msg);
            out = null;
        } catch (Exception e ){}
    }

    public Player getBoundPlayer ()
    {
        return boundPlayer;
    }
}