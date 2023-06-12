package server.game;

import server.room.Player;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game implements GameCommunications {
    private final HashMap<String, HashSet<String>> drawings;
    private int playerIndex;
    private ScheduledExecutorService roundPool;

    public Game(HashMap<String, HashSet<String>> drawings) {
        this.playerIndex = 0;
        this.drawings = drawings;
        this.roundPool = Executors.newScheduledThreadPool(1);
    }

    public void startGame(LinkedList<Player> players, String category) {
        roundPool.scheduleWithFixedDelay(() -> {
            int randomElement = new Random().nextInt(drawings.size());
            String answer = drawings.keySet().toArray(new String[0])[randomElement];

            // Cria uma nova instância de Round e executa
            Round round = new Round(players, answer, playerIndex);
            round.run();

            for (Player p : players) {
                if (p.getPoints() >= 120) {
                    System.out.println(p.getNickname() + " foi o vencedor!");
                    roundPool.shutdown();
                }
            }
            playerIndex++;
        }, 0, 5, TimeUnit.SECONDS);
    }
}
