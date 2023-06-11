package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import server.protocol.Protocol;

public class ChatClientTest {
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

class RequestHandlerChat extends Thread {
    public static String nick = new String();
    public Socket socket;
    public Semaphore a;
    public Semaphore b;

    public RequestHandlerChat(Socket socket, Semaphore a, Semaphore b)
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

                if (request.startsWith(Protocol.LOGIN_STRING) && request.split(",").length > 1)
                {
                    nick = request.split(",")[1];
                }

                out.writeUTF(request);
                b.release();
            }

            socket.close();
            scan.close();
        } catch (Exception e) {e.printStackTrace();}
    }
}

class ResponseHandlerChat extends Thread {
    public Socket socket;
    public Semaphore a; 
    public Semaphore b;
    
    public ResponseHandlerChat(Socket socket, Semaphore a, Semaphore b)
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
                
                if(response.split(",")[0].equals(Protocol.JOINED_IN_A_ROOM_STRING)) {
                    startChating(Integer.parseInt(response.split(",")[1]));
                }

                b.release();
            }

        } catch (Exception e) {}
    }

    public void startChating (int id)
    {
        try {
            Socket s = new Socket("localhost", 3000);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            
            Scanner scan = new Scanner(System.in);
            String msg = new String();

            // logando no chat
            out.writeUTF(String.format("%s,%d,%s", Protocol.JOIN_CHAT_STRING, id, RequestHandlerChat.nick));
            System.out.println(in.readUTF());

            // Thread q ficara printando as mensagens recebidas
            new MSGPrinter(s).start();

            // Loop de envio de mensagens
            while (!s.isClosed())
            {
                msg = scan.nextLine();
                out.writeUTF(msg);
            }

            scan.close();
        } catch (Exception e) { }
    }

    class MSGPrinter extends Thread {
        public Socket s;
        public MSGPrinter (Socket s) { this.s = s; }
        public void run ()
        {
            try {
                DataInputStream in = new DataInputStream(s.getInputStream());
                String msg = new String();

                while (!s.isClosed())
                {
                    msg = in.readUTF();
                    System.out.println(msg);
                }

            } catch (Exception e ) {}
        }
    }
}