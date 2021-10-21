package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.webcheckers.model.Piece.PIECECOLOR;

/**
 * Model class for a checker board row
 * 
 * @author Phil Ganem, Sierra Tran, and Mohammed Alam
 * @author Anh Nguyen
 */
public class Row implements Iterable<Space> {

    // Index of a given row
    private final int index;
    // Spaces within a given row
    private final List<Space> spaces;

    /**
     * Constructor for a populated row
     * 
     * @param index
     *  int: Index of a row
     * @param color
     *  COLOR: starting color
     * @param blackSpace alternating flag to find black space on board
     */
    public Row(int index, PIECECOLOR color, boolean blackSpace) {
        this.spaces = new ArrayList<>();
        this.index = index;
        initialize(color, blackSpace);
    }

    /**
     * Constructor for an empty row
     * 
     * @param index index of the row
     */
    public Row(int index) {
        this.spaces = new ArrayList<>();
        this.index = index;
    }

    /**
     * Initializes a board based on the color of the spaces.
     * @param color
     *  The player's color
     * @param isBlackSpace 
     *  True puts a checker piece on the tile, and tile is empty if false
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
     * Adds a space
     * @param space given Space
     */
    public void addSpace(Space space) {
        assert(spaces.size() < BoardView.BOARD_LENGTH);
        spaces.add(space);
    }

// ------- Getters and Setters -------

    /**
     * Gets the index of a row
     * @return
     *  int: Index of a row
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Finds and returns the space with a given cell index
     * @param cellIdx given cell index
     * @return Space
     */
    public Space getSpace(int cellIdx) {
        Space space = null;
        for (Space s : spaces) {
            if (s.getCellIdx() == cellIdx) {
                space = s;
            }
        }
        return space;
    }

    /**
     * Iterator for the row
     * @return 
     * Iterator: Iterator of the row
     */
    @Override
    public Iterator<Space> iterator() {
        return this.spaces.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Row)) return false;
        Row spaces1 = (Row) o;
        return index == spaces1.index && spaces.equals(spaces1.spaces);
    }

}
