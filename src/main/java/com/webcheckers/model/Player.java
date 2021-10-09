package com.webcheckers.model;

/**
 * @author Phil Ganem 
 * Model tier entity for a player
 * */
public class Player {

    //Player name
    private String playerName;

    /**
     * Constructor for a player 
     * @param playerName
     *  Name of the player 
     */
    
    public Player(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Getter for playerName
     * @return
     *  Str: Name of the player
     */
    public String getName() {
        return playerName;
    }

    /**
     * Determines if two players are equal
     * @param player
     *  Player object
     * @return
     *  Bool: result of assert
     */
    public boolean equals(Player player) {
        return this.playerName.equals(player.playerName);
    }

    /**
     * ToString for player class
     * @return
     *  str: String rep of player
     */
    public String toString() {
        return this.playerName;
    }
}
