package server.protocol;

import java.util.Hashtable;
import java.util.Map;
import server.Server;
import server.room.Player;
import server.room.Room;

public class GetRoomsProtocol implements Protocol
{
    public static String process (String[] request, Player player)
    {
        Hashtable<Integer, Room> roomMap = Server.getRoomMap();
        String response = new String();

        for (Map.Entry<Integer, Room> room : roomMap.entrySet())
        {
            int id = room.getValue().getId();
            int size = room.getValue().getAmoutOfPlayers();
            String name = room.getValue().getName();
            response = response.concat(String.format("%d,%s,%d\n", id, name, size));
        }
        
        if ((response.length()-1) >= 0)
        {
            response = response.substring(0, response.length()-1);
        }

        return response;
    }
}
