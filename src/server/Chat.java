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
            System.out.println("chat rodando! porta 3000");
            while (!server.isClosed())
                e.execute(new ChatConnectionHandler(this, server.accept()));
        
        } catch (IOException e ) {}
    }

    public static void broadcast (int roomId, String msg)
    {
        ArrayList<ChatConnectionHandler> chatGroup = chats.get(roomId);
        
        synchronized (chatGroup) {
            for (ChatConnectionHandler client : chatGroup) {
                if (client != null) client.reciveMessage(msg);
            }
            chatGroup.notifyAll();
        }
    }

    public static void endChat (int roomId)
    {
        ArrayList<ChatConnectionHandler> chatGroup = chats.get(roomId);
        synchronized (chatGroup) {
            if (chatGroup != null) {
                chats.remove(roomId);
            }
        }
    }

    public static void quit (int roomId,ChatConnectionHandler ch )
    {
        ArrayList<ChatConnectionHandler> chatGroup = chats.get(roomId);
        synchronized (chatGroup) {
            chatGroup.remove(ch);
        }
    }

    public static void join (int roomId, ChatConnectionHandler connectHandler)
    {
        ArrayList<ChatConnectionHandler> chatGroup = chats.get(roomId);
        
        if (chatGroup != null)
        {
            synchronized (chatGroup) {
                chatGroup.add(connectHandler);
            }

            broadcast(roomId, connectHandler.getNickname() + " entrou!");
        }
    }

    public static void newChat (int id)
    {
        synchronized (chats) {    
            chats.put(id, new ArrayList<ChatConnectionHandler>());
        }
    }
}