package com.webcheckers.model;

/**
 * Represents a move on the board.
 * @Author: Anh Nguyen
 */
public class Move {
    /**
     * Types of moves.
     */
    public enum MoveType {
        SIMPLE,
        JUMP
    }

    //The start position of the move
    private final Position start;
    //The end position of the move
    private final Position end;
    //The type of move
    private MoveType moveType;


    /**
     * Constructor for a simple move.
     * @param start The start position of the move.
     * @param end The end position of the move.
     */
    public Move(Position start, Position end, MoveType type) {
        this.start = start;
        this.end = end;
        this.moveType = type;
    }

    /**
     * Getter for the start position of the move.
     * @return The start position of the move.
     */
    public Position getStart() {
        return start;
    }

    /**
     * Getter for the end position of the move.
     * @return The end position of the move.
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Getter for the type of move.
     * @return The type of move.
     */
    public MoveType getMoveType() {
        return moveType;
    }

    /**
     * Setter for the type of move.
     * @param moveType The type of move.
     */
    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }
}
