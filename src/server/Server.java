package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import server.handlers.LoginHandler;
import server.room.User;
import server.room.Room;

public class Server extends Thread 
{
    public static void main(String[] args) {
        Server server = new Server(5000);
        server.start();
    }
    
    private static Hashtable<Integer, Room> roomMap;
    private static Hashtable<String, User> usersMap;
    private static Executor roomPool;
    private static Executor clientPool;
    private static String roomListString;
    private static int amountOfCreatedRooms;
    private static int amountOfConnectedUsers;
    private static int port;

    public Server(int port) {
        Server.port = port;
        amountOfCreatedRooms = 0;
        amountOfConnectedUsers = 0;
        roomListString = new String();
        roomMap = new Hashtable<Integer, Room>();
        usersMap = new Hashtable<String, User>();
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
    
    public static int getAmountOfConnectedUsers() {
        return amountOfConnectedUsers;
    }

    public static int getAmountOfCreatedRooms() 
    {
        return amountOfCreatedRooms;
    }

    public static User getUser(String name)
    {
        return usersMap.get(name);
    }

    public static Room getRoom (int id)
    {
        return roomMap.get(id);
    }

    public static Hashtable<Integer, Room> getRoomMap() 
    {
        return roomMap;
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

    public static void addUser(User user)
    {
        synchronized (usersMap) {
            usersMap.put(user.getNickname(), user);
            amountOfConnectedUsers++;
            usersMap.notifyAll();
        }
    }

    public static void addRoom(String name, User host, String category)
    {
        synchronized (roomMap) {
            int roomId = ++amountOfCreatedRooms;

            Room room = new Room(roomId, name, 100, category);
            
            roomMap.put(room.getId(), room);
            room.receiveUser(host);
            roomMap.notifyAll();
            roomPool.execute(room);
        }
    }

    public static boolean removeUser (User user)
    {
        try {
            user.getServerSocket().close();

            synchronized (usersMap) {
                usersMap.remove(user.getNickname());
                amountOfConnectedUsers--;
                usersMap.notifyAll();
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
