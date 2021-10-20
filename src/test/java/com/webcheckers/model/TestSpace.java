package com.webcheckers.model;
import com.webcheckers.model.Piece.TYPE;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.Assert.assertEquals;
import com.webcheckers.model.Piece.PIECECOLOR;

/**
 * @author Mohammed Alam
 */
@Tag("Model-tier")
public class TestSpace {
    @Test
    public void testValid() {
        // make a row wiht a new index
        Space mySpace = new Space(0, true, new Piece(TYPE.SINGLE, PIECECOLOR.RED));

        // assert expected index vs result form getIndex()
        assertEquals(mySpace.isValid(), true);
    }

    @Test
    public void testNotValid() {
        // make a row wiht a new index
        Space mySpace = new Space(1, false, new Piece(TYPE.SINGLE, PIECECOLOR.RED));

        // assert expected index vs result form getIndex()
        assertEquals(mySpace.isValid(), false);
    }

    @Test
    public void testGetPiece() {
        // make a row wiht a new index
        Piece myPiece = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Space mySpace = new Space(0, true, myPiece);

        // assert expected index vs result form getIndex()
        assertEquals(mySpace.getPiece(), myPiece);
    }

    @Test
    public void testGetCellDx() {
        // make a row wiht a new index
        Piece myPiece = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Space mySpace = new Space(0, true, myPiece);

        // assert expected index vs result form getIndex()
        assertEquals(mySpace.getCellIdx(), 0);
    }

    @Test
    public void testSpaceEquals() {
        // make a row wiht a new index
        Piece myPiece1 = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Space mySpace1 = new Space(0, true, myPiece1);

        Piece myPiece2 = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Space mySpace2 = new Space(0, true, myPiece2);

        // assert expected index vs result form getIndex()
        assertEquals(mySpace1.equals(mySpace2), true);
    }
}
