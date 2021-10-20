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

        player1Name = "Hi";
        player2Name = "Hello";
        player3Name = "Bye";
        player4Name = "Hi";

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

    @Test
    public void testRemovePlayer() {
        CuT.addPlayer(player1Name);
        CuT.addPlayer(player2Name);
        CuT.addPlayer(player3Name);

        CuT.removePlayer(player1Name);
        assertEquals(2, CuT.getLobby().size());
        assertTrue(!CuT.getLobby().containsKey(player1Name.toLowerCase()));

        CuT.removePlayer(player2Name);
        assertEquals(1, CuT.getLobby().size());
        assertTrue(!CuT.getLobby().containsKey(player2Name.toLowerCase()));

        CuT.removePlayer(player3Name);
        assertEquals(0, CuT.getLobby().size());
        assertTrue(!CuT.getLobby().containsKey(player3Name.toLowerCase()));
    }

    @Test
    public void testGetPlayer() {
        CuT.addPlayer(player1Name);

        // Player 1 is in the lobby, should return Player 1
        assertTrue(player1.equals(CuT.getPlayer(player1Name)));

        // Player 2 is not in the lobby, should return null
        assertEquals(null, CuT.getPlayer(player2Name));
    }

    @Test
    public void testActivePlayersMessage() {
        String no_players = "There are currently no players online.";
        String one_player = "There is 1 player online.";
        String two_players = "There are 2 players online.";
        String three_players = "There are 3 players online.";

        assertEquals(no_players, CuT.activePlayersMessage());

        CuT.addPlayer(player1Name);
        assertEquals(one_player, CuT.activePlayersMessage());

        CuT.addPlayer(player2Name);
        assertEquals(two_players, CuT.activePlayersMessage());

        CuT.addPlayer(player3Name);
        assertEquals(three_players, CuT.activePlayersMessage());
    }

    @Test
    public void testContains() {
        CuT.addPlayer(player1Name);

        assertTrue(CuT.contains(player1Name));
        assertFalse(CuT.contains(player2Name));
    }
}
