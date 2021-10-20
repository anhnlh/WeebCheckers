package com.webcheckers.app;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import com.webcheckers.model.Player;

import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;

@Tag("Application-tier")
@Testable
public class TestPlayerLobby {
    
    private PlayerLobby CuT;

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private HashMap<String, Player> lobby;

    private int lobbyNum;
    private String player1Name;
    private String player2Name;
    private String player3Name;
    private String player4Name;
    
    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();

        player1Name = "hi";
        player2Name = "hello";
        player3Name = "bye";
        player4Name = "hi";

        player1 = new Player(player1Name);
        player2 = new Player(player2Name);
        player3 = new Player(player3Name);
        player4 = new Player(player4Name);

        lobby = new HashMap<>();
    }

    @Test
    public void constructorTest() {
        assertNotNull(new PlayerLobby());
    }

    @Test
    public void testAddPlayer() {
        // 0 players added
        assertTrue(CuT.getLobby().isEmpty());

        // 1 player added
        CuT.addPlayer(player1Name);
        assertEquals(1, CuT.getLobby().size());
        assertTrue(CuT.getLobby().containsKey(player1Name.toLowerCase()));

        // 2 players added
        CuT.addPlayer(player2Name);
        assertEquals(2, CuT.getLobby().size());
        assertTrue(CuT.getLobby().containsKey(player2Name.toLowerCase()));

        // 3 players added
        CuT.addPlayer(player3Name);
        assertEquals(3, CuT.getLobby().size());
        assertTrue(CuT.getLobby().containsKey(player3Name.toLowerCase()));

        // Attempted to add player 4, but rejected due to duplicate name
        CuT.addPlayer(player4Name);
        assertEquals(3, CuT.getLobby().size());
    }
}
