package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Tag("Model-tier")
@Testable
public class TestBoardView {
    @Test
    public void testConstructor() {
        new BoardView();
    }

    @Test
    public void testGetterSetters() {
        BoardView CuT = new BoardView();
        assertNotNull(CuT.getBoard());

        assertEquals(CuT.getNumRedPieces(), 12);
        assertEquals(CuT.getNumWhitePieces(), 12);

        CuT.decreaseNumRedPieces();
        CuT.decreaseNumRedPieces();
        CuT.decreaseNumRedPieces();

        assertEquals(CuT.getNumRedPieces(), 9);

        for (int i = 0; i < 12; i++) {
            CuT.decreaseNumWhitePieces();
        }
        assertEquals(CuT.getNumWhitePieces(), 0);

    }
}
