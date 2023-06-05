package server.handlers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import server.Server;
import server.protocol.Protocol;
import server.room.Player;

public class LoginHandler implements Runnable, Protocol {
    private Socket socket;
    private Server server;

    public LoginHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String[] request = null;

            out.writeUTF(CONNECTION_SUCESSFULL_STRING);
            
            while (!socket.isClosed())
            {
                request = in.readUTF().split(",");

                if (request.length != 2 || !request[0].equals(LOGIN_STRING) || request[1].equals(null) || request[1].equals(""))
                    out.writeUTF(FORBBIDEN_REQUEST_STRING);
                
                else if (server.getPlayer(request[1]) != null)
                    out.writeUTF(ACCOUNT_ALREADY_EXIST_STRING);

                else {
                    Player p = new Player(request[1], socket);
                    server.addPlayer(p);
                    out.writeUTF(SUCESSFULL_STRING);

                    new ComunicateHandler(server, p);
                    
                    if (p != null) p.getSocket().close();
                    p = null;
                }
            }
        } catch (IOException e) { }
    }
}
