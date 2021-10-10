package com.webcheckers.model;

/**
 * Model class for a chess piece
 * 
 * @author Phil Ganem and Sierra Tran
 */
public class Piece {
    //Possible types of pieces
    private enum TYPE {SINGLE, KING}
    //Possible colors of pieces
    private enum COLOR {RED, WHITE}

    //type of piece
    private TYPE type;
    //color of piece
    private COLOR color;

    /**
     * Constructor of model class Piece
     * @param type
     *  TYPE: type of piece
     * @param color
     *  COLOR: color of piece
     */
    public Piece(TYPE type, COLOR color){
        this.type = type;
        this.color = color;
    }

    /**
     * Checks to see if a piece is a king
     * @return
     *  bool: if piece is king
     */
    public boolean isKing(){
        return this.getType() == TYPE.KING;
    }
//------- Getters and Setters -------

    /**
     * Sets the type of a piece. Allowing a piece to become a king
     * @param type
     *  TYPE: type that piece becomes
     */
    public void setType(TYPE type) {
        this.type = type;
    }

    /**
     * gets the type of current piece
     * @return
     *  TYPE: piece type
     */
    public TYPE getType() {
        return this.type;
    }

    /**
     * Gets the color of current piece
     * @return
     *  COLOR: color of current piece
     */
    public COLOR getColor() {
        return this.color;
    }

}
