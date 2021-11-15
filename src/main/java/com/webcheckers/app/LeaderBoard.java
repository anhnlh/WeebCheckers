package com.webcheckers.app;

import java.util.HashMap;

public class LeaderBoard {
    private int totalPlayers;
    private HashMap<String, Integer> playerWinMap;
    public static int leaderBoardNum = 0;

    public LeaderBoard() {
        this.totalPlayers = 0;
        this.playerWinMap = new HashMap<String, Integer>();
        leaderBoardNum++;
    }

    /**
     * Adds players to leader Board
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
        if (!lobby.containsKey(playerName.toLowerCase())) {
            lobby.put(playerName.toLowerCase(), new Player(playerName));
            return true;
        }

        return false;
    }

    /**
     * Increment Player Win
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
        if (!lobby.containsKey(playerName.toLowerCase())) {
            lobby.put(playerName.toLowerCase(), new Player(playerName));
            return true;
        }

        return false;
    }
}
