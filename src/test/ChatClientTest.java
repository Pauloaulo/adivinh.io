package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientTest {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8080);
            new InThread(s).start();
            
            new OutThread(s).start();
        } catch (Exception e) {}
    }
}

class InThread extends Thread {
    public Socket s;

    public InThread (Socket s)
    {
        this.s = s;
    }
    
    public void run ()
    {
        try 
        {
            DataInputStream in = new DataInputStream(s.getInputStream());

            while (!s.isClosed())
                System.out.println(in.readUTF());

        } catch (Exception e) {}

    }
}

class OutThread extends Thread {
    public Socket s;
    public OutThread (Socket s)
    {
        this.s = s;
    }
    
    public void run ()
    {
        try
        {
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            Scanner scan = new Scanner(System.in);

            while (!s.isClosed()) out.writeUTF(scan.nextLine());
            
            scan.close();

        } catch (Exception e) { }
    }
}
