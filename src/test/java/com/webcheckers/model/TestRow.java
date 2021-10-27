package com.webcheckers.model;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Mohammed Alam
 */
@Tag("Model-tier")
public class TestRow {

    @Test
    public void testGetIndex() {
        // make a row with a new index
        Row myRow = new Row(5);

        // assert expected index vs result form getIndex()
        assertEquals(myRow.getIndex(), 5);
    }

    // Used to test get spaces when mdiway through a game
    @Test
    public void testAddSpacee() {
        // Our spaces arraymade with getSpace()
        Row myRow = new Row(5);

        // Code adding a new Space() using addSpace()
        Space mySpace = new Space(5, true, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        
        // Asserting new space is in our list by making sure to
        // call getSpaces again()
        myRow.addSpace(mySpace);
        assertEquals(myRow.getSpace(5), mySpace);
    }

    // We will test the Rows being equal, they should in this case
    // because they are the same index meaning same position of the board
    @Test
    public void TestRowEquals() {
        // Our Row made wiht the same index
        Row myRow1 = new Row(5);
        
        Row myRow2 = new Row(5);

        // Asserting that our Rows are equal
        assertEquals(myRow1, myRow2);
    }

    @Test
    public void TestIteratorNotEqual() {
        // Our spaces arraymade with getSpace()
        Row myRow = new Row(5);
        ArrayList<Space> preSpaces = new ArrayList<>();
        
        // ArrayList currentSpaces = Row().getSpaces()
        Iterator<Space> expectedSpaces = preSpaces.iterator();
        Iterator<Space> actualSpaces = myRow.iterator();

        // Asserting new space is in our list by making sure to
        // call getSpaces again()
        assertNotEquals(expectedSpaces, actualSpaces);
    }
}
