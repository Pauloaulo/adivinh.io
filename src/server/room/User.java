package server.room;
import server.handlers.ChatConnectionHandler;

import java.net.Socket;
public class User
{
    private String name;
    private Socket serverSocket;
    private ChatConnectionHandler ch;
    private Socket chatSocket;
    private Socket paintingSocket;
    private Room room;
    
    public User(String nickname, Socket socket)
    {
        this.room = null;
        this.name = nickname;
        this.serverSocket = socket;
    }

    public void setChatSocket (Socket s)
    {
        this.chatSocket = s;
    }
    
    public void setPaintingSocket (Socket s)
    {
        this.paintingSocket = s;
    }
    
    public Socket getChatSocket ()
    {
        return chatSocket;
    }

    public Socket getPaintingSocket ()
    {
        return paintingSocket;
    }

    public Socket getServerSocket ()
    {
        return serverSocket;
    }

    public Room getRoom ()
    {
        return room;
    }

    public boolean setRoom (Room r) {
        if (r != null)
        {
            this.room = r;
        }
        return room != null;
    }

    public void setCh (ChatConnectionHandler c) {
        if (c != null) this.ch = c;
    }
    
    public boolean quitRoom ()
    {
        Room prevRoom = room;
        if (room != null)
            room.quitUser(this);
        room = null;
        return (prevRoom != room);
    }

    public String getNickname() 
    {
        return name;
    }
    
    public boolean setName(String name) 
    {
        String prevName = name;
        this.name = name;
        return prevName != name;
    }
}