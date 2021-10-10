package com.webcheckers.model;

public class Space {
    private int cellIdx;

    public int getCellIdx() {
        return cellIdx;
    }

    public boolean isValid() {
        // if the space is a valid location (dark square) AND there is no piece on it
            // return true
        // else 
        return false;
    }

    public Piece getPiece() {
        // if there is a piece on the space
            // return the piece
        // else
        return null;
    }
}
