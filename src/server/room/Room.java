package server.room;

import java.util.LinkedList;

import server.Chat;
import server.Server;
import server.game.Game;

public class Room implements Runnable
{
    private int id;
    private LinkedList<Player> players;
    private Server server;
    private String name;
    private String category;
    private Game game;

    public Room (Server server, int id, String name, int capacity, String category)
    {
        this.id = id;
        this.name = name;
        this.server = server;
        this.players = new LinkedList<Player>();
        this.category = category;
        Chat.newChat(id);
    }

    public int getId () { return id; }
    public int getAmoutOfPlayers () { return players.size(); }
    public String getName () { return name; }
    public String getInfo()
    {
        String data = String.format("size %d,chat %s:%d,painting %s:%d", players.size(), "localhost", 0, "localhost", 0);
        return data;
    }
    public String getCategory() {
        return category;
    }

    public void recivePlayer (Player p)
    {
        synchronized (players) {
            players.add(p);
            System.out.println(String.format("%s: %s entrou!",name,p.getNickname()));
        }
    }

    public void quitPlayer (Player p)
    {
        synchronized (players) {
            players.remove(p);
            System.out.println(String.format("%s: %s saiu T-T", name, p.getNickname()));
        }
    }

    @Override
    public void run()
    {
        while (players.size() > 0) {
            System.out.println(String.format("sala %s está rodando!\njogadores %d", name, players.size()));
            try { Thread.sleep(2000); } catch (Exception e) {}
        }

        System.out.println("sala " + name + " encerrada");
        Chat.endChat(id);
        server.removeRoom(this.id);
    }
}