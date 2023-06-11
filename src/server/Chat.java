package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import server.handlers.ChatConnectionHandler;

public class Chat extends Thread
{
    private static Hashtable<Integer, ArrayList<ChatConnectionHandler>> chats = new Hashtable<Integer, ArrayList<ChatConnectionHandler>>();
    private static ServerSocket server;
    
    @Override
    public void run ()
    {
        try {
            Executor e = Executors.newCachedThreadPool();
            server = new ServerSocket(3000);

            while (!server.isClosed())
                e.execute(new ChatConnectionHandler(this, server.accept()));
        
        } catch (IOException e ) {}
    }

    public static void broadcast (int roomId, String msg)
    {
        synchronized (chats.get(roomId)) {
            for (ChatConnectionHandler cHandler : chats.get(roomId)) {
                if (cHandler != null) cHandler.reciveMessage(msg);
            }
        }
    }

    public static void endChat (int roomId)
    {
        synchronized (chats.get(roomId)) {
            if (chats.get(roomId) != null) {
                chats.get(roomId).remove(roomId);
            }
        }
    }

    public static void join (int roomId, ChatConnectionHandler h)
    {
        synchronized (chats) {
            if (chats.get(roomId) != null)
            {
                chats.get(roomId).add(h);
                broadcast(roomId, h.getNickname() + " entrou!");
            }
        }
    }

    public static void newChat (int id)
    {
        synchronized (chats) {    
            chats.put(id, new ArrayList<ChatConnectionHandler>());   
        }
    }
}