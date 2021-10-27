package com.webcheckers.model;

/**
 * Model class for a chess piece
 *
 * @author Phil Ganem and Sierra Tran
 */
public class Piece {
    // Possible types of pieces
    public enum Type {
        SINGLE, KING
    }

    // Possible colors of pieces
    public enum Color {
        RED, WHITE, NONE
    }

    // type of piece
    private Type type;
    // color of piece
    private final Color color;

    /**
     * Constructor of model class Piece
     *
     * @param type  TYPE: type of piece
     * @param color COLOR: color of piece
     */
    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Checks to see if a piece is a king
     *
     * @return bool: if piece is king
     */
    public boolean isKing() {
        return this.getType() == Type.KING;
    }

// ------- Getters and Setters -------

    /**
     * Sets the type of a piece. Allowing a piece to become a king
     *
     * @param type TYPE: type that piece becomes
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * gets the type of current piece
     *
     * @return TYPE: piece type
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Gets the color of current piece
     *
     * @return COLOR: color of current piece
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Checks if this Piece and the given Object is equal.
     * 
     * @param o an object
     * @return whether this Piece and given object are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return type == piece.type && color == piece.color;
    }

}
