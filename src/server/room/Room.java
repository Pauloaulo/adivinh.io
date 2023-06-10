package server.room;

import java.util.LinkedList;
import server.Server;
import server.room.chat.Chat;
import server.room.screen.Screen;

public class Room implements Runnable
{
    private int chatPort;
    private int drawnerPort;
    private int id;
    private LinkedList<Player> players;
    private Server server;
    private String name;
    protected Chat chat;
    protected Screen drawner;

    public Room (Server server, int id, String name, int capacity)
    {
        this.id = id;
        this.name = name;
        this.server = server;
        this.players = new LinkedList<Player>();
        this.chatPort = 5000 + id;
        this.drawnerPort = 1024 + id;
        this.chat = new Chat(chatPort, capacity);
        this.drawner = new Screen(players, drawnerPort);
    }

    public int getId () { return id; }
    public int getAmoutOfPlayers () { return players.size(); }
    public String getName () { return name; }
    
    public String getInfo()
    {
        String data = String.format("%d,%s,%d,%s,%d", players.size(), "localhost", chatPort, "localhost", drawnerPort);
        return data;
    }

    public synchronized void recivePlayer (Player p)
    {
        players.add(p);
        System.out.println(String.format("%s: %s entrou!",name,p.getNickname()));
    }

    public synchronized void quitPlayer (Player p)
    {
        players.remove(p);
        System.out.println(String.format("%s: %s saiu T-T", name, p.getNickname()));
    }

    @Override
    public void run()
    {
        while (players.size() > 0) {
            System.out.println(String.format("sala %s está rodando!\njogadores %d", name, players.size()));
            try { Thread.sleep(2000); } catch (Exception e) {}
        }

        System.out.println("sala " + name + " encerrada");
        server.removeRoom(this.id);
    }
}