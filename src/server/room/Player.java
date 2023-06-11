package server.room;
import java.net.Socket;
public class Player
{
    private int points;
    private String name;
    private Socket serverSocket;
    private Socket chatSocket;
    private Socket paintingSocket;
    private Room room;
    
    public Player (String nickname, Socket socket)
    {
        this.room = null;
        this.name = nickname;
        this.points = 0;
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
    
    public boolean quitRoom ()
    {
        Room prevRoom = room;
        if (room != null)
            room.quitPlayer(this);
        room = null;
        return (prevRoom != room);
    }

    public boolean incrementPoints(int points)
    {
        int prevPoints = points;
        this.points += points;
        return prevPoints < points;
    }

    public int getPoints()
    {
        return points;
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