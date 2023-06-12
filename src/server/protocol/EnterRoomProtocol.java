package server.protocol;

import server.Server;
import server.room.User;
import server.room.Room;

public class EnterRoomProtocol implements Protocol 
{
    public static String process (String[] request, User user)
    {
        if (user.getRoom() != null)
            return ALREADY_IN_A_ROOM_STRING;
        if (request.length != 2)
            return FORBBIDEN_REQUEST_STRING;

        int roomId;
        
        try { 
            roomId = Integer.parseInt(request[1]);
        } catch (Exception e) {
            return FORBBIDEN_REQUEST_STRING;
        }
        
        Room room = Server.getRoom(roomId);
        
        if (room == null)
            return ROOM_NOT_EXISTS_STRING;
        
        room.receiveUser(user);

        String response = String.format("%s,%s", JOINED_IN_A_ROOM_STRING, user.getRoom().getInfo());

        // Retorna os dados da sala ao se conectar
        // "(status),(qtd. jogadores),(host chat),(porta chat),(host drawner),(porta drawner)"
        //                            |_________TCP__________| |____________UDP_____________|  
        //                             \    endereço chat   /   \    endereço do drawner   /
        
        return response;
    }
}
