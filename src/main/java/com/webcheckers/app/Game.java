package com.webcheckers.app;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Row;
import com.webcheckers.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Application-tier Entity Game to represent a game
 *
 * @author Anh Nguyen
 */
public class Game {
    /**
     * Red player
     */
    private final Player redPlayer;

    /**
     * White player
     */
    private final Player whitePlayer;

    /**
     * Checkers board
     */
    private final BoardView board;

    /**
     * Identification for the game
     */
    private final int ID;

    /**
     * Constructor for the Game class
     * @param redPlayer given red player
     * @param whitePlayer given white player
     */
    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new BoardView();
        this.ID = Objects.hash(redPlayer, whitePlayer);
    }

    /**
     * BoardView for the red player
     * @return the board
     */
    public BoardView redPlayerBoard() {
        return board;
    }

    /**
     * BoardView for the white player, which is backwards
     * @return newly built backward board
     */
    public BoardView whitePlayerBoard() {
        List<Row> whiteBoard = new ArrayList<>();
        // iterate backward to build board backward for orientation
        for (int r = BoardView.BOARD_LENGTH - 1; r >= 0; r--) {
            Row row = new Row(board.getRow(r).getIndex());
            for (int c = BoardView.BOARD_LENGTH - 1; c >= 0; c--) {
                Space space = board.getRow(r).getSpace(c);
                row.addSpace(space);
            }
            whiteBoard.add(row);
        }
        return new BoardView(whiteBoard);
    }

    /**
     * Returns the red player
     * @return red Player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Returns the white player
     * @return white Player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Returns the game's ID
     * @return game's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Checks if the given player is the red player
     * @param other given player
     * @return true if given player is the red player
     */
    public boolean isRedPlayer(Player other) {
        return other.equals(this.redPlayer);
    }
}
