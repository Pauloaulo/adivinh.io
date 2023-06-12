package client.views.main;

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
