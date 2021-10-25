package com.webcheckers.model;

public class Position {
    private final int row;
    private final int cell;

    public Position(int row, int cell) {
        if (row >= 0 && row <= 7) {
            this.row = row;
        } else {
            this.row = -1;
        }
        if (cell >= 0 && cell <= 7) {
            this.cell = cell;
        } else {
            this.cell = -1;
        }

    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }

    public static boolean isInBounds(Position pos) {
        return pos.row != -1 && pos.cell != -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return row == position.row && cell == position.cell;
    }

}
