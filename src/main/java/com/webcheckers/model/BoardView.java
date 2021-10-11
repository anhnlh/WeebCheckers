package com.webcheckers.model;

import java.util.Iterator;

/**
 * Model class for a checker board row
 * 
 * @Author Mohammed Alam
 */

public class BoardView implements Iterable<Row> {

    public enum UserColor {
        RED, WHITE
    }

    // The board length
    static final int BOARD_LENGTH = 8;
    // The color of the player using the boardView
    private final UserColor color;

    public BoardView(UserColor color) {
        this.color = color;
        board = new Board(color);
    }

    public Board getBoard() {
        return board;
    }

    public static int getBoardLength() {
        return BOARD_LENGTH;
    }

    public UserColor getColor() {
        return color;
    }

    @Override
    public Iterator<Row> iterator() {
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < BOARD_LENGTH; i++)
            rows.add(new Row(i, board));
        return rows.iterator();
    }
}
