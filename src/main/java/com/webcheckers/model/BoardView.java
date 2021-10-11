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

    public enum UserColor {
        RED, WHITE
    }

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

    /**
     * Getters & Setters
     */
    public List<Row> getBoard() {
        return board;
    }

    public static int getBoardLength() {
        return BOARD_LENGTH;
    }

    private List<Row> rows;
    public static final int DIM = 8;


    @Override
    public Iterator<Row> iterator() {
        return this.board.iterator();
    }
}
