package server.handlers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import server.Chat;
import server.protocol.Protocol;

public class ChatConnectionHandler implements Runnable
{
    private int roomId;
    private String nickcname;
    private Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    
    public ChatConnectionHandler (Chat c, Socket s) { socket = s; }

    @Override
    public void run()
    {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String[] request = in.readUTF().split(",");
            
            if (request.length == 3) {
                roomId = Integer.parseInt(request[1]);
                nickcname = request[2];
                
                if (request[0].equals(Protocol.JOIN_CHAT_STRING)) {
    
                    Chat.join(roomId, this);
                    out.writeUTF(Protocol.CHAT_JOINED_SUCESSFULLY);

                    // Começa o loop se comunicando
                    while (!socket.isClosed()) {
                        Chat.broadcast(roomId, String.format("%s: %s", nickcname, in.readUTF()));
                    }
                }
            } else {
                out.writeUTF(Protocol.FORBBIDEN_REQUEST_STRING);
            } 
        } catch (Exception e) {}
    }

    public void reciveMessage (String msg) 
    {
        synchronized (out) {
            try { out.writeUTF(msg); } catch (Exception e) {}
        }
    }

    public String getNickname ()
    {
        return nickcname;
    }
}