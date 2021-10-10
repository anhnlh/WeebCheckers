package com.webcheckers.model;

import java.util.Iterator;

public class Row implements Iterable<Space> {
    private int index;

    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return null;
    }
}
