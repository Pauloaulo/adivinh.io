package test;

import java.util.ArrayList;
import java.util.Hashtable;

public class MonitorPlayground {
    public static int TEST_AMOUNT = 3;
    public static Hashtable<Integer, ArrayList<Msger>> data = new Hashtable<Integer, ArrayList<Msger>>(TEST_AMOUNT);  

    public static void main(String[] args) {
        MonitorPlayground m = new MonitorPlayground();

        for (int i = 0; i < TEST_AMOUNT; i++)
        {
            data.put(i, new ArrayList<Msger>(TEST_AMOUNT));
            for(int j = 0; j < TEST_AMOUNT; j++)
                data.get(i).add(new Msger());
        }

        for (int i = 0; i < TEST_AMOUNT; i++)
            new Runer(m, "u-"+i).start();
        
        for (int i = 0; i < TEST_AMOUNT; i++)
            new Runer(m, "v-"+i).start();
    }

    public static synchronized void a (String id)
    {
        while (true) {
            System.out.println(id); 
            try {Thread.sleep(2000);} catch (Exception e) {}
        }
    }

    public static void b(String id)
    {
        int idd = Integer.parseInt(id.split("-")[1]);

        while (true) {
            synchronized(data.get(idd)) {
                data.get(idd).get(idd).printmsg(String.format("%s", id));
                try { Thread.sleep(2000); } catch (Exception e) {}
            }
        }
    }
}

class Msger {
    public void printmsg (String msg) 
    {
        System.out.println(msg);
    }
}

class Runer extends Thread {
    public MonitorPlayground p;
    public String id;

    public Runer (MonitorPlayground p, String id)
    {
        this.id = id;
    }
    
    public void run ()
    {
        MonitorPlayground.b(id);
    }
}