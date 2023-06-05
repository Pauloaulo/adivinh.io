package server.protocol;

import server.Server;
import server.room.Player;

public class ExitRoomProtocol implements Protocol
{
    public static String process (String[] request, Server server, Player player)
    {
        if (player.getRoom() == null)
            return ROOM_NOT_EXISTS_STRING;

        player.quitRoom();
        return SUCESSFULL_STRING;
    }
}
