package server.room.screen;

import java.util.LinkedList;

import server.room.Player;

public class Screen 
{
    public LinkedList<Player> players;
    
    public Screen (LinkedList<Player> players, int port)
    {
        this.players = players;
    }
}
