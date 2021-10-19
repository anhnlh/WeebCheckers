package com.webcheckers.model;

import java.util.Objects;

/**
 * Model object for a space on the board
 * 
 * @author Phil Ganem Sierra Tran
 */
public class Space {
    //ID for a space
    private int cellIdx;
    //Possible piece on a space
    private Piece piece;
    //If a space is a valid move
    private boolean isValid;

    /**
     * Constructor for a space on the checker board
     * @param cellIdx
     *  int: ID of space
     * @param isValid
     *  bool: determining whether space is a valid move
     * @param piece
     *  Piece: piece on a space
     */
    public Space(int cellIdx, boolean isValid, Piece piece){
        this.cellIdx = cellIdx;
        this.isValid = isValid;
        this.piece = piece;
    }

    /**
     * Checks if a space is a valid move by seeing if its a 
     * black square and if there's a piece there
     * @return
     *  bool: is valid?
     */
    public boolean isValid() {
        return this.piece != null && this.isValid; // looks better
    }

//------- Getters and Setters -------

    /**
     * Gets the piece on a space
     * @return
     *  Piece: piece
     */
    public Piece getPiece() {
        if(this.piece == null){return null;}
        return this.piece;
    }

    /**
     * Gets the cellid of a space
     * @return
     *  int: Cell ID
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Space)) return false;
        Space space = (Space) o;
        return cellIdx == space.cellIdx && Objects.equals(piece, space.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellIdx, piece);
    }
}
