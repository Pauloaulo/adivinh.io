package server.protocol;

import server.Server;
import server.room.User;

public class LogoutProtocol implements Protocol {
    
    public static String process (String[] request, User user)
    {
        user.quitRoom();
        Server.removeUser(user);
        return SUCESSFULL_STRING;        
    }
}
