package com.webcheckers.model;

/**
 * The Position class represents a position on the board.
 *
 * @author Anh Nguyen
 */
public class Position {
    //row of a position
    private final int row;
    //cell of a position
    private final int cell;

    /**
     * Constructor for Position
     * @param row row of the position
     * @param cell cell of the position
     */
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

    /**
     * Getter for row
     * @return row of the position
     */
    public int getRow() {
        return row;
    }

    

    /**
     * Getter for cell
     * @return cell of the position
     */
    public int getCell() {
        return cell;
    }

    /**
     * Checks if the position is valid
     * @return true if the position is valid, false otherwise
     */
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
