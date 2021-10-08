package com.webcheckers.app;

import com.webcheckers.model.Player;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Phil Ganem 
 * Application tier entity for a player lobby
 * */
public class PlayerLobby {
    
    //Hashmap representation of a lobby
    // name (str) connects to Player object
    private HashMap<String, Player> lobby;

    //Lobby ID
    private final int lobbyID;

    //Static int for ID creation
    public static int lobbyNum = 0;

    /**
     * constructor for a playerLobby
     */
    public PlayerLobby() {
        this.lobby = new HashMap<>();
        this.lobbyID = lobbyNum;
        lobbyNum++;
    }

    /**
     * Adds players to lobby
     * @param name
     *  str: Player name
     * @return
     *  Bool: true on successful add / false on unsuccessful add
     */
    public synchronized boolean addPlayer(String playerName) {
        
        //return false if name invalid
        if(playerName.length() >= 26 || playerName.length() <= 0 || playerName.isEmpty()) { 
            return false; 
        }
        
        //return false if name invalid
        Pattern invalid = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher find = invalid.matcher(playerName);
        if(find.find()) {
             return false; 
        }
        
        //If name doesnt exist in lobby, add and return true
        if(!lobby.containsKey(playerName.toLowerCase())) {
            lobby.put(playerName.toLowerCase(), new Player(playerName));
            return true;
        }
        
        return false;
    }

    /**
     * Removes a player from the map
     * @param player
     *  str: Player name
     */
    public synchronized void removePlayer(String player) {
        lobby.remove(player.toLowerCase());
    }

    /**
     * Finds a player within the lobby
     * @param player
     *  str: player name
     * @return
     *  Player: found player object
     */
    public synchronized Player getPlayer(String player) {
        if(lobby.containsKey(player.toLowerCase())) {
            return lobby.get(player.toLowerCase());
        }
        return null;
    }

    /**
     * Get all the players wihin Lobby object
     * @return
     *  Collection<str>: all player objects
     */
    public Collection<Player> getActivePlayers() {
        return lobby.values();
    }

    /**
     * Gets all players excluding a singulair one (the viewer)
     *
     * @param exclude
     *  Player: Player object that should be excluded from collection
     * @return
     *  Collection<str>: collection of all players but excluded 
     */
    public Collection<Player> getOtherActivePlayers(Player exclude) {
        Collection<Player> temp = new ArrayList<>();
        lobby.values().forEach(player -> temp.add(player));
        temp.remove(exclude);
        return temp;
    }

// --- Getters and Setters ---

    public HashMap<String, Player> getLobby() {
        return lobby;
    }

    public void setLobby(HashMap<String, Player> lobby) {
        this.lobby = lobby;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public static int getLobbyNum() {
        return lobbyNum;
    }

    public int size() {
        return lobby.size();
    }
}
