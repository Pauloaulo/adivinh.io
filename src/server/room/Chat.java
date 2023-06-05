package server.room;

import java.util.LinkedList;

public class Chat extends Thread
{
    public LinkedList<Player> players;
    protected int port;

    public Chat (LinkedList<Player> players, int port)
    {
        this.players = players;
        this.port = port;
    }
}
