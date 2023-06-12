package server.room.screen;

import java.util.LinkedList;

import server.room.User;

public class Screen 
{
    public LinkedList<User> users;
    
    public Screen (LinkedList<User> users, int port)
    {
        this.users = users;
    }
}
