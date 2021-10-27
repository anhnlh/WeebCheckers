package com.webcheckers.model;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.Assert.assertEquals;

import com.webcheckers.model.Move.MoveType;

/**
 * @author Phil Ganem
 */
@Tag("Model-tier")
public class TestMove {

    @Test
    public void test_constructor() {
        // Setup
        Position start = new Position(0, 0);
        Position end = new Position(1, 1);
        // Run the test
        Move move = new Move(start, end, MoveType.SIMPLE);
        // Verify the results
        assertEquals(start, move.getStart());
        assertEquals(end, move.getEnd());
    }

    @Test
    public void test_constructor_with_null_start() {
        // Setup
        Position end = new Position(1, 1);
        // Run the test
        Move move = new Move(null, end, MoveType.SIMPLE);
        // Verify the results
        assertEquals(null, move.getStart());
        assertEquals(end, move.getEnd());
    }

    @Test
    public void test_constructor_with_null_end() {
        // Setup
        Position start = new Position(0, 0);
        // Run the test
        Move move = new Move(start, null, MoveType.SIMPLE);
        // Verify the results
        assertEquals(start, move.getStart());
        assertEquals(null, move.getEnd());
    }

    @Test
    public void test_constructor_with_null_start_and_end() {
        // Run the test
        Move move = new Move(null, null, MoveType.SIMPLE);
        // Verify the results
        assertEquals(null, move.getStart());
        assertEquals(null, move.getEnd());
    }

    @Test    
    public void test_getType() {
        // Setup
        Position start = new Position(0, 0);
        Position end = new Position(1, 1);
        // Run the test
        Move move = new Move(start, end, MoveType.SIMPLE);
        // Verify the results
        assertEquals(MoveType.SIMPLE, move.getMoveType());
    }
    
}
