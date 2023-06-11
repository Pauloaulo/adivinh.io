package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import server.game.DataBase;
import server.handlers.LoginHandler;
import server.room.Player;
import server.room.Room;

public class Server extends Thread 
{
    public static void main(String[] args) {
        Server server = new Server(5000);
        server.start();
    }
    
    private Hashtable<Integer, Room> roomMap;
    private Hashtable<String, Player> playersMap;
    private DataBase data;
    private Executor roomPool;
    private Executor clientPool;
    private String roomList;
    private int amountOfCreatedRooms;
    private int amountOfConnectedPlayers;
    private int port;

    public Server(int port) {
        this.port = port;
        amountOfCreatedRooms = 0;
        amountOfConnectedPlayers = 0;
        roomList = new String();
        roomMap = new Hashtable<Integer, Room>();
        playersMap = new Hashtable<String, Player>();
        data = new DataBase();
        roomPool = Executors.newCachedThreadPool();
        clientPool = Executors.newCachedThreadPool();
        new Chat().start();
    }

    @Override
    public void run()
    {
        try
        {
            ServerSocket server = new ServerSocket(port);

            while (!server.isClosed()) 
                clientPool.execute(new LoginHandler(this, server.accept()));

            server.close();

        } catch (IOException e) { e.printStackTrace(); }

    }
    
    public int getAmountOfConnectedPlayers() {
        return amountOfConnectedPlayers;
    }

    public int getAmountOfCreatedRooms() 
    {
        return amountOfCreatedRooms;
    }

    public Player getPlayer(String name) 
    {
        return playersMap.get(name);
    }

    public Room getRoom (int id)
    {
        return roomMap.get(id);
    }

    public Hashtable<Integer, Room> getRoomMap() 
    {
        return roomMap;
    }
    public HashMap<String, HashSet<String>> getCategoryData(String category) {
        HashMap<String, HashMap<String, HashSet<String>>> newData = data.getData();
        HashMap<String, HashSet<String>> categoryData = newData.get(category);
        return categoryData;
    }

    public synchronized void updateRoomList (String roomList)
    {
        this.roomList = roomList;
    }

    public String getRoomList()
    {
        return roomList;
    }

    public synchronized void addPlayer(Player player)
    {
        playersMap.put(player.getNickname(), player);
        amountOfConnectedPlayers++;
    }

    public synchronized void addRoom(String name, Player host, String category)
    {
        Room room = new Room(this, ++amountOfCreatedRooms, name, 10, category);
        roomMap.put(room.getId(), room);
        host.enterRoom(room);
        roomPool.execute(room);
    }

    public synchronized boolean removePlayer (Player player)
    {
        try {
            player.getSocket().close();
            playersMap.remove(player.getNickname());
            amountOfConnectedPlayers--;
        } catch (IOException e) { return false; }

        return true;
    }

    public synchronized void removeRoom(int id) 
    {
        roomMap.remove(id);
        if (roomMap.size() == 0) amountOfCreatedRooms = 0;
    }
}
