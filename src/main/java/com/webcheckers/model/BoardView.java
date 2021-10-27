package com.webcheckers.model;

import com.webcheckers.app.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Model class for the checkers board
 *
 * @author Mohammed Alam and Anh Nguyen
 */

public class BoardView implements Iterable<Row> {

    // board length
    public static final int BOARD_LENGTH = 8;
    // Array list for board made up of Rows
    private final List<Row> board;

    // starting number of pieces for each player
    private int numRedPieces = 12;
    private int numWhitePieces = 12;

    /**
     * Constructor for our Board
     * <p>
     * Another construcot for a board with a current board implementation
     */
    public BoardView() {
        this.board = new ArrayList<>();
        initialize();
    }

    /**
     * Constructor with a given board (List of Row)
     * Only use is {@link Game#getWhitePlayer()} to build white player's board
     *
     * @param board given board
     */
    public BoardView(List<Row> board) {
        this.board = board;
    }

    /**
     * Populates BoardView with Rows
     */
    private void initialize() {
        boolean blackSpace = false;
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if (i < 3) {
                board.add(new Row(i, Piece.Color.WHITE, blackSpace));
            } else if (i > 4) {
                board.add(new Row(i, Piece.Color.RED, blackSpace));
            } else {
                board.add(new Row(i, Piece.Color.NONE, blackSpace));
            }
            blackSpace = !blackSpace;
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return this.board.iterator();
    }

// ------- Getters and Setters -------

    /**
     * Gets the board
     *
     * @return list: board
     */
    public List<Row> getBoard() {
        return board;
    }

    /**
     * Finds and returns a Row from the board
     *
     * @param index index of the Row
     * @return the Row
     */
    public Row getRow(int index) {
        Row row = null;
        for (Row r : board) {
            if (r.getIndex() == index) {
                row = r;
            }
        }
        return row;
    }

    public int getNumRedPieces() {
        return numRedPieces;
    }

    public void decreaseNumRedPieces() {
        this.numRedPieces--;
    }

    public int getNumWhitePieces() {
        return numWhitePieces;
    }

    public void decreaseNumWhitePieces() {
        this.numWhitePieces--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardView)) return false;
        BoardView rows = (BoardView) o;
        return board.equals(rows.board);
    }

}
