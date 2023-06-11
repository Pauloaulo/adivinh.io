package server.game;

import server.room.Player;

import javax.swing.Timer;
import java.util.*;

public class Game {
    private static final int TIMER_IN_SECONDS = 90;
    private static final int REFRESH = 1000;
    private int points;
    private final HashMap<String, HashSet<String>> drawnings;
    private int actualPlayer;
    private int remainingTime;

    public Game(HashMap<String, HashSet<String>> drawnings) {
        this.points = 10;
        this.actualPlayer = 0;
        this.drawnings = drawnings;
        this.remainingTime = TIMER_IN_SECONDS;
    }

    public void round(final LinkedList<Player> players, final String answer) {
        // Libera a tela para o player atual
        // Bloqueia o chat para o player atual
        // Enviar a resposta e lista de correspondêcias para o player atual
        Timer timer = new Timer(REFRESH, e -> {
            remainingTime--;
            if (remainingTime == 0) {
                System.out.println("A resposta era: " + answer);
                // Bloqueia a tela para o player atual
                // Libera o chat para o player atual
                actualPlayer += 1 % players.size();
                ((Timer) e.getSource()).stop();
            } else {
                System.out.println(remainingTime);
            }
        });
        timer.start();
    }

    public void startGame(LinkedList<Player> players, String category) {
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Player p : players) {
                    if (p.getPoints() >= 120) {
                        System.out.println(p.getNickname() + " Ganhou!!!");
                        cancel();
                        timer.cancel();
                    }
                }
                remainingTime = TIMER_IN_SECONDS;
                points = 10;

                int randomElement = new Random().nextInt(drawnings.size());
                String answer = drawnings.keySet().toArray(new String[0])[randomElement];

                round(players, answer);
            }
        }, 0, 95 * 1000);
    }

    // Encontrar lugar para fazer essa verificação
    // De preferência do lado do cliente
    public void verifyInput (String category, String answer, String input) {
        input = input.toLowerCase(Locale.ROOT);

        if (input.equals(answer)) {
            System.out.printf("%s!!!%n\n", input);
        } else {
            HashSet<String> variables = drawnings.get(answer);
            if (variables.contains(input)) {
                System.out.printf("%s está quase!\n", input);
            }
        }
    }

    public void addPoint (Player p) {
        p.incrementPoints(this.points);
        points--;
    }
}
