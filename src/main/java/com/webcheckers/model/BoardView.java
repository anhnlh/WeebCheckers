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

    // board length
    public static final int BOARD_LENGTH = 8;
    // Array list for board made up of Rows
    private final List<Row> board;

    /**
     * Constructor for our Board
     * 
     * Another construcot for a board with a current board implementation
     */
    public BoardView() {
        this.board = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        boolean blackSpace = false;
        for(int i = 0; i < BOARD_LENGTH; i++) {
            if (i < 3) {
                board.add(new Row(i, Piece.PIECECOLOR.WHITE, blackSpace));
            } else if (i > 4) {
                board.add(new Row(i, Piece.PIECECOLOR.RED, blackSpace));
            } else {
                board.add(new Row(i, Piece.PIECECOLOR.NONE, blackSpace));
            }
            blackSpace = !blackSpace;
        }
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

    public Row getRow(int index) {
        Row row = null;
        for (Row r : board) {
            if (r.getIndex() == index) {
                row = r;
            }
        }
        return row;
    }


}
