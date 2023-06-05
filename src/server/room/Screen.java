package server.room;

import java.util.LinkedList;

public class Screen 
{
    public LinkedList<Player> players;
    
    public Screen (LinkedList<Player> players, int port)
    {
        this.players = players;
    }
}
