package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Model class for a checker board row
 * 
 * @Author Phil Ganem, Sierra Tran and Mohammed Alam
 */
public class Row implements Iterable<Space> {

    // Possible colors of a space
    private enum SPACECOLOR {
        Black, Red
    }

    // Index of a given row
    private final int index;
    // Spaces within a given row
    private final ArrayList<Space> spaces;
    // If the row starts with a blank space or not
    private boolean startsBlackSpace;

    /**
     * Constructor for a populated row
     * 
     * @param index
     * @param color
     * @param blackSpace
     */
    public Row(int index, SPACECOLOR color, boolean blackSpace) {
        this.spaces = new ArrayList<Space>();
        this.index = index;
        this.startsBlackSpace = blackSpace;
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

    public int getIndex(int index) {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        boolean isValid = false;
        for (int i = 0; i < 8; i++) {
            if ((i % 2 != 0) && (index == 4 || index == 3)) {
                isValid = true;
            }
            if ((i % 2 != 0))
            spaces.add(new Space(i + (index * 8), isValid,  ));
        }
        return spaces.iterator();
    }
}
