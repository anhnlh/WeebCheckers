package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.webcheckers.model.Piece.PIECECOLOR;

/**
 * Model class for a checker board row
 * 
 * @Author Phil Ganem, Sierra Tran and Mohammed Alam
 */
public class Row implements Iterable<Space> {

    // // Possible colors of a space
    // private enum SPACECOLOR {
    // Black, Red, None
    // }

    // Index of a given row
    private final int index;
    // Spaces within a given row
    private final ArrayList<Space> spaces;

    /**
     * Constructor for a populated row
     * 
     * @param index
     * @param color
     * @param blackSpace
     */
    public Row(int index, PIECECOLOR color, boolean blackSpace) {
        this.spaces = new ArrayList<Space>();
        this.index = index;
        initialize(color, blackSpace);
    }

    /**
     * Constructor for an empty row
     * 
     * @param index
     */
    public Row(int index) {
        this.spaces = new ArrayList<Space>();
        this.index = index;
    }

    /**
     * Initializes a board based on the color of the spaces.
     *
     * @param color        The player's color
     * @param isBlackSpace True puts a checker piece on the tile, and tile is empty
     *                     if false
     */
    public void initialize(PIECECOLOR color, boolean isBlackSpace) {
        for (int col = 0; col < BoardView.BOARD_LENGTH; col++) {
            if (isBlackSpace && color != PIECECOLOR.NONE) {
                spaces.add(new Space(col, true, new Piece(Piece.TYPE.SINGLE, color)));
            } else {
                spaces.add(new Space(col, isBlackSpace, null));
            }
            isBlackSpace = !isBlackSpace;
        }
    }

    /**
     * Getters & Setters
     */
    public int getIndex(int index) {
        return index;
    }

    /**
     * Iterator for the row
     *
     * @return Iterator of the row
     */
    @Override
    public Iterator<Space> iterator() {
        return this.spaces.iterator();
    }
}
