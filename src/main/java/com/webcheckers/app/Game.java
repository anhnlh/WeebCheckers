package com.webcheckers.app;

import com.webcheckers.model.*;
import com.webcheckers.util.Message;

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

    private Player playerInTurn;

    /**
     * Constructor for the Game class
     *
     * @param redPlayer   given red player
     * @param whitePlayer given white player
     */
    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new BoardView();
        this.ID = Objects.hash(redPlayer, whitePlayer);
        this.playerInTurn = redPlayer; // red player starts first
    }

    /**
     * BoardView for the red player
     *
     * @return the board
     */
    public BoardView redPlayerBoard() {
        return board;
    }

    /**
     * BoardView for the white player, which is backwards
     *
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
     *
     * @return red Player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Returns the white player
     *
     * @return white Player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Returns the game's ID
     *
     * @return game's ID
     */
    public int getID() {
        return ID;
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public void setPlayerInTurn(Player playerInTurn) {
        this.playerInTurn = playerInTurn;
    }

    public boolean isRedPlayerTurn() {
        return playerInTurn.equals(redPlayer);
    }

    private Piece.PIECECOLOR getPieceColor() {
        Piece.PIECECOLOR color = null;
        if (isRedPlayer(playerInTurn)) {
            color = Piece.PIECECOLOR.RED;
        } else {
            color = Piece.PIECECOLOR.WHITE;
        }
        return color;
    }

    /**
     * Checks if the given player is the red player
     *
     * @param other given player
     * @return true if given player is the red player
     */
    public boolean isRedPlayer(Player other) {
        return other.equals(this.redPlayer);
    }

    private boolean isSimpleMove(Move move) {
        boolean valid = false;

        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();

        Piece startPiece = board.getRow(startRow).getSpace(startCell).getPiece();   // should be not null if valid move
        Piece endPiece = board.getRow(endRow).getSpace(endCell).getPiece();         // should be null if valid move

        // perform check
        if (startPiece != null && endPiece == null) {
            switch (startPiece.getType()) {
                case SINGLE:
                    // red's board is different from white's
                    valid = isRedPlayer(playerInTurn) ?
                            endRow == startRow + 1 && (endCell == startCell + 1 || endCell == startCell - 1) :  // red
                            endRow == startRow - 1 && (endCell == startCell + 1 || endCell == startCell - 1);   // white
                    break;
                case KING:
                    // king can move diagonally backwards
                    valid = (endRow == startRow + 1 || endRow == startRow - 1) &&
                            (endCell == startCell + 1 || endCell == startCell - 1);
                    break;
            }
        }

        return valid;
    }

    private boolean isJumpMove(Move move) {
        boolean valid = false;

        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();

        Piece startPiece = board.getRow(startRow).getSpace(startCell).getPiece();   // should be not null if valid move
        Piece endPiece = board.getRow(endRow).getSpace(endCell).getPiece();         // should be null if valid move
        Piece capturePiece = board.getRow((startRow + endRow) / 2).
                getSpace((startCell + endCell) / 2).getPiece();   // should be not null valid move

        // perform check
        if (startPiece != null && endPiece == null && capturePiece != null) {
            switch (startPiece.getType()) {
                case SINGLE:
                    valid = isRedPlayer(playerInTurn) ?
                            endRow == startRow + 2 && (endCell == startCell + 2 || endCell == startCell - 2)
                                    && !capturePiece.getColor().equals(getPieceColor()) :                         // red
                            endRow == startRow - 2 && (endCell == startCell + 2 || endCell == startCell - 2)
                                    && !capturePiece.getColor().equals(getPieceColor());                            // white
                    break;
                case KING:
                    valid = (endRow == startRow + 2 || endRow == startRow - 2) &&
                            (endCell == startCell + 2 || endCell == startCell - 2) &&
                            !capturePiece.getColor().equals(getPieceColor());
                    break;
            }
        }

        return valid;
    }

    private boolean availableJumpMove() {
        for (Row row : board) {
            for (Space space : row) {
                Piece piece = space.getPiece();
                if (piece != null && !piece.getColor().equals(getPieceColor())) {
                    Position pos = new Position(row.getIndex(), space.getCellIdx());
                    for (int r = -2; r <= 2; r += 4) {      // -2 and +2 to rowIndex
                        for (int c = -2; c <= 2; c += 4) {  // -2 and +2 to cellIdx
                            Position endPos = new Position(pos.getRow() + r, pos.getCell() + c);
                            if (endPos.getRow() != -1 && endPos.getCell() != -1) {
                                Move move = new Move(pos, endPos);
                                return isJumpMove(move);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public Message validateMove(Move move) {
        Message message = Message.error("Invalid move.");
        if (isSimpleMove(move)) {
            if (availableJumpMove()) {
                message = Message.error("Jump move available. Must make jump moves.");
            } else {
                // do something
                message = Message.info("Valid simple move.");
            }
        } else if (isJumpMove(move)) {
            // do something
            message = Message.info("Valid jump move.");
        }

        return message;
    }
}
