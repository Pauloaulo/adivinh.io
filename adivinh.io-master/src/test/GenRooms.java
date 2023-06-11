package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import server.protocol.Protocol;


public class GenRooms {
    public static void main(String[] args) {

        int amount = 100;
        
        Executor e = Executors.newFixedThreadPool(amount);

        for (int i = 0; i < amount; i++)
            e.execute(new ClientTest("Sala gerada " + i));
    }
}

class ClientTest implements Runnable
{
    public String nickname;

    public ClientTest(String nickname)
    {
        this.nickname = nickname;    
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket("localhost", 5000);
            
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            System.out.println(in.readUTF());

            out.writeUTF(String.format("%s,%s",Protocol.LOGIN_STRING, nickname));

            if(in.readUTF().equals(Protocol.SUCESSFULL_STRING))
            {
                out.writeUTF(String.format("%s,%s", Protocol.CREATE_ROOM_STRING, nickname));
                wait();
            }
            
        } catch (Exception e) {
            System.out.println("desconectado!");
        }
    }
}
