package GUI;

import GUI.login.LoginFrame;
import server.protocol.Protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Control {
    private String response = "";
    private String request = "";

    Object lock1 = new Object();
    Object lock2 = new Object();

    public Control(DataInputStream in, DataOutputStream out)
    {
        (new InBuf(in)).start();
        (new OutBuf(out)).start();
    }

    public void setRequest(String request) {
        try {
            synchronized (lock1) {
                while (!OutBuf.waiting) {
                    lock1.wait();
                }
                this.request = request;

                lock1.notify();             //notifica outbuf para enviar msg
                OutBuf.waiting = false;
            }
        } catch (Exception ignored)  { System.out.println("request_erro"); }
    }

    /* Metodo esta gerando DeadLock,
    * não pode ser chamado antes de setRequest*/
    public String getResponse() {
        try {
            synchronized (lock2) { lock2.wait(); }
        } catch (InterruptedException e) { e.printStackTrace(); }
        return response;
    }

    //Inner class
    private class InBuf extends Thread {
        DataInputStream in;

        public InBuf(DataInputStream in) {
            this.in = in;
        }

        @Override
        public void run ()
        {
            try {

                while (!response.equals(Protocol.LOGOUT_STRING))
                {
                    response = in.readUTF();

                    synchronized (lock2) { lock2.notify(); }
                }
                in.close();

            } catch (IOException e) {}
        }
    }

    //Inner class
    private class OutBuf extends Thread {
        DataOutputStream out;

        static boolean waiting = false;

        public OutBuf(DataOutputStream out) {
            this.out = out;
        }

        @Override
        public void run ()
        {
            try {

                while (!request.equals(Protocol.LOGOUT_STRING))
                {
                    synchronized (lock1) {
                        waiting = true; lock1.notify(); //notifica que esta esperando

                        lock1.wait();
                        out.writeUTF(request);
                    }
                }
                out.close();
            } catch (Exception e) { System.out.println("algum erro de merda"); }
        }
    }


    public static void main(String[] args)
    {
        Control control;
        try
        {
            Socket socket = new Socket("localhost", 5000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            control = new Control(in, out);
            new LoginFrame(null,control);
        } catch (Exception e) { /*connection erro frame*/ }
    }

}

