package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginStressTest {
    public static void main(String[] args) {
            
        Executor e = Executors.newFixedThreadPool(10000);

        for (int i = 0; i < 10000; i++)
        e.execute(new SimulateLogin(i));

    }
}
class SimulateLogin implements Runnable
{
    public int id;

    public SimulateLogin (int id) { this.id = id; }

    @Override 
    public void run()
    {
        try  {

            Socket s = new Socket("localhost", 5000);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            
            System.out.println(in.readUTF());
            out.writeUTF(String.format("login,user-%d", id));
            System.out.println();
            
            //try {Thread.sleep(10000);} catch (Exception e) {}
            
            out.writeUTF("logout");
            System.out.println();
            s.close();

        } catch (IOException e) {}
    }
}
