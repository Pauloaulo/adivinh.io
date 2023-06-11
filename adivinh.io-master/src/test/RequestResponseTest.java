package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import server.protocol.Protocol;

public class RequestResponseTest {
    public static void main(String[] args) throws IOException
    {
        try {
            Socket socket = new Socket("localhost", 5000);
            Semaphore s1 = new Semaphore(0, true);
            Semaphore s2 = new Semaphore(1, true);
            RequestHandlerChat req = new RequestHandlerChat(socket, s1, s2);
            ResponseHandlerChat res = new ResponseHandlerChat(socket, s2, s1);
            
            req.start();
            res.start();

        } catch (IOException e) {e.printStackTrace();}
    }
}

class RequestHandler extends Thread {
    public Socket socket;
    public Semaphore a;
    public Semaphore b;

    public RequestHandler (Socket socket, Semaphore a, Semaphore b)
    {
        this.socket = socket;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run () {
        try {
            Scanner scan = new Scanner(System.in);
            String request = new String();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            while (!request.equals(Protocol.LOGOUT_STRING))
            {
                a.acquire();
                System.out.print("RESQUEST: ");
                request = scan.nextLine();
                out.writeUTF(request);
                b.release();
            }

            socket.close();
            scan.close();
        } catch (Exception e) {e.printStackTrace();}
    }
}

class ResponseHandler extends Thread {
    public Socket socket;
    public Semaphore a; 
    public Semaphore b;
    
    public ResponseHandler (Socket socket, Semaphore a, Semaphore b)
    {
        this.socket = socket;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run () {
        try {
            String response = new String();
            DataInputStream in = new DataInputStream(socket.getInputStream());

            while (!socket.isClosed())
            {
                a.acquire();
                response = in.readUTF();
                System.out.println("SERVER RESPONSE:");
                System.out.println(response);
                b.release();
            }

        } catch (Exception e) {}
    }

}