package server.protocol;

import server.room.Player;

public class ExitRoomProtocol implements Protocol
{
    public static String process (String[] request, Player player)
    {
        if (player.getRoom() == null)
            return ROOM_NOT_EXISTS_STRING;

        player.quitRoom();
        return SUCESSFULL_STRING;
    }
}
