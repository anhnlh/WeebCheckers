package com.webcheckers.model;
import com.webcheckers.model.Piece.TYPE;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.Assert.assertEquals;
import com.webcheckers.model.Piece.PIECECOLOR;

/**
 * @author Phil Ganem
 */
@Tag("Model-tier")
public class TestPiece {
    
    /**
     * Tests to see if color of a piece is correctly set
     */
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

    /**
     * Tests to see if type of a piece is correctly set
     */
    @Test
    public void testgetType() {
        Piece singlePiece = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Piece kingPiece = new Piece(TYPE.KING, PIECECOLOR.WHITE);

        TYPE actualSingle = singlePiece.getType();
        TYPE actualKing = kingPiece.getType();

        assertEquals(TYPE.SINGLE, actualSingle);
        assertEquals(TYPE.KING, actualKing);
    }
 
    /**
     * Tests to see if two pieces with same field values are equal
     */
    @Test
    public void testequals() {
        Piece singlePieceOne = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        Piece singlePieceTwo = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        assertEquals(singlePieceOne.equals(singlePieceTwo), true); 
    }
}

