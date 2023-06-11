package server.room;

import java.util.LinkedList;

import server.Chat;
import server.Server;
// import server.game.Game;

public class Room implements Runnable
{
    private int id;
    private LinkedList<Player> players;
    private String name;
    private String category;
    // private Game game;

    public Room (int id, String name, int capacity, String category)
    {
        this.id = id;
        this.name = name;
        this.players = new LinkedList<Player>();
        this.category = category;
        Chat.newChat(id);
    }

    public int getId () { return id; }
    public int getAmoutOfPlayers () { return players.size(); }
    public String getName () { return name; }
    public String getInfo()
    {
        String data = String.format("%d,%d,%s:%d,%s:%d", id, players.size(), "localhost", 0, "localhost", 0);
        return data;
    }
    public String getCategory() {
        return category;
    }

    public void recivePlayer (Player p)
    {
        synchronized (players) {
            players.add(p);
            p.setRoom(this);
            System.out.println(String.format("%s: %s entrou!",name,p.getNickname()));
            players.notifyAll();
        }
    }

    public void quitPlayer (Player p)
    {
        synchronized (players) {
            players.remove(p);
            p.setChatSocket(null);
            players.notifyAll();
        }
    }

    @Override
    public void run()
    {
        while (players.size() > 0) {
            // System.out.println(String.format("sala %s está rodando!\njogadores %d", name, players.size()));
            try { Thread.sleep(5000); } catch (Exception e) {}
        }

        System.out.println("sala " + name + " encerrada");
        Chat.endChat(id);
        Server.removeRoom(this.id);
    }
}