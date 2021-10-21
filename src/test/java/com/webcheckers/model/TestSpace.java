package com.webcheckers.model;
import com.webcheckers.model.Piece.TYPE;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import com.webcheckers.model.Piece.PIECECOLOR;

import static org.junit.Assert.*;

/**
 * @author Mohammed Alam
 */
@Tag("Model-tier")
public class TestSpace {
    @Test
    public void testValid() {
        // make a row with a new index
        Space mySpace = new Space(0, true, new Piece(TYPE.SINGLE, PIECECOLOR.RED));

        // assert expected index vs result form getIndex()
        assertTrue(mySpace.isValid());
    }

    @Test
    public void testNotValid() {
        // make a row with a new index
        Space mySpace = new Space(1, false, new Piece(TYPE.SINGLE, PIECECOLOR.RED));

        // assert expected index vs result form getIndex()
        assertFalse(mySpace.isValid());
    }

    @Test
    public void testGetPiece() {
        // make a row with a new index
        Piece myPiece = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Space mySpace = new Space(0, true, myPiece);

        // assert expected index vs result form getIndex()
        assertEquals(mySpace.getPiece(), myPiece);
    }

    @Test
    public void testGetCellIDx() {
        // make a row with a new index
        Piece myPiece = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Space mySpace = new Space(0, true, myPiece);

        // assert expected index vs result form getIndex()
        assertEquals(mySpace.getCellIdx(), 0);
    }

    @Test
    public void testSpaceEquals() {
        // make a row with a new index
        Piece myPiece1 = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Space mySpace1 = new Space(0, true, myPiece1);

        Piece myPiece2 = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Space mySpace2 = new Space(0, true, myPiece2);

        // assert expected index vs result form getIndex()
        assertTrue(mySpace1.equals(mySpace2));
    }
}
