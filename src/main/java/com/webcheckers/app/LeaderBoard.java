package com.webcheckers.app;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeaderBoard {
    private HashMap<String, Integer> playerWinMap;
    public static int leaderBoardNum = 0;

    public LeaderBoard() {
        this.playerWinMap = new HashMap<String, Integer>();
        leaderBoardNum++;
    }

    /**
     * Adds players to leader Board, or increments Player win if existing.
     *
     * @param playerName str: Player name
     * @return Bool: true on successful add / false on unsuccessful add
     */
    public synchronized boolean addPlayer(String playerName) {

        // return false if name invalid
        if (playerName.length() >= 26 || playerName.length() <= 0 || playerName.isEmpty()) {
            return false;
        }

        // return false if name invalid
        Pattern invalid = Pattern.compile("[^A-Za-z0-9-_ ]");
        Matcher find = invalid.matcher(playerName);
        if (find.find()) {
            return false;
        }

        // If name doesnt exist in lobby, add and return true
        if (!playerWinMap.containsKey(playerName.toLowerCase())) {
            playerWinMap.put(playerName.toLowerCase(), 0);
            return true;
        } else {
            playerWinMap.put(playerName.toLowerCase(), playerWinMap.get(playerName.toLowerCase() + 1));
            return true;
        }
    }

}
