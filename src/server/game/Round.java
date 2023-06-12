package server.game;

import server.room.Player;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;

public class Round implements Runnable, GameCommunications{
    private static final int TIMER_IN_SECONDS = 90;
    private static final int REFRESH = 1000;
    private int remainingTime;
    private final LinkedList<Player> players;
    private final Player actualPlayer;
    private final String answer;
    private int points;

    public Round(LinkedList<Player> players, String answer, int playerIndex) {
        this.remainingTime = TIMER_IN_SECONDS;
        this.players = players;
        this.actualPlayer = players.get(playerIndex);
        this.answer = answer;
        this.points = 10;
    }
    @Override
    public void run() {
        //Bloqueia o chat e libera a tela do player atual
        String[] startRound = {OPEN_DRAW, CLOSE_CHAT, answer};
        DataOutputStream out = null;

        try {
            out = new DataOutputStream(actualPlayer.getServerSocket().getOutputStream());
            out.writeUTF(Arrays.toString(startRound));
        } catch (IOException ignored) { }

        DataOutputStream finalOut = out;
        Timer timer = new Timer(REFRESH, e -> {
            System.out.println(remainingTime);

            if (remainingTime == 0) {
                System.out.println("A resposta era: " + answer);

                String[] endRound = {CLOSE_DRAW, OPEN_CHAT};
                if (finalOut != null) {
                    try {
                        finalOut.writeUTF(Arrays.toString(endRound));
                    } catch (IOException ignored) {}
                }
                ((Timer) e.getSource()).stop();
            } else {
                remainingTime--;
            }
        });
        timer.start();
    }

    public void addPoints (Player p) {
        p.incrementPoints(points);
        points--;
    }
    /*
    public void verifyInput(String category, String answer, String input) {
        input = input.toLowerCase(Locale.ROOT);

        if (input.equals(answer)) {
            System.out.printf("%s!!!%n\n", input);
        } else {
            HashSet<String> variables = drawings.get(answer);
            if (variables.contains(input)) {
                System.out.printf("%s está quase!\n", input);
            }
        }
    }
     */
}
