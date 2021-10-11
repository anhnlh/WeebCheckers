package com.webcheckers.app;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Row;
import com.webcheckers.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private final Player redPlayer;
    private final Player whitePlayer;
    private final BoardView board;
    private final int ID;

    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new BoardView();
        this.ID = Objects.hash(redPlayer, whitePlayer);
    }

    public BoardView redPlayerBoard() {
        return board;
    }

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

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public int getID() {
        return ID;
    }

    public boolean isRedPlayer(Player other) {
        return other.equals(this.redPlayer);
    }
}
