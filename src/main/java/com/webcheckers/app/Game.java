package com.webcheckers.app;

import com.webcheckers.model.*;
import com.webcheckers.util.Message;

import java.util.*;

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

    private final Deque<Move> moveDeque;

    private boolean gameOver;

    private String gameOverMessage;

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
        this.moveDeque = new LinkedList<>();
        this.gameOver = false;
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

    public void setPlayerInTurn(Player playerInTurn) {
        this.playerInTurn = playerInTurn;
    }

    public boolean isRedPlayerTurn() {
        return playerInTurn.equals(redPlayer);
    }

    private Piece.Color playerColor() {
        Piece.Color color;
        if (isRedPlayer(playerInTurn)) {
            color = Piece.Color.RED;
        } else {
            color = Piece.Color.WHITE;
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

    public boolean isGameOver() {
        return gameOver;
    }

    public String getGameOverMessage() {
        return gameOverMessage;
    }

    public void setGameOverMessage(String gameOverMessage) {
        this.gameOverMessage = gameOverMessage;
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
                            endRow == startRow - 1 && (endCell == startCell + 1 || endCell == startCell - 1) :  // red
                            endRow == startRow + 1 && (endCell == startCell + 1 || endCell == startCell - 1);   // white
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
                getSpace((startCell + endCell) / 2).getPiece();               // should be not null valid move

        // multiple jump case
        if (!moveDeque.isEmpty() && moveDeque.peekFirst().getMoveType().equals(Move.MoveType.JUMP)) {
            // startPiece must be the piece from the start of the chain of jumps
            // removing the move from the moveDeque and adding it back for good practice
            Move firstJumpMove = moveDeque.removeFirst();
            startPiece = board.getRow(firstJumpMove.getStart().getRow()).
                    getSpace(firstJumpMove.getStart().getCell()).getPiece();
            moveDeque.addFirst(firstJumpMove);
        }

        // perform check
        if (startPiece != null && endPiece == null && capturePiece != null) {
            switch (startPiece.getType()) {
                case SINGLE:
                    System.out.println("reached");
                    valid = isRedPlayer(playerInTurn) ?
                            endRow == startRow - 2 && (endCell == startCell + 2 || endCell == startCell - 2)
                                    && !capturePiece.getColor().equals(playerColor()) :           // red
                            endRow == startRow + 2 && (endCell == startCell + 2 || endCell == startCell - 2)
                                    && !capturePiece.getColor().equals(playerColor());            // white
                    break;
                case KING:
                    valid = (endRow == startRow + 2 || endRow == startRow - 2) &&
                            (endCell == startCell + 2 || endCell == startCell - 2) &&
                            !capturePiece.getColor().equals(playerColor());
                    break;
            }
        }

        return valid;
    }

    private boolean allPossibleJumpMoves() {
        for (Row row : board) {
            for (Space space : row) {
                Piece piece = space.getPiece();
                if (piece != null && piece.getColor().equals(playerColor())) {
                    Position start = new Position(row.getIndex(), space.getCellIdx());
                    for (int r = -2; r <= 2; r += 4) {      // -2 and +2 to rowIndex
                        for (int c = -2; c <= 2; c += 4) {  // -2 and +2 to cellIdx
                            Position end = new Position(start.getRow() + r, start.getCell() + c);
                            if (Position.isInBounds(end)) {
                                Move move = new Move(start, end, Move.MoveType.JUMP);
                                if (isJumpMove(move)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean singlePossibleJumpMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        for (int r = -2; r <= 2; r += 4) {
            for (int c = -2; c <= 2; c += 4) {
                Position newEnd = new Position(end.getRow() + r, end.getCell() + c);
                if (!newEnd.equals(start) && Position.isInBounds(newEnd)) {
                    Move m = new Move(end, newEnd, Move.MoveType.JUMP);
                    if (isJumpMove(m)) {
                        System.out.println("singlePossibleJumpMove reached");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Message validateMove(Move move) {
        Message message = Message.error("Invalid move.");
        System.out.println(move);
        if (isSimpleMove(move)) {
            if (allPossibleJumpMoves()) {
                message = Message.error("Jump move available. Must make jump moves.");
            } else {
                move.setMoveType(Move.MoveType.SIMPLE);
                moveDeque.add(move);
                message = Message.info("Valid simple move.");
            }
        } else if (isJumpMove(move)) {
            move.setMoveType(Move.MoveType.JUMP);
            moveDeque.add(move);
            message = Message.info("Valid jump move.");
        }

        return message;
    }

    public boolean makeMove() {
        boolean movesMade = false;

        // if a jump move is still possible with the latest move
        if (moveDeque.getLast().getMoveType().equals(Move.MoveType.JUMP) && singlePossibleJumpMove(moveDeque.getLast())) {
            return false;
        }

        // makes all the moves
        while (!moveDeque.isEmpty()) {
            Move move = moveDeque.remove();

            int startRow = move.getStart().getRow();
            int startCell = move.getStart().getCell();
            int endRow = move.getEnd().getRow();
            int endCell = move.getEnd().getCell();

            Space start = board.getRow(startRow).getSpace(startCell);
            Space end = board.getRow(endRow).getSpace(endCell);
            switch (move.getMoveType()) {
                case SIMPLE:
                    end.setPiece(start.getPiece());
                    start.setPiece(null);
                    break;
                case JUMP:
                    end.setPiece(start.getPiece());
                    start.setPiece(null);
                    Space capture = board.getRow((startRow + endRow) / 2).
                                        getSpace((startCell + endCell) / 2);
                    capture.setPiece(null);
                    if (isRedPlayerTurn()) {
                        board.decreaseNumWhitePieces();
                    } else {
                        board.decreaseNumRedPieces();
                    }
                    break;
            }
            if ((isRedPlayerTurn() && endRow == 0) ||                             // red
                (!isRedPlayerTurn() && endRow == BoardView.BOARD_LENGTH - 1)) {   // white
                end.getPiece().setType(Piece.Type.KING);
            }
            if (board.getNumRedPieces() == 0) {
                gameOver = true;
                gameOverMessage = getWhitePlayer() + "won! " + getRedPlayer() + "ran out of pieces.";
                setGameOver();
            } else if (board.getNumWhitePieces() == 0) {
                gameOver = true;
                gameOverMessage = getRedPlayer() + "won! " + getWhitePlayer() + "ran out of pieces.";
                setGameOver();
            }
            movesMade = true;
        }

        // TODO (Optional): lose if run out of moves

        return movesMade;
    }

    public void setGameOver() {
        redPlayer.setPlaying(false);
        whitePlayer.setPlaying(false);
        gameOver = true;
    }

    public boolean backupMove() {
        if (!moveDeque.isEmpty()) {
            moveDeque.removeLast();
            return true;
        }
        return false;
    }
}
