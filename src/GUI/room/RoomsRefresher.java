<<<<<<< HEAD
<<<<<<< HEAD
package GUI.room;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoomsRefresher implements Runnable {
    private RoomFrame frame;
    private ScheduledExecutorService executor;

    public RoomsRefresher(RoomFrame frame) {
        this.frame = frame;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this, 1, 5, TimeUnit.SECONDS);
    }

    public void restart() {
        executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this, 1, 5, TimeUnit.SECONDS);
    }

    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public void run() {
        frame.getRooms();
    }
}
=======
package GUI.room;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoomsRefresher implements Runnable {
    private RoomFrame frame;
    private ScheduledExecutorService executor;

    public RoomsRefresher(RoomFrame frame) {
        this.frame = frame;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this, 1, 5, TimeUnit.SECONDS);
    }

    public void restart() {
        executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this, 1, 5, TimeUnit.SECONDS);
    }

    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public void run() {
        frame.getRooms();
    }
}
>>>>>>> 23b1d70e725adc06e152eac01322ca46d1b6d8ce
=======
package GUI.room;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoomsRefresher implements Runnable {
    private RoomFrame frame;
    private ScheduledExecutorService executor;

    public RoomsRefresher(RoomFrame frame) {
        this.frame = frame;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this, 1, 5, TimeUnit.SECONDS);
    }

    public void restart() {
        executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this, 1, 5, TimeUnit.SECONDS);
    }

    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public void run() {
        frame.getRooms();
    }
}
>>>>>>> 12f24ba8b94604303eed8067a5a21cabccc7d0d3
