package server.room;

import java.util.LinkedList;

import server.Chat;
import server.Server;
// import server.game.Game;

public class Room implements Runnable
{
    private int id;
    private LinkedList<User> users;
    private String name;
    private String category;
    public Room (int id, String name, int capacity, String category)
    {
        this.id = id;
        this.name = name;
        this.users = new LinkedList<User>();
        this.category = category;
        Chat.newChat(id);
    }

    public int getId () { return id; }
    public int getAmountOfUsers() { return users.size(); }
    public String getName () { return name; }
    public String getInfo()
    {
        String data = String.format("%d,%d,%s:%d,%s:%d", id, users.size(), "localhost", 0, "localhost", 0);
        return data;
    }
    public String getCategory() {
        return category;
    }

    public void receiveUser(User p)
    {
        synchronized (users) {
            users.add(p);
            p.setRoom(this);
            System.out.println(String.format("%s: %s entrou!",name,p.getNickname()));
            users.notifyAll();
        }
    }

    public void quitUser(User p)
    {
        synchronized (users) {
            users.remove(p);
            p.setChatSocket(null);
            users.notifyAll();
        }
    }

    @Override
    public void run()
    {
        while (users.size() > 0) {
            // System.out.println(String.format("sala %s está rodando!\njogadores %d", name, players.size()));
            try { Thread.sleep(5000); } catch (Exception e) {}
        }

        System.out.println("sala " + name + " encerrada");
        Chat.endChat(id);
        Server.removeRoom(this.id);
    }
}