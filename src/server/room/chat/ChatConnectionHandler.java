package server.room.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatConnectionHandler implements Runnable
{
    private Chat chat;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ChatConnectionHandler (Chat c, Socket s)
    {
        socket = s;
        chat = c;
    }

    @Override
    public void run()
    {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            
            out.writeUTF("printo");
            
            while (!socket.isClosed())
                sendMessage(in.readUTF());

        } catch (IOException e) {}
    }
    
    public void reciveMessage (String msg)
    {
        try {out.writeUTF(msg);} catch (Exception e) {}
    }
    
    public void sendMessage (String msg) 
    {
        chat.broadcast(msg);
    }

    public void shutdown ()
    {
        try {socket.close();} catch (Exception e) {}
    }
}
