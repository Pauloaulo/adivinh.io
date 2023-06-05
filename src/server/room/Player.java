package server.room;

import java.net.Socket;

public class Player
{
    private String name;
    private int points;
    private Socket socket;
    private Room room;
    
    public Player (String name, Socket socket)
    {
        this.room = null;
        this.name = name;
        this.points = 0;
        this.socket = socket;
    }

    public Socket getSocket ()
    {
        return socket;
    }

    public Room getRoom ()
    {
        return room;
    }

    public void enterRoom (Room r)
    {
        if (this.room == null) {
            r.recivePlayer(this);
            this.room = r;
        }
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

    public String getName() 
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
