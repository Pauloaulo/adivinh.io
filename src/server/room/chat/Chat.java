package server.room.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Chat extends Thread
{
    public static void main(String[] args)
    {
        Chat c = new Chat(8080, 10);
        c.start();
    }

    private ArrayList<ChatConnectionHandler> handlers;
    private ServerSocket server;
    private int port;
    private int size;

    public Chat (int port, int size)
    {
        handlers = new ArrayList<ChatConnectionHandler>(size);
        this.port = port;
        this.size = size;
    }

    @Override
    public void run ()
    {
        try
        {
            Executor e = Executors.newFixedThreadPool(size);
            server = new ServerSocket(port);

            while (!server.isClosed())
            {
                ChatConnectionHandler connectionHandler =  new ChatConnectionHandler(this, server.accept());
                handlers.add(connectionHandler);
                e.execute(connectionHandler);
                broadcast("alguem se conectou");
            }

            shutdown();
        } catch (IOException e ) {}
    }

    // encerra o chat
    public void shutdown()
    {
        broadcast("chat se encerrou");
        try { server.close(); } catch (IOException e ) {}
    }

    // Envia mensagem para todos os sockets conectados
    public synchronized void broadcast (String msg) 
    {
        for (ChatConnectionHandler c : handlers)
            if (c != null) c.reciveMessage(msg);
    }
}