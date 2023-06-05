package server.handlers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import server.Server;
import server.protocol.CreateRoomProtocol;
import server.protocol.EnterRoomProtocol;
import server.protocol.ExitRoomProtocol;
import server.protocol.GetRoomsProtocol;
import server.protocol.LogoutProtocol;
import server.protocol.Protocol;
import server.room.Player;

public class ComunicateHandler implements Protocol 
{
    public ComunicateHandler(Server server, Player player)
    {
        try
        {
            DataInputStream in = new DataInputStream(player.getSocket().getInputStream());
            DataOutputStream out = new DataOutputStream(player.getSocket().getOutputStream());
            String[] request = null;
            String response = null;
    
            System.out.println(String.format("%s has been connected", player.getName()));
    
            while (!player.getSocket().isClosed())
            {
                request = in.readUTF().split(",");
    
                switch (request[0])
                {
                    case GET_ROOMS_STRING:
                        response = GetRoomsProtocol.process(request, server, player);
                        out.writeUTF(response);
                        break; 
                    case ENTER_ROOM_STRING:
                        response = EnterRoomProtocol.process(request, server, player);
                        out.writeUTF(response);
                        break;
                    case CREATE_ROOM_STRING:
                        response = CreateRoomProtocol.process(request, server, player);
                        out.writeUTF(response);
                        break;
                    case EXIT_ROOM_STRING:
                        response = ExitRoomProtocol.process(request, server, player);
                        out.writeUTF(response);
                        break;
                    case LOGOUT_STRING:
                        response = LogoutProtocol.process(request, server, player);
                        out.writeUTF(response);
                        player.getSocket().close();
                        break;
                    default:
                        out.writeUTF(FORBBIDEN_REQUEST_STRING);     
                        break;
                }
            }
            
        } catch (IOException e) {
            player.quitRoom();
            server.removePlayer(player);
        }

        System.out.println(String.format("%s has been disconnected", player.getName()));
    }
}
