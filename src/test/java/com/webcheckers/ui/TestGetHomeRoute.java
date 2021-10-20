package com.webcheckers.ui;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;

import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;

import spark.*;

@Tag("UI-tier")
@Testable
public class TestGetHomeRoute {
    
    private GetHomeRoute CuT;

    private PlayerLobby playerLobby;
    private HashMap<String, Game> gameMap;
    private String player1Name;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;
    
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        
        player1Name = "player1";
        playerLobby = new PlayerLobby();
        playerLobby.addPlayer(player1Name);

        gameMap = new HashMap<>();

        CuT = new GetHomeRoute(gameMap, playerLobby, templateEngine);
    }

    @Test
    public void constructorTest() {
        new GetHomeRoute(gameMap, playerLobby, templateEngine);
        assertNotNull(gameMap);
        assertNotNull(playerLobby);
        assertNotNull(templateEngine);
    }

}
