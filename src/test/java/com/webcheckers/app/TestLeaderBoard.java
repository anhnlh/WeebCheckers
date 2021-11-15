package com.webcheckers.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.webcheckers.model.Player;

import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;


/** 
 * Tests the Application-tier PlayerLobby class 
*/
@Tag("Application-tier")
@Testable
public class TestLeaderBoard {
    /**
     * The component-under-test (CuT)
     */
    private LeaderBoard CuT;

    private int lobbyNum;
    private String player1Name;
    private String player2Name;
    private String player3Name;
    private String player4Name;

    /**
     * Setup values before each test
     */
    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();

        player1Name = "Hi";
        player2Name = "Hello";
        player3Name = "Bye";
        player4Name = "Hi";

    }

    /**
     * Tests {@link PlayerLobby#PlayerLobby()}
     */
    @Test
    public void constructorTest() {
        new LeaderBoard();
    }

    /**
     * Tests {@link LeaderBoard#addPlayer(String)}
     */
    @Test
    public void testAddPlayer() {
        // 0 players added
        assertTrue(CuT.isEmpty());

        // 1 player added
        CuT.addPlayer(player1Name);
        assertEquals(1, CuT.getSize());
        assertTrue(CuT.getMap().containsKey(player1Name.toLowerCase()));

        // 2 players added
        CuT.addPlayer(player2Name);
        assertEquals(2, CuT.getSize());
        assertTrue(CuT.getMap().containsKey(player2Name.toLowerCase()));

        // 3 players added
        CuT.addPlayer(player3Name);
        assertEquals(3, CuT.getSize());
        assertTrue(CuT.getMap().containsKey(player3Name.toLowerCase()));

        // Attempted to add player 4, but rejected due to duplicate name
        CuT.addPlayer(player4Name);
        assertEquals(3, CuT.getSize());
    }

}
