package com.webcheckers.model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Phil Ganem
 */
@Tag("Model-tier")
public class TestPosition {

    
    @Test
    public void testConstructor() {
        Position testPosition = new Position(1,1);
        assertEquals(1, testPosition.getRow());
        assertEquals(1, testPosition.getCell());
    }

    @Test
    public void testEquals() {
        Position testPosition = new Position(1,1);
        Position testPosition2 = new Position(1,1);
        assertEquals(testPosition, testPosition2);
    }

    @Test
    public void testNotEquals() {
        Position testPosition = new Position(1,1);
        Position testPosition2 = new Position(2,2);
        assertNotEquals(testPosition, testPosition2);
    }

    @Test
    public void testIsInBounds() {
        Position testPosition = new Position(1,1);
        assertTrue(Position.isInBounds(testPosition));
    }

    @Test
    public void testBadPositionConstructor() {
        Position pos1 = new Position(0, 8);
        Position pos2 = new Position(8, 0);
        Position pos3 = new Position(-1, 0);
        Position pos4 = new Position(0, -1);
        Position pos5 = new Position(123123, -1239123);

        assertFalse(Position.isInBounds(pos1));
        assertFalse(Position.isInBounds(pos2));
        assertFalse(Position.isInBounds(pos3));
        assertFalse(Position.isInBounds(pos4));
        assertFalse(Position.isInBounds(pos5));
    }
}