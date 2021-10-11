package com.webcheckers.model;

/**
 * @author Phil Ganem 
 * Model tier entity for a player
 * */
public class Player {

    //Player name
    private String name;

    private boolean isPlaying;

    /**
     * Constructor for a player
     * @param playerName
     *  Name of the player
     */
    public Player(String playerName) {
        this.name = playerName;
        this.isPlaying = false;
    }

    /**
     * Getter for playerName
     * @return
     *  Str: Name of the player
     */
    public String getName() {
        return name;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    /**
     * Determines if two players are equal
     * @param player
     *  Player object
     * @return
     *  Bool: result of assert
     */
    public boolean equals(Player player) {
        return this.name.equals(player.name);
    }

    /**
     * ToString for player class
     * @return
     *  str: String rep of player
     */
    public String toString() {
        return this.name;
    }
}
