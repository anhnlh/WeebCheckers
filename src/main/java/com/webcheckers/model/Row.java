package com.webcheckers.model;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Model class for a checker board row
 * 
 * @Author Phil Ganem and Sierra Tran
 */
public class Row implements Iterable<Space> {

    //Possible colors of a space
    private enum SPACECOLOR{Black, Red}
    //Index of a given row
    private final int index;
    //Spaces within a given row
    private final ArrayList<Space> spaces;

    /**
     * Constructor for a populated row
     * @param index
     * @param color
     * @param blackSpace
     */
    public Row(int index, SPACECOLOR color, boolean blackSpace){
        this.spaces = new ArrayList<Space>();
        this.index = index;
    }

    /**
     * Constructor for an empty row
     * @param index
     */
    public Row(int index){
        this.spaces = new ArrayList<Space>();
        this.index = index;
    }

    public int getIndex(int index) {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return null;
    }
}
