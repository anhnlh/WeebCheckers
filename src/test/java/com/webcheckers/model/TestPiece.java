package com.webcheckers.model;
import com.webcheckers.model.Piece.TYPE;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testGetColorRed() {
        PIECECOLOR CuT = PIECECOLOR.RED;

        Piece singlePiece = new Piece(TYPE.SINGLE, PIECECOLOR.RED);

        PIECECOLOR actualDefault = singlePiece.getColor();

        assertEquals(CuT, actualDefault);
    }

    /**
     * Tests to see if color of a piece is correctly set
     */
    @Test
    public void testGetColorWhite() {
        PIECECOLOR CuT = PIECECOLOR.WHITE;

        Piece kingPiece = new Piece(TYPE.KING, PIECECOLOR.WHITE);

        PIECECOLOR actualKing = kingPiece.getColor();

        assertEquals(CuT, actualKing);
    }

    /**
     * Tests to see if type of a piece is correctly set
     */
    @Test
    public void testGetTypeSingle() {
        Piece CuT = new Piece(TYPE.SINGLE, PIECECOLOR.RED);

        TYPE actual = CuT.getType();

        assertEquals(TYPE.SINGLE, actual);
    }

    /**
     * Tests to see if type of a piece is correctly set
     */
    @Test
    public void testGetTypeKing() {
        Piece CuT = new Piece(TYPE.KING, PIECECOLOR.WHITE);

        TYPE actual = CuT.getType();

        assertEquals(TYPE.KING, actual);
    }
 
    /**
     * Tests to see if two pieces with same field values are equal
     */
    @Test
    public void testequalsSingle() {
        Piece CuT = new Piece(TYPE.SINGLE, PIECECOLOR.RED);
        
        Piece otherPiece = new Piece(TYPE.SINGLE, PIECECOLOR.RED);

        assertEquals(CuT, otherPiece);
    }

    /**
     * Tests to see if two pieces with same field values are equal
     */
    @Test
    public void testequalsKing() {
        Piece CuT = new Piece(TYPE.KING, PIECECOLOR.WHITE);
        
        Piece otherPiece = new Piece(TYPE.KING, PIECECOLOR.WHITE);

        assertTrue(CuT.equals(otherPiece));
    }
}

