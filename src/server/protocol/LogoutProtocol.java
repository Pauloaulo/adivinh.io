package server.protocol;

import server.Server;
import server.room.Player;

public class LogoutProtocol implements Protocol {
    
    public static String process (String[] request, Player player)
    {
        player.quitRoom();
        Server.removePlayer(player);
        return SUCESSFULL_STRING;        
    }
}
