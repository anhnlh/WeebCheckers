package com.webcheckers.app;

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

    private HashMap<String, Player> lobby;
    private int lobbyNum;
    
    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();

        player1 = new Player("player1");
        player2 = new Player("player2");
        player3 = new Player("player3");
    }


}
