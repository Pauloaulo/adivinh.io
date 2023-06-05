package server.protocol;

import server.Server;
import server.room.Player;

public class CreateRoomProtocol implements Protocol {
    public static String process (String[] request, Server server, Player p)
    {
        if (p.getRoom() != null)
            return ALREADY_IN_A_ROOM_STRING;
        if (request.length != 2)
            return FORBBIDEN_REQUEST_STRING;

        String roomName = request[1];
        server.addRoom(roomName, p);
        String response = String.format("%s,%s", SUCESSFULL_STRING, p.getRoom().getInfo());
        return response;
    }
}
