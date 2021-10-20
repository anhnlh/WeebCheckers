package com.webcheckers.model;
import com.webcheckers.model.Piece.TYPE;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.webcheckers.model.Piece.PIECECOLOR;

/**
 * @author Phil Ganem
 */
public class TestPiece {
    
    @Test
    public void testgetColor() {
        PIECECOLOR expectedSingle = PIECECOLOR.RED;
        PIECECOLOR expectedKing = PIECECOLOR.WHITE;

        Piece singlePiece = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Piece kingPiece = new Piece(TYPE.KING, PIECECOLOR.WHITE);

        PIECECOLOR actualDefault = singlePiece.getColor();
        PIECECOLOR actualKing = kingPiece.getColor();

        assertEquals(expectedSingle, actualDefault);
        assertEquals(expectedKing, actualKing);
    }

    @Test
    public void testgetType() {
        Piece singlePiece = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Piece kingPiece = new Piece(TYPE.KING, PIECECOLOR.WHITE);

        TYPE actualSingle = singlePiece.getType();
        TYPE actualKing = kingPiece.getType();

        assertEquals(TYPE.SINGLE, actualSingle);
        assertEquals(TYPE.KING, actualKing);
    }
 
    @Test
    public void testequals() {
        Piece singlePieceOne = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Piece singlePieceTwo = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        assertEquals(singlePieceOne.equals(singlePieceTwo), true); 
    }
}

