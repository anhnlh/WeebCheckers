package com.webcheckers.model;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.Assert.assertEquals;

/**
 * @author Phil Ganem
 */
@Tag("Model-tier")
public class testPosition {

    @Test
    public void test_constructor() {
        Position testPosition = new Position(1,1);
        assertEquals(1, testPosition.getRow());
        assertEquals(1, testPosition.getCell());
    }

    @Test
    public void test_equals() {
        Position testPosition = new Position(1,1);
        Position testPosition2 = new Position(1,1);
        assertEquals(testPosition, testPosition2);
    }

    @Test
    public void test_notEquals() {
        Position testPosition = new Position(1,1);
        Position testPosition2 = new Position(2,2);
        assertEquals(false, testPosition.equals(testPosition2));
    }
}