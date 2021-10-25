package com.webcheckers.model;

public class Move {
    public enum MoveType {
        SIMPLE,
        JUMP
    }

    private final Position start;
    private final Position end;
    private MoveType moveType;


    public Move(Position start, Position end, MoveType type) {
        this.start = start;
        this.end = end;
        this.moveType = type;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }
}
