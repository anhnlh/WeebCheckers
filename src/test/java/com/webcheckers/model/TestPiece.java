package com.webcheckers.model;
import com.webcheckers.model.Piece.Type;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.webcheckers.model.Piece.Color;

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
        Color CuT = Color.RED;

        Piece singlePiece = new Piece(Type.SINGLE, Color.RED);

        Color actualDefault = singlePiece.getColor();

        assertEquals(CuT, actualDefault);
    }

    /**
     * Tests to see if color of a piece is correctly set
     */
    @Test
    public void testGetColorWhite() {
        Color CuT = Color.WHITE;

        Piece kingPiece = new Piece(Type.KING, Color.WHITE);

        Color actualKing = kingPiece.getColor();

        assertEquals(CuT, actualKing);
    }

    /**
     * Tests to see if type of a piece is correctly set
     */
    @Test
    public void testGetTypeSingle() {
        Piece CuT = new Piece(Type.SINGLE, Color.RED);

        Type actual = CuT.getType();

        assertEquals(Type.SINGLE, actual);
    }

    /**
     * Tests to see if type of a piece is correctly set
     */
    @Test
    public void testGetTypeKing() {
        Piece CuT = new Piece(Type.KING, Color.WHITE);

        Type actual = CuT.getType();

        assertEquals(Type.KING, actual);
    }
 
    /**
     * Tests to see if two pieces with same field values are equal
     */
    @Test
    public void testequalsSingle() {
        Piece CuT = new Piece(Type.SINGLE, Color.RED);
        
        Piece otherPiece = new Piece(Type.SINGLE, Color.RED);

        assertEquals(CuT, otherPiece);
    }

    /**
     * Tests to see if two pieces with same field values are equal
     */
    @Test
    public void testequalsKing() {
        Piece CuT = new Piece(Type.KING, Color.WHITE);
        
        Piece otherPiece = new Piece(Type.KING, Color.WHITE);

        assertTrue(CuT.equals(otherPiece));
    }
}

