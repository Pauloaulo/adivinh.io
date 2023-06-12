package server.protocol;

import server.room.User;

public class ExitRoomProtocol implements Protocol
{
    public static String process (String[] request, User user)
    {
        if (user.getRoom() == null)
            return ROOM_NOT_EXISTS_STRING;

        user.quitRoom();
        return SUCESSFULL_STRING;
    }
}
