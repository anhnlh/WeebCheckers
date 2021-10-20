package com.webcheckers.model;
import com.webcheckers.model.Piece.TYPE;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import com.webcheckers.model.Piece.PIECECOLOR;

/**
 * @author Mohammed Alam
 */
@Tag("Model-tier")
public class TestRow {

    @Test
    public void testGetIndex() {
        // make a row wiht a new index
        Row myRow = new Row(5);

        // assert expected index vs result form getIndex()
        assertEquals(myRow.getIndex(), 5);
    }

    // Used to test get spaces when a new game is called.
    @Test
    public void getSpacesNew() {
        // Our spaces arraymade with getSpace()
        Row myRow = new Row(5);
        // ArrayList currentSpaces = Row().getSpaces()
        Space actual = myRow.getSpace(5);
        // Code adding a new Space() using addSpace()

        // Asserting new space is in our list by making sure to
        // call getSpaces again()
    }

    // Used to test get spaces when mdiway through a game
    @Test
    public void getSpacesInGame() {
        // Our spaces arraymade with getSpace()
        // new Row() class
        // ArrayList currentSpaces = Row().getSpaces()

        // Code adding a new Space() using addSpace()

        // Asserting new space is in our list by making sure to
        // call getSpaces again()
    }

    @Test
    public void testAddSpace() {
        // Our spaces arraymade with getSpace()
        // new Row() class
        // ArrayList currentSpaces = Row().getSpaces()

        // Code adding a new Space() using addSpace()

        // Asserting new space is in our list by making sure to
        // call getSpaces again()
    }
}
