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
import server.room.User;

public class ComunicationHandler implements Protocol 
{
    public ComunicationHandler(Server server, User user)
    {
        try
        {
            DataInputStream in = new DataInputStream(user.getServerSocket().getInputStream());
            DataOutputStream out = new DataOutputStream(user.getServerSocket().getOutputStream());
            String[] request = null;
            String response = null;
    
            System.out.println(String.format("%s has been connected", user.getNickname()));
    
            while (!user.getServerSocket().isClosed())
            {
                request = in.readUTF().split(",");
    
                switch (request[0])
                {
                    case GET_ROOMS_STRING:
                        response = GetRoomsProtocol.process(request, user);
                        out.writeUTF(response);
                        break; 
                    case ENTER_ROOM_STRING:
                        response = EnterRoomProtocol.process(request, user);
                        out.writeUTF(response);
                        break;
                    case CREATE_ROOM_STRING:
                        response = CreateRoomProtocol.process(request, user);
                        out.writeUTF(response);
                        break;
                    case EXIT_ROOM_STRING:
                        response = ExitRoomProtocol.process(request, user);
                        out.writeUTF(response);
                        break;
                    case LOGOUT_STRING:
                        response = LogoutProtocol.process(request, user);
                        out.writeUTF(response);
                        user.getServerSocket().close();
                        break;
                    default:
                        out.writeUTF(FORBBIDEN_REQUEST_STRING);     
                        break;
                }
            }
            
        } catch (IOException e) {
            user.quitRoom();
            Server.removeUser(user);
        }

        System.out.println(String.format("%s has been disconnected", user.getNickname()));
    }
}
