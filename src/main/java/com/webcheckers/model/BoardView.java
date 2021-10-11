package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Model class for a checker board row
 * 
 * @Author Mohammed Alam
 */

public class BoardView implements Iterable<Row> {

    //Possible colors of a user
    public enum UserColor {RED, WHITE}
    // board length
    static final int BOARD_LENGTH = 8;
    // Array list for board made up of Rows
    private final List<Row> board;

    /**
     * Constructor for our Board
     * 
     * Another construcot for a board with a current board implementation
     */
    public BoardView() {
        this.board = new ArrayList<>();
    }

    public BoardView(List<Row> board) {
        this.board = board;
    }

    @Override
    public Iterator<Row> iterator() {
        return this.board.iterator();
    }

// ------- Getters and Setters -------

    /**
     * Gets the board
     * @return
     *  list: board
     */
    public List<Row> getBoard() {
        return board;
    }

    /**
     * Gets the board length
     * @return
     *  int: Board length
     */
    public static int getBoardLength() {
        return BOARD_LENGTH;
    }

}
