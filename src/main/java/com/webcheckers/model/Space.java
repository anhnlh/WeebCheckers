package com.webcheckers.model;

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
        if(this.piece == null || this.isValid == false){return false;}
        return true;
    }

// ------ Getters and Setters -------

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

}
