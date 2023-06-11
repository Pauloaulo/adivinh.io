package server.protocol;

import server.Server;
import server.room.Player;

public class LogoutProtocol implements Protocol {
    
    public static String process (String[] request, Server server, Player player)
    {
        player.quitRoom();
        server.removePlayer(player);
        return SUCESSFULL_STRING;        
    }
}
