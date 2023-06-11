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
    
    private static Hashtable<Integer, Room> roomMap;
    private static Hashtable<String, Player> playersMap;
    private static DataBase data;
    private static Executor roomPool;
    private static Executor clientPool;
    private static String roomListString;
    private static int amountOfCreatedRooms;
    private static int amountOfConnectedPlayers;
    private static int port;

    public Server(int port) {
        Server.port = port;
        amountOfCreatedRooms = 0;
        amountOfConnectedPlayers = 0;
        roomListString = new String();
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
    
    public static int getAmountOfConnectedPlayers() {
        return amountOfConnectedPlayers;
    }

    public static int getAmountOfCreatedRooms() 
    {
        return amountOfCreatedRooms;
    }

    public static Player getPlayer(String name) 
    {
        return playersMap.get(name);
    }

    public static Room getRoom (int id)
    {
        return roomMap.get(id);
    }

    public static Hashtable<Integer, Room> getRoomMap() 
    {
        return roomMap;
    }

    public static HashMap<String, HashSet<String>> getCategoryData(String category) {
        HashMap<String, HashMap<String, HashSet<String>>> newData = data.getData();
        HashMap<String, HashSet<String>> categoryData = newData.get(category);
        return categoryData;
    }

    public static void updateRoomListString (String roomList)
    {
        synchronized (roomList) {
            Server.roomListString = roomList;
            roomList.notifyAll();
        }
    }

    public static String getRoomListString()
    {
        return roomListString;
    }

    public static void addPlayer(Player player)
    {
        synchronized (playersMap) {
            playersMap.put(player.getNickname(), player);
            amountOfConnectedPlayers++;
            playersMap.notifyAll();
        }
    }

    public static void addRoom(String name, Player host, String category)
    {
        synchronized (roomMap) {
            int roomId = ++amountOfCreatedRooms;

            Room room = new Room(roomId, name, 100, category);
            
            roomMap.put(room.getId(), room);
            room.recivePlayer(host);
            roomMap.notifyAll();
            roomPool.execute(room);
        }
    }

    public static boolean removePlayer (Player player)
    {
        try {
            player.getServerSocket().close();

            synchronized (playersMap) {
                playersMap.remove(player.getNickname());
                amountOfConnectedPlayers--;
                playersMap.notifyAll();
            }

        } catch (IOException e) { return false; }

        return true;
    }

    public static void removeRoom(int id) 
    {
        synchronized (roomMap) {
            roomMap.remove(id);
            if (roomMap.size() == 0) amountOfCreatedRooms = 0;
            roomMap.notifyAll();
        }
    }
}
