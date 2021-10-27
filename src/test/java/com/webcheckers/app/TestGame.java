package com.webcheckers.app;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Row;
import com.webcheckers.model.Space;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Tests the Application-tier Game class
 * @author Anh Nguyen
 */
@Tag("Application-tier")
@Testable
public class TestGame {

    /**
     * The component-under-test (CuT)
     */
    private Game CuT;

    // Values for testing purposes
    private Player redPlayer;
    private Player whitePlayer;
    private BoardView board;

    @BeforeEach
    public void setup() {
        redPlayer = new Player("Player 1");
        whitePlayer = new Player("Player 2");
        board = new BoardView();

        CuT = new Game(redPlayer, whitePlayer);
    }

    /**
     * Tests Game's constructor {@link Game#Game(Player, Player)}
     */
    @Test
    public void testCreateGame() {
        new Game(redPlayer, whitePlayer);
    }

    /**
     * Tests {@link Game#getRedPlayer()}
     */
    @Test
    public void testGetRedPlayer() {
        assertEquals(CuT.getRedPlayer(), redPlayer);
    }

    /**
     * Tests {@link Game#getWhitePlayer()}
     */
    @Test
    public void testGetWhitePlayer() {
        assertEquals(CuT.getWhitePlayer(), whitePlayer);
    }

    /**
     * Tests {@link Game#getID()}
     */
    @Test
    public void testGetID() {
        assertEquals(CuT.getID(), Objects.hash(redPlayer, whitePlayer));
    }

    /**
     * Tests {@link Game#isRedPlayer(Player)}
     */
    @Test
    public void testIsRedPlayer() {
        assertTrue(CuT.isRedPlayer(redPlayer));
        assertFalse(CuT.isRedPlayer(whitePlayer));
    }

    /**
     * Tests {@link Game#redPlayerBoard()}
     */
    @Test
    public void testRedPlayerBoard() {
        assertEquals(CuT.redPlayerBoard(), board);
    }

    /**
     * Tests {@link Game#whitePlayerBoard()}
     */
    @Test
    public void testWhitePlayerBoard() {
        List<Row> whiteBoard = new ArrayList<>();
        // iterate backward to build board backward for orientation
        for (int r = BoardView.BOARD_LENGTH - 1; r >= 0; r--) {
            Row row = new Row(board.getRow(r).getIndex());
            for (int c = BoardView.BOARD_LENGTH - 1; c >= 0; c--) {
                Space space = board.getRow(r).getSpace(c);
                row.addSpace(space);
            }
            whiteBoard.add(row);
        }
        BoardView whitePlayerBoard = new BoardView(whiteBoard);

        assertEquals(CuT.whitePlayerBoard(), whitePlayerBoard, "White's boards don't match");
    }

}
